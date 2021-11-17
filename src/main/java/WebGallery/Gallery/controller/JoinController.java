package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.service.AdminService;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.JwtAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final GuestService guestService;
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    private final AdminService adminService;
    private final MailService mailService;
    private JwtAuthenticationProvider jwtAuthenticationProvider;


    // 아이디 중복 확인
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplication(@PathVariable String id){
        return ResponseEntity.ok(guestService.checkIdDuplication(id));
    }

    // 이메일 중복 확인
    @GetMapping("/guest-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplication(@PathVariable String email){
        return ResponseEntity.ok(guestService.checkEmailDuplication(email));
    }

    // 닉네임 중복 확인
    @GetMapping("/guest-nick/{nick}/exists")
    public ResponseEntity<Boolean> checkNickDuplication(@PathVariable String nick){
        return ResponseEntity.ok(guestService.checkNickDuplication(nick));
    }

    // 로그아웃
    @GetMapping("/logout")
    public void logout(HttpServletResponse response){
//        session.invalidate();
        Cookie cookie = new Cookie("X-AUTH-TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    //이메일 이름 일치 확인
    @GetMapping("/check/findpw/{email}/{name}")
    public Map<String, Boolean> pw_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){

        Map<String, Boolean> map = new HashMap<>();
        log.info("email : "+ email);
        log.info("name :"+ name);

        map.put("check", guestService.checkEmailAndName(email, name));
        return map;
    }

    //id 찾기
    @GetMapping("/check/findid/{email}/{name}")
    public Map<String, String> id_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){

        Map<String, String> json = new HashMap<>();
        String id = guestService.findGuestId(email, name);
        if(id == null){
            json.put("id", null);
        }else{
            json.put("id",id);
        }
        return json;
    }

    @GetMapping("/info")    //새로고침 시 요청이 들어오는 경로 토큰 유효시 정보 꺼내서 주고 아니면 null
    public GuestInfoDTO getInfo(){
        Object details = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(details != null && !(details instanceof String)) return new GuestInfoDTO(details);
        return null;
    }


    //비번 찾기 메일
    @PostMapping("/check/findpw/sendmail")
    public ResponseEntity sendMail(@RequestBody FindPwDTO findPwDTO){
        MailDTO mailDTO = mailService.createMailAndChangePassword(findPwDTO);
        mailService.sendMail(mailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }


    // 회원가입
    @PostMapping("/guestJoin")
    public ResponseEntity guestJoin(@Validated @RequestBody GuestJoinDTO guestJoinDTO){

        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public GuestInfoDTO login(@Validated @RequestBody LoginDTO loginDTO,
                                                     HttpServletResponse response){

        Guest guest = guestRepository.findById(loginDTO.getId());
        if(!passwordEncoder.matches(loginDTO.getPw(), guest.getPassword())){
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String token = jwtAuthenticationProvider.createToken(guest.getId(), guest.getRole());
        response.setHeader("X-AUTH-TOKEN", token);

        Cookie cookie = new Cookie("X-AUTH-TOKEN", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

        GuestInfoDTO guestInfoDTO = new GuestInfoDTO();
        guestInfoDTO.setGno(guest.getGno());
        guestInfoDTO.setNickname(guest.getNick());

        return guestInfoDTO;
    }

//    //관리자 로그인
//    @PostMapping("/adminLogin")
//    public ResponseEntity adminLogin(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult,
//                          @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request){
//
//        log.info("login : {}, {}", loginDTO.getId(), loginDTO.getPw());
//
//        if(bindingResult.hasFieldErrors()){
//            log.info("로그인 바인딩 에러");
//        }
//
//        //로그인 성공
//        Admin admin = adminService.Login(loginDTO);
//        HttpSession session = request.getSession();
//        session.setAttribute("loggedIn",admin.getNick());
//        session.setAttribute("role",admin.getRole());
//        log.info("관리자 로그인 성공");
//
//        return new ResponseEntity(HttpStatus.OK);
//    }



}
