package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.dto.MailDTO;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.service.AdminService;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.util.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    private final AdminService adminService;
    private final MailService mailService;
    private final AwsService awsService;

    @PostMapping("/test")
    public ResponseEntity<String> test(MultipartFile file){
        log.info("test : " + file.toString());
        try {
            A_thumb aThumb = awsService.uploadFileToA_thumb(file);
            String url = awsService.getFileUrl(aThumb.getStodname());
            return ResponseEntity.ok(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

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

    // 회원가입 폼
    @GetMapping("/guestJoinForm")
    public String guestJoinForm(){
        return "/join";
    }

    // 로그인 폼
    @GetMapping("/loginForm")
    public String loginForm(){ return "/front/guest/login";}

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity<Map<String,String>> logout(HttpSession session){
        String logout = guestService.logout(session);
        Map<String, String> map = new HashMap<>();
        if(logout.equals("true")){
            log.info("로그아웃 성공");
            map.put("로그아웃","성공");
        }else{
            log.info("로그인이 되어있지 않습니다.");
            map.put("로그아웃","실패");
        }
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


    //비번 찾기 메일
    @PostMapping("/check/findpw/sendmail")
    public ResponseEntity sendMail(String email, String name){
        MailDTO mailDTO = mailService.createMailAndChangePassword(email, name);
        mailService.sendMail(mailDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원가입
    @PostMapping("/guestJoin")
    public ResponseEntity guestJoin(@RequestBody @Valid GuestJoinDTO guestJoinDTO){

        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String ,String>> login(@Valid LoginDTO loginDTO, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/")String redirectURL, HttpServletRequest request){

        log.info("login : {}, {}", loginDTO.getId(), loginDTO.getPw());

        if(bindingResult.hasFieldErrors()){
            log.info("로그인 바인딩 에러");
        }
        Map<String, String > map = new HashMap<>();
        //로그인 성공
        String loginGuestNick = guestService.Login(loginDTO);
        Guest guest = guestService.findGuestNick(loginGuestNick);
        if(loginGuestNick != null){
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn",loginGuestNick);
            session.setAttribute("role",guest.getRole());
            log.info("로그인 성공");
            map.put("로그인","성공");
            return ResponseEntity.ok(map);
        }

        //로그인 실패
        bindingResult.reject("loginFail", "올바르지 않은 아이디 혹은 비밀번호 입니다.");
        map.put("로그인","실패");
        return ResponseEntity.ok(map);
    }

    //관리자 로그인
    @PostMapping("/adminLogin")
    public ResponseEntity adminLogin(@Valid LoginDTO loginDTO, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request){

        log.info("login : {}, {}", loginDTO.getId(), loginDTO.getPw());
        
        if(bindingResult.hasFieldErrors()){
            log.info("로그인 바인딩 에러");
        }

        //로그인 성공
        Admin admin = adminService.Login(loginDTO);
        HttpSession session = request.getSession();
        session.setAttribute("loggedIn",admin.getNick());
        session.setAttribute("role",admin.getRole());
        log.info("관리자 로그인 성공");

        return new ResponseEntity(HttpStatus.OK);
    }

//    // 비밀번호 재 등록
//    public ResponseEntity<Map<String, String> reAddPw(String pw){
//
//
//    }

}
