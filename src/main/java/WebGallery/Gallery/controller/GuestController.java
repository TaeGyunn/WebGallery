package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.ChangePwDTO;
import WebGallery.Gallery.dto.GuestModifyDTO;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.service.WorkService;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
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
    private final Response response;


    // 작업물 좋아요
    @GetMapping("/likeWork/{gno}/{wno}")
    public ResponseEntity<?> likeWork(@PathVariable("gno") Long gno,
                                   @PathVariable("wno") Long wno){

        return workService.likeWork(gno, wno);

    }
    
    //게스트 수정
    @PutMapping("/modifyGuest")
    public ResponseEntity<?> modifyGuest(@Validated @RequestBody GuestModifyDTO guestModifyDTO,
                                                          Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }

        Map<String, String> map = new HashMap<>();
        int check = guestService.modifyGuest(guestModifyDTO);
        if(check == 0){
          return response.success("수정 사항 없음");
        }
        map.put("modify","success");
        return response.success(map, "수정 완료", HttpStatus.OK);
    }

    //비밀번호 변경
    @PostMapping("/repw")
    public ResponseEntity<?> rePw(@Validated @RequestBody ChangePwDTO changePwDTO, Errors errors){

        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return guestService.changePw(changePwDTO);
    }
    
    //게스트 삭제
    @DeleteMapping("/deleteGuest/{gno}")
    public ResponseEntity<?> deleteGuest(@PathVariable(value = "gno") Long gno){

         return guestService.deleteGuest(gno);
    }

}
