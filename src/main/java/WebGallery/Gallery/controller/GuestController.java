package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestModifyDTO;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GuestController {

    private final GuestService guestService;

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

    
    //게스트 수정
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
    
    //게스트 삭제
    @DeleteMapping("/deleteGuest/{gno}")
    public String deleteGuest(@PathVariable(value = "gno") Long gno){
        guestService.deleteGuest(gno);
        return "";
    }



}
