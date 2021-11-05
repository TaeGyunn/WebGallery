package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.dto.ModifyWorkDTO;
import WebGallery.Gallery.dto.PageWorkDTO;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.repository.WorkRepository;
import WebGallery.Gallery.service.WorkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.Message;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class workController {

    private final WorkService workService;

    @GetMapping("/insertWorkForm")
    public String insertWorkForm(){
        return "";
    }

    @GetMapping("/deleteWorkForm")
    public String deleteWorkForm(){
        return "";
    }

    @GetMapping("/modifyWorkForm")
    public String modifyWorkForm(){
        return "";
    }
    
    //작업물 전체 페이징
    @GetMapping("/workPage/{page}/{size}")
    public Page<PageWorkDTO> getWorks(@PathVariable(value = "page") Integer page,
                               @PathVariable(value = "size") Integer size,
                               Pageable pageable){

        Page<PageWorkDTO> works = workService.workPage(page, size);

        return works;
    }
    
    //테마별 아이템 페이징
    @GetMapping("/workThemaPage/{page}/{size}/{thema}")
    public ResponseEntity<Page<PageWorkDTO>> getThemaWorks(@PathVariable(value = "page") Integer page,
                                           @PathVariable(value = "size") Integer size,
                                           @PathVariable(value = "thema") String thema){

        Page<PageWorkDTO> works = workService.workThemaPage(page,size,thema);
        return ResponseEntity.ok(works);
    }
    
    
    // 작업물 좋아요
    @GetMapping("/likeWork/{guestNo}/{workNo}")
    public ResponseEntity likeWork(@PathVariable("guestNo") Long gno,
                                   @PathVariable("workNo") Long wno){

        workService.likeWork(gno, wno);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    //작업물 추가
    @PostMapping("/insertWork")
    public ResponseEntity insertWork(InsertWorkDTO insertWorkDTO){

        log.info(insertWorkDTO.toString());
        if(insertWorkDTO.getTags().size() > 3){
            log.info("tag는 3개 까지만 가능합니다.");
            return new ResponseEntity(HttpStatus.OK);
        }
        int check = workService.InsertWork(insertWorkDTO);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    //작업물 수정
    @PutMapping("/modifyWork")
    public ResponseEntity modifyWork(ModifyWorkDTO modifyWorkDTO){

        workService.modifyWork(modifyWorkDTO);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    //작업물 삭제
    @DeleteMapping("/deleteWork/{workNo}")
    public ResponseEntity deleteWork(@PathVariable(value = "workNo") Long workNo){
        log.info("workNo : "+workNo);

        workService.deleteWork(workNo);

        return new ResponseEntity(HttpStatus.OK);
    }


}
