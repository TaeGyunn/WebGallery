package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.repository.WorkRepository;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.WorkService;
import WebGallery.Gallery.util.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value="작업물 좋아요 / 취소", notes = "작업물이 좋아요 되있을시 좋아요 취소 / 좋아요가 없을시 좋아요를 한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="gno", value = "게스트 번호", example="19", dataType = "Long", paramType = "path")
            ,@ApiImplicitParam(name="wno", value = "작품 번호", example="8", dataType = "Long", paramType = "path")})
    @GetMapping("/likeWork/{gno}/{wno}")
    public ResponseEntity<?> likeWork(@PathVariable("gno") Long gno,
                                   @PathVariable("wno") Long wno){


        return workService.likeWork(gno, wno);

    }
    
    //게스트 수정
    @ApiOperation(value="게스트 수정", notes = "게스트 정보를 수정한다.")
    @PutMapping("/modifyGuest")
    public ResponseEntity<?> modifyGuest(@Validated @RequestBody GuestModifyDTO guestModifyDTO,
                                                          Errors errors){
        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }

        Map<String, Boolean> map = new HashMap<>();
        int check = guestService.modifyGuest(guestModifyDTO);
        if(check == 0){
            map.put("modify",false);
          return response.success(map,"수정 사항 없음",HttpStatus.OK);
        }else if(check == 2){
            map.put("modify", false);
            return response.fail(map,"해당하는 유저가 존재하지 않습니다", HttpStatus.BAD_REQUEST);

        }
        map.put("modify",true);
        return response.success(map, "수정 완료", HttpStatus.OK);
    }

    //비밀번호 변경
    @ApiOperation(value="비밀번호 변경", notes = "비밀번호를 변경한다.")
    @PostMapping("/repw")
    public ResponseEntity<?> rePw(@Validated @RequestBody ChangePwDTO changePwDTO, Errors errors){

        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }

        return guestService.changePw(changePwDTO);
    }
    
    //게스트 삭제
    @ApiOperation(value="게스트 삭제", notes = "게스트를 삭제한다.")
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
    @ApiOperation(value="작가 가입", notes = "작가 정보 입력 후 권한을 작가로 바꾼다.")
    @PostMapping("/authorJoin")
    public ResponseEntity<?> authorJoin(@RequestPart("join") AuthorJoinDTO authorJoinDTO,
                                        @RequestPart("thumb") MultipartFile thumb){


        return guestService.authorJoin(authorJoinDTO, thumb);
    }

    //작가 삭제
    @ApiOperation(value="작가 탈퇴", notes = "탈퇴한다.")
    @PostMapping("/authorDelete")
    public ResponseEntity<?> authorDelete(@RequestBody DeleteAuthorDTO deleteAuthorDTO){

        return authorService.authorDelete(deleteAuthorDTO.getId());
    }

}
