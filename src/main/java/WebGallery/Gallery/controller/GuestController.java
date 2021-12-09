package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.WorkService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guest")
@Slf4j
public class GuestController {

    private final GuestService guestService;
    private final AuthorService authorService;
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
        }else if(check == 2){
            return response.fail("해당하는 유저가 존재하지 않습니다", HttpStatus.BAD_REQUEST);

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
    @PostMapping("/deleteGuest")
    public ResponseEntity<?> deleteGuest(@Validated @RequestBody DeleteGuestDTO deleteGuestDTO, Errors errors){

        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }
        LogoutDTO logoutDTO = new LogoutDTO(deleteGuestDTO);
        guestService.logout(logoutDTO);
        return guestService.deleteGuest(deleteGuestDTO);
    }

    //작가 가입
    @PostMapping("/authorJoin")
    public ResponseEntity<?> authorJoin(@RequestPart("join") AuthorJoinDTO authorJoinDTO,
                                        @RequestPart("thumb") MultipartFile thumb){


        return guestService.authorJoin(authorJoinDTO, thumb);
    }

    //작가 삭제
    @DeleteMapping("/authorDelete/{gno}")
    public ResponseEntity<?> authorDelete(@PathVariable(value = "gno") Long gno){

        return authorService.authorDelete(gno);
    }

}
