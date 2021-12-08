package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final MailService mailService;
    private final Response response;


    // 아이디 중복 확인
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<?> checkIdDuplication(@PathVariable String id){

        //true면 중복 false면 중복x
        boolean check = guestService.checkIdDuplication(id);
        Map<String, String> map = new HashMap<>();

        if(check == true){
            map.put("duplication", "true");
            return response.success(map,"아이디가 중복입니다.", HttpStatus.OK);
        }else{
            map.put("duplication", "false");
            return response.success(map,"아이디 사용 가능합니다.",HttpStatus.OK);
        }

    }

    // 이메일 중복 확인
    @GetMapping("/guest-emails/{email}/exists")
    public ResponseEntity<?> checkEmailDuplication(@PathVariable String email){

        boolean check = guestService.checkEmailDuplication(email);
        Map<String, String> map = new HashMap<>();

        if(check == true){
            map.put("duplication", "true");
            return response.success(map,"이메일이 중복입니다.",HttpStatus.OK);
        }else{
            map.put("duplication", "false");
            return response.success(map,"이메일 사용 가능합니다.",HttpStatus.OK);
        }
    }

    // 닉네임 중복 확인
    @GetMapping("/guest-nick/{nick}/exists")
    public ResponseEntity<?> checkNickDuplication(@PathVariable String nick){
        boolean check = guestService.checkNickDuplication(nick);
        Map<String, String> map = new HashMap<>();

        if(check == true){
            map.put("duplication", "true");
            return response.success(map,"닉네임 중복입니다.",HttpStatus.OK);
        }else{
            map.put("duplication", "false");
            return response.success(map,"닉네임 사용 가능합니다.",HttpStatus.OK);

        }
    }

    //이메일 이름 일치 확인
    @GetMapping("/check/findpw/{email}/{name}")
    public ResponseEntity<?> pw_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){


        boolean check = guestService.checkEmailAndName(email, name);
        Map<String, String> map = new HashMap<>();

        if(check == true){
            map.put("coincide", "true");
            return response.success(map,"이메일과 이름이 일치합니다.",HttpStatus.OK);
        }else{
            map.put("coincide", "false");
            return response.success(map,"이메일과 이름이 일치하지 않습니다.",HttpStatus.OK);
        }
    }

    //id 찾기
    @GetMapping("/check/findid/{email}/{name}")
    public ResponseEntity<?> id_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){


        Map<String, String> map = new HashMap<>();
        String id = guestService.findGuestId(email, name);
        if(id == null){
            map.put("id", "null");
            return response.success(map,"아이디가 존재하지 않습니다",HttpStatus.OK);
        }else{
            map.put("id",id);
            return response.success(map,"아이디는 "+id+" 입니다.",HttpStatus.OK);
        }
    }

    //비번 찾기 메일
    @PostMapping("/check/findpw/sendmail")
    public ResponseEntity<?> sendMail(@RequestBody FindPwDTO findPwDTO){

        MailDTO mailDTO = mailService.createMailAndChangePassword(findPwDTO);
        mailService.sendMail(mailDTO);
        Map<String, String> map = new HashMap<>();
        map.put("mail", "success");

        return response.success(map,"메일 전송",HttpStatus.OK);
    }


    // 회원가입
    @PostMapping("/guestJoin")
    public ResponseEntity<?> guestJoin(@Validated @RequestBody GuestJoinDTO guestJoinDTO,
                                                         Errors errors){

        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }
        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);
        Map<String, String> map = new HashMap<>();

        if(gno > 0){
            map.put("join", "success");
            return response.success(map, "회원가입 성공", HttpStatus.OK);
        }else{
            map.put("join", "fail");
            return response.success(map, "회원가입 실패", HttpStatus.OK);
        }

    }


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
    @PostMapping("/logout2")
    public ResponseEntity<?> logout(@Validated @RequestBody  LogoutDTO logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return guestService.logout(logout);
    }
    
    // 재발급
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated @RequestBody ReissueDTO reissue, Errors errors) {
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
