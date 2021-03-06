package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.A_TumbRepository;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.JwTokenProvider2;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTemplate redisTemplate;
    private final Response response;
    private final JwTokenProvider2 jwTokenProvider2;
    private final AwsService awsService;
    private final AuthorRepository authorRepository;
    private final A_TumbRepository a_tumbRepository;


    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        return guestRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkIdDuplication(String id) {
        return guestRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean checkNickDuplication(String nick) {
        return guestRepository.existsByNick(nick);
    }

    @Transactional(readOnly = true)
    public boolean checkEmailAndId(String email, String id){

        Guest guest = guestRepository.findByEmail(email);
        if(guest != null && guest.getId().equals(id)){
            return true;
        }
        return false;
    }

    public Long join(GuestJoinDTO guestJoinDTO) {

        String encodedPassword = passwordEncoder.encode(guestJoinDTO.getPw());


        Guest guest = new Guest(
                guestJoinDTO.getName(),
                guestJoinDTO.getId(),
                encodedPassword,
                guestJoinDTO.getNick(),
                guestJoinDTO.getEmail(),
                Role.GUEST);

        guestRepository.save(guest);
        return guest.getGno();
    }

    @Transactional(readOnly = true)
    public Guest findGuestNick(String nick){
        Guest guest = guestRepository.findByNick(nick);
        return guest;
    }

    @Transactional(readOnly = true)
    public String findGuestId(String email, String name){
        Guest guest = guestRepository.findByEmail(email);

        if(guest != null && guest.getName().equals(name)){
            return guest.getId();
        }else{
            return null;
        }
    }

    public ResponseEntity<?> deleteGuest(DeleteGuestDTO deleteGuestDTO){

            Map<String, Boolean> map = new HashMap<>();

            Guest guest = guestRepository.findById(deleteGuestDTO.getId()).orElse(null);
            if(guest == null){
                map.put("delete", false);
                return response.fail(map,"???????????? ????????? ???????????? ????????????", HttpStatus.BAD_REQUEST);
            }
            guestRepository.delete(guest);
            map.put("delete", true);
            return response.success(map,"????????? ????????? ?????????????????????", HttpStatus.OK);
    }

    public Integer modifyGuest(GuestModifyDTO guestModifyDTO){

        int check = 0;
        try {
            Guest guest = guestRepository.findById(guestModifyDTO.getId()).orElse(null);
            if(guest == null){
                check = 2;
            }

            if (!guest.getNick().equals(guestModifyDTO.getNick())) {
                guest.changeNick(guestModifyDTO.getNick());
                check = 1;
            }

            if (!guest.getEmail().equals(guestModifyDTO.getEmail())) {
                guest.changeEmail(guestModifyDTO.getEmail());
                check = 1;
            }
            if (check == 1) {
                guestRepository.save(guest);
            }
        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
        return check;
    }

    public ResponseEntity<?> changePw(ChangePwDTO changePwDTO){

        Guest guest = guestRepository.findById(changePwDTO.getId()).orElse(null);
        Map<String, Boolean> map = new HashMap<>();
        if(guest == null){
            map.put("modify", false);
            return response.fail(map,"???????????? ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = passwordEncoder.encode(changePwDTO.getPw());

        guest.changePw(encodedPassword);
        guestRepository.save(guest);

        map.put("modify", true);

        return response.success(map,"???????????? ????????? ??????????????????",HttpStatus.OK);

    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {

        Guest guest = guestRepository.findById(loginDTO.getId()).orElse(null);
        Map<String, Boolean> map = new HashMap<>();
        if(guest == null){
            map.put("login", false);
            return response.fail(map,"???????????? ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }

        if(!passwordEncoder.matches(loginDTO.getPw(),guest.getPassword())){
            map.put("login", false);
            return response.fail(map,"???????????? ?????? ????????????", HttpStatus.BAD_REQUEST);
        }

        UsernamePasswordAuthenticationToken authenticationToken = loginDTO.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        UserResponseDTO.TokenInfo tokenInfo = jwTokenProvider2.generateToken(authentication);


        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(),
                        tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "???????????? ??????????????????", HttpStatus.OK);

    }

    public ResponseEntity<?> reissue(ReissueDTO reissue) {
        // 1. Refresh Token ??????
        if (!jwTokenProvider2.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token ?????? User email ??? ???????????????.
        Authentication authentication = jwTokenProvider2.getAuthentication(reissue.getAccessToken());

        // 3. Redis ?????? User email ??? ???????????? ????????? Refresh Token ?????? ???????????????.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);
        }

        // 4. ????????? ?????? ??????
        UserResponseDTO.TokenInfo tokenInfo = jwTokenProvider2.generateToken(authentication);

        // 5. RefreshToken Redis ????????????
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token ????????? ?????????????????????.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(LogoutDTO logout) {
        // 1. Access Token ??????
        if (!jwTokenProvider2.validateToken(logout.getAccessToken())) {
            return response.fail("????????? ???????????????.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token ?????? User ??? ???????????????.
        Authentication authentication = jwTokenProvider2.getAuthentication(logout.getAccessToken());

        // 3. Redis ?????? ?????? User ??? ????????? Refresh Token ??? ????????? ????????? ?????? ??? ?????? ?????? ???????????????.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token ??????
            redisTemplate.delete("RT:" + authentication.getName());
        }
        // 4. ?????? Access Token ???????????? ????????? ?????? BlackList ??? ????????????
        Long expiration = jwTokenProvider2.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("???????????? ???????????????.");
    }

    public ResponseEntity<?> authorJoin(AuthorJoinDTO authorJoinDTO, MultipartFile thumb){


        Map<String, Boolean> map = new HashMap<>();
        try {
            Guest guest = guestRepository.findById(authorJoinDTO.getId()).orElse(null);
            if(guest == null){
                map.put("join",false);
                return response.fail(map,"??????????????? ???????????? ????????????", HttpStatus.BAD_REQUEST);
            }

            A_thumb a_thumb = awsService.uploadFileToA_thumb(thumb);
            String stodName = a_thumb.getStodname();

            Author author = new Author(
                    guest,
                    authorJoinDTO.getSns(),
                    authorJoinDTO.getComment(),
                    stodName
            );

            Author save = authorRepository.save(author);

            if(save != null){
                a_thumb.saveAuthor(author);
                a_tumbRepository.save(a_thumb);
                guest.changeRole(Role.AUTHOR);
                guestRepository.save(guest);
                map.put("join",true);
                return response.success(map,"?????? ?????? ??????",HttpStatus.OK);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        map.put("join",false);
        return response.fail(map, "?????? ????????? ?????????????????????.", HttpStatus.BAD_REQUEST);
    }
}
