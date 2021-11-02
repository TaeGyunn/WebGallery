package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestModifyDTO;
import WebGallery.Gallery.dto.MailDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final MailService mailService;

    @GetMapping("/deleteGuestForm")
    public String deleteGuestForm(){
        return "";
    }


    @GetMapping("/modifyGuestForm")
    public String modifyGuestForm(){
        // 폼 넘겨줄떄 guest정보 넘겨주기 
        // 폼 기본 값 설정위해서
        return "";
    }

    @GetMapping("/check/findpw/{email}/{name}")
    public Map<String, Boolean> pw_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){

        Map<String, Boolean> json = new HashMap<>();
        log.info("email : "+ email);
        log.info("name :"+ name);

        json.put("check", guestService.checkEmailAndName(email, name));
        return json;
    }

    @PostMapping("/check/findpw/sendmail")
    public void sendMail(String email, String name){
        MailDTO mailDTO = mailService.createMailAndChangePassword(email, name);
        mailService.sendMail(mailDTO);
    }


    @PutMapping("/modifyGuest")
    public String modifyGuest(@Valid GuestModifyDTO guestModifyDTO){
        // 아직 프론트가 json으로 할지 말지 안정해져서 매개변수 간단하게 넣음

        int check = guestService.modifyGuest(guestModifyDTO);
        if(check == 0){
            log.info("modify fail");
            return "";
        }
        return "";
    }

    @DeleteMapping("/deleteGuest/{gno}")
    public String deleteGuest(@PathVariable(value = "gno") Long gno){
        guestService.deleteGuest(gno);
        return "";
    }

}
