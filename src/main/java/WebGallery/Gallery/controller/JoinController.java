package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final GuestService guestService;

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
    @GetMapping("/guestJoin")
    public String guestJoinForm(){
        return "/";
    }

    // 회원가입
    @PostMapping("/guestJoin")
    public String guestJoin(@Valid GuestJoinDTO guestJoinDTO){

        log.info(guestJoinDTO.toString());
        Long gno = guestService.join(guestJoinDTO);

        return "";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm(){ return "/";}

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
        }

        //로그인 실패
        bindingResult.reject("loginFail", "올바르지 않은 아이디 혹은 비밀번호 입니다.");
        return "/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        String logout = guestService.logout(session);
        if(logout.equals("true")){
            log.info("로그아웃 성공");
            return "redirect:/login";
        }
        return "redirect:/";
    }

}
