package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.CreateNoticeDTO;
import WebGallery.Gallery.entity.Notice;
import WebGallery.Gallery.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    //공지사항 불러오기기
   @GetMapping("/getAllNotice/{page}/{size}")
    public ResponseEntity<Page<Notice>> getNotice(@PathVariable(value = "page") Integer page,
                                    @PathVariable(value = "size") Integer size){

        Page<Notice> notices = adminService.getNotice(page, size);
        if(notices == null){
            log.info("게시판 불러오기 실패");
            return null;
        }
        return ResponseEntity.ok(notices);
    }

    // 공지사항 작성
    @PostMapping("/Notice")
    public ResponseEntity createNotice(@RequestBody CreateNoticeDTO createNoticeDTO){

        log.info(createNoticeDTO.toString());
        adminService.createNotice(createNoticeDTO);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    //공지사항 삭제
    @DeleteMapping("/deleteNotice/{adno}")
    public ResponseEntity deleteNotice(@PathVariable(value = "adno") Long adno){

        adminService.deleteNotice(adno);

        return new ResponseEntity(HttpStatus.OK);
    }

}
