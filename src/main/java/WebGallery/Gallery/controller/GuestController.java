package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.GuestModifyDTO;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.service.WorkService;
import WebGallery.Gallery.util.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final WorkService workService;

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

    // 작업물 좋아요
    @GetMapping("/likeWork/{gno}/{wno}")
    public ResponseEntity likeWork(@PathVariable("gno") Long gno,
                                   @PathVariable("wno") Long wno){

        workService.likeWork(gno, wno);

        return new ResponseEntity(HttpStatus.OK);
    }

    
    //게스트 수정
    @PutMapping("/modifyGuest")
    public ResponseEntity<Map<String,String>> modifyGuest(@Valid @RequestBody GuestModifyDTO guestModifyDTO){
        // 아직 프론트가 json으로 할지 말지 안정해져서 매개변수 간단하게 넣음

        Map<String, String> map = new HashMap<>();
        int check = guestService.modifyGuest(guestModifyDTO);
        if(check == 0){
            log.info("modify fail");
            map.put("수정", "실패");
            return ResponseEntity.ok(map);

        }
        map.put("수정","성공");
        return ResponseEntity.ok(map);

    }
    
    //게스트 삭제
    @DeleteMapping("/deleteGuest/{gno}")
    public ResponseEntity deleteGuest(@PathVariable(value = "gno") Long gno){
        guestService.deleteGuest(gno);

        return new ResponseEntity(HttpStatus.OK);
    }

}
