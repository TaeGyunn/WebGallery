package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.ChangePwDTO;
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

import javax.servlet.http.HttpSession;
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


    // 작업물 좋아요
    @GetMapping("/likeWork/{gno}/{wno}")
    public ResponseEntity likeWork(@PathVariable("gno") Long gno,
                                   @PathVariable("wno") Long wno){

        workService.likeWork(gno, wno);

        return new ResponseEntity(HttpStatus.OK);
    }

    
    //게스트 수정
    @PutMapping("/modifyGuest")
    public ResponseEntity<Map<String,String>> modifyGuest(@Valid @RequestBody GuestModifyDTO guestModifyDTO,
                                                          @RequestHeader Map<String, Object> header){
        if(header.containsKey("X-AUTH-TOKEN")){
            Map<String, String> map = new HashMap<>();
            int check = guestService.modifyGuest(guestModifyDTO);
            if(check == 0){
                log.info("modify fail");
                return null;
            }
            map.put("수정","성공");
            return ResponseEntity.ok(map);
        }
        return null;
    }
    //비밀번호 변경
    @PostMapping("/repw")
    public ResponseEntity rePw(@RequestBody ChangePwDTO changePwDTO, HttpSession session){

        log.info(changePwDTO.toString());
        int check = 0 ;
        String nick =  (String)session.getAttribute("LoginNick");
        check = guestService.changePw(changePwDTO.getPw(), nick);
        if(check == 0){
            return null;
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    
    //게스트 삭제
    @DeleteMapping("/deleteGuest/{gno}")
    public ResponseEntity<Map<String, String>> deleteGuest(@PathVariable(value = "gno") Long gno){
        int check = guestService.deleteGuest(gno);
        Map<String, String> map = new HashMap<>();
        if(check == 0){
            map.put("delete", "fail");
        }else{
            map.put("delete", " success");
        }
        return ResponseEntity.ok(map);
    }

}
