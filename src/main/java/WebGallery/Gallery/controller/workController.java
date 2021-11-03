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
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class workController {

    private final WorkService workService;
    private final WorkRepository workRepository;

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

    @GetMapping("/workPage/{page}/{size}")
    public Page<PageWorkDTO> getWorks(@PathVariable(value = "page") Integer page,
                               @PathVariable(value = "size") Integer size,
                               Pageable pageable){

        Page<PageWorkDTO> works = workService.workPage(page, size);

        return works;
    }

    @PostMapping("/insertWork")
    public String insertWork(InsertWorkDTO insertWorkDTO){

        log.info(insertWorkDTO.toString());
        if(insertWorkDTO.getTags().size() > 3){
            log.info("tag는 3개 까지만 가능합니다.");
            return "";
        }
        int check = workService.InsertWork(insertWorkDTO);

        return "";
    }




    @PutMapping("/modifyWork")
    public String modifyWork(ModifyWorkDTO modifyWorkDTO){

        return "";
    }

    @DeleteMapping("/deleteWork/{workNo}")
    public String deleteWork(@PathVariable(value = "workNo") Long workNo){
        log.info("workNo : "+workNo);

        workService.deleteWork(workNo);

        return "";
    }


}
