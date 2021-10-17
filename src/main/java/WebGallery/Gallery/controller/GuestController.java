package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.service.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class GuestController {

    private final GuestService guestService;

    // 아이디 중복 확인
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<Boolean> checkIdDupslication(@PathVariable Long id){
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


}
