package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final GuestService guestService;
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final Response response;


    // 아이디 중복 확인
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<Map<String, Boolean>> checkIdDuplication(@PathVariable String id){

        Map<String, Boolean> map = new HashMap<>();
        //true면 중복 false면 중복x
        map.put("duplicate", guestService.checkIdDuplication(id));
        return ResponseEntity.ok(map);
    }

    // 이메일 중복 확인
    @GetMapping("/guest-emails/{email}/exists")
    public ResponseEntity<Map<String, Boolean>> checkEmailDuplication(@PathVariable String email){
        Map<String, Boolean> map = new HashMap<>();
        map.put("duplicate", guestService.checkEmailDuplication(email));
        return ResponseEntity.ok(map);
    }

    // 닉네임 중복 확인
    @GetMapping("/guest-nick/{nick}/exists")
    public ResponseEntity<Map<String, Boolean>> checkNickDuplication(@PathVariable String nick){
        Map<String, Boolean> map = new HashMap<>();
        map.put("duplicate", guestService.checkNickDuplication(nick));
        return ResponseEntity.ok(map);
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
    public ResponseEntity<Map<String, String>> sendMail(@RequestBody FindPwDTO findPwDTO){
        MailDTO mailDTO = mailService.createMailAndChangePassword(findPwDTO);
        mailService.sendMail(mailDTO);
        Map<String, String> map = new HashMap<>();
        map.put("mail", "success");

        return ResponseEntity.ok(map);
    }


    // 회원가입
    @PostMapping("/guestJoin")
    public ResponseEntity<Map<String, String>> guestJoin(@Validated @RequestBody GuestJoinDTO guestJoinDTO){

        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);
        Map<String, String> map = new HashMap<>();
        if(gno > 0){
            map.put("join", "success");
        }else{
            map.put("join", "fail");
        }

        return ResponseEntity.ok(map);
    }

    // 로그인
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO,
//                                                     HttpServletResponse response){
//
//        Guest guest = guestRepository.findById(loginDTO.getId()).
//                orElseThrow(() -> new IllegalArgumentException("가입되지 않은 id 입니다."));
//
//        if(!passwordEncoder.matches(loginDTO.getPw(), guest.getPassword())){
//            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
//        }
//        String token = jwtTokenProvider.createToken(guest.getNick(), guest.getRole());
//        response.setHeader("X-AUTH-TOKEN", token);
//
//
//
//        GuestInfoDTO guestInfoDTO = new GuestInfoDTO(guest.getNick(), guest.getGno(),guest.getRole());
//
//        return ResponseEntity.ok(guestInfoDTO);
//    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO,
                                   Errors errors){
            if(errors.hasErrors()){
                return response.invalidFields(Helper.refineErrors(errors));
            }
            return guestService.login(loginDTO);

    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated LogoutDTO logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return guestService.logout(logout);
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated ReissueDTO reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return guestService.reissue(reissue);
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
