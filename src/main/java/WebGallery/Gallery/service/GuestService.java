package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    public boolean checkEmailAndName(String email, String name){
        log.info("check");
        Guest guest = guestRepository.findByEmail(email);
        if(guest.getName().equals(name)){
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
        if(guest.getName().equals(name)){
            return guest.getId();
        }else{
            log.info("정보를 다시 입력해주십시오");
            return null;
        }
    }

    public Integer deleteGuest(Long gno){
        int check = 0;
        try {
            Guest guest = guestRepository.findByGno(gno);
            guestRepository.delete(guest);
            log.info("guest delete success");
            check = 1;
        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
        return check;
    }

    public Integer modifyGuest(GuestModifyDTO guestModifyDTO){

        log.info(guestModifyDTO.toString());
        int check = 0;
        try {
            Guest guest = guestRepository.findByGno(guestModifyDTO.getGno());

            if (!guest.getNick().equals(guestModifyDTO.getNick())) {
                guest.changeNick(guestModifyDTO.getNick());
                check = 1;
            }
            if (!guest.getPw().equals(guestModifyDTO.getPw())) {
                guest.changePw(guestModifyDTO.getPw());
                check = 1;
            }
            if (!guest.getEmail().equals(guestModifyDTO.getEmail())) {
                guest.changeEmail(guestModifyDTO.getEmail());
                check = 1;
            }
            if (check == 1) {
                guestRepository.save(guest);
            } else {
                log.info("guest 수정사항 없음");
            }
        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
        return check;
    }

    public Integer changePw(String pw, String guestNick){
        int check = 0;
        try {
            Guest guest = guestRepository.findByNick(guestNick);
            guest.changePw(pw);
            guestRepository.save(guest);
            check = 1;
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return check;
    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {

        if(guestRepository.findById(loginDTO.getId()).orElse(null) == null){
            return response.fail("해당하는 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }


        UsernamePasswordAuthenticationToken authenticationToken =
                loginDTO.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("===================check2====================");
        UserResponseDTO.TokenInfo tokenInfo = jwTokenProvider2.generateToken(authentication);
        log.info("===================check3====================");

        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(),
                        tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);


        return response.success(tokenInfo, "로그인에 성공했습니다", HttpStatus.OK);

    }

    public ResponseEntity<?> reissue(ReissueDTO reissue) {
        // 1. Refresh Token 검증
        if (!jwTokenProvider2.validateToken(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 를 가져옵니다.
        Authentication authentication = jwTokenProvider2.getAuthentication(reissue.getAccessToken());

        // 3. Redis 에서 User email 을 기반으로 저장된 Refresh Token 값을 가져옵니다.
        String refreshToken = (String)redisTemplate.opsForValue().get("RT:" + authentication.getName());
        if(!refreshToken.equals(reissue.getRefreshToken())) {
            return response.fail("Refresh Token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        // 4. 새로운 토큰 생성
        UserResponseDTO.TokenInfo tokenInfo = jwTokenProvider2.generateToken(authentication);

        // 5. RefreshToken Redis 업데이트
        redisTemplate.opsForValue()
                .set("RT:" + authentication.getName(), tokenInfo.getRefreshToken(), tokenInfo.getRefreshTokenExpirationTime(), TimeUnit.MILLISECONDS);

        return response.success(tokenInfo, "Token 정보가 갱신되었습니다.", HttpStatus.OK);
    }

    public ResponseEntity<?> logout(LogoutDTO logout) {
        // 1. Access Token 검증
        if (!jwTokenProvider2.validateToken(logout.getAccessToken())) {
            return response.fail("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. Access Token 에서 User email 을 가져옵니다.
        Authentication authentication = jwTokenProvider2.getAuthentication(logout.getAccessToken());

        // 3. Redis 에서 해당 User email 로 저장된 Refresh Token 이 있는지 여부를 확인 후 있을 경우 삭제합니다.
        if (redisTemplate.opsForValue().get("RT:" + authentication.getName()) != null) {
            // Refresh Token 삭제
            redisTemplate.delete("RT:" + authentication.getName());
        }

        // 4. 해당 Access Token 유효시간 가지고 와서 BlackList 로 저장하기
        Long expiration = jwTokenProvider2.getExpiration(logout.getAccessToken());
        redisTemplate.opsForValue()
                .set(logout.getAccessToken(), "logout", expiration, TimeUnit.MILLISECONDS);

        return response.success("로그아웃 되었습니다.");
    }
}
