package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.dto.MailDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final GuestService guestService;
    private final MailService mailService;

    // 아이디 중복 확인
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<Boolean> checkIdDuplication(@PathVariable Long id){
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
    public String logout(HttpSession session){
        String logout = guestService.logout(session);
        if(logout.equals("true")){
            log.info("로그아웃 성공");
            return "redirect:/login";
        }

        return "redirect:/";
    }
    
    //이메일 이름 일치 확인
    @GetMapping("/check/findpw/{email}/{name}")
    public Map<String, Boolean> pw_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){

        Map<String, Boolean> json = new HashMap<>();
        log.info("email : "+ email);
        log.info("name :"+ name);

        json.put("check", guestService.checkEmailAndName(email, name));
        return json;
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
    public void sendMail(String email, String name){
        MailDTO mailDTO = mailService.createMailAndChangePassword(email, name);
        mailService.sendMail(mailDTO);
    }

    // 회원가입
    @PostMapping("/guestJoin")
    public String guestJoin(@Valid GuestJoinDTO guestJoinDTO){

        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);

        return "/";
    }

    // 로그인
    @PostMapping("/login")
    public String login(@Valid LoginDTO loginDTO, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/")String redirectURL, HttpServletRequest request){

        log.info("login : {}, {}", loginDTO.getId(), loginDTO.getPw());

        if(bindingResult.hasFieldErrors()){
            log.info("로그인 바인딩 에러");
        }
        
        //로그인 성공
        String loginGuestNick = guestService.Login(loginDTO);
        Guest guest = guestService.findGuestNick(loginGuestNick);
        if(loginGuestNick != null){
            HttpSession session = request.getSession();
            session.setAttribute("loggedIn",loginGuestNick);
            session.setAttribute("role",guest.getRole());
            log.info("로그인 성공");
            return "redirect:";
        }

        //로그인 실패
        bindingResult.reject("loginFail", "올바르지 않은 아이디 혹은 비밀번호 입니다.");
        return "/login";
    }

}
