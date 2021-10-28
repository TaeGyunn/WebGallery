package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.AuthorModifyDTO;
import WebGallery.Gallery.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authorJoinForm")
    public String authorJoinForm(){return "/authorJoin";}

    @PostMapping("/authorJoin")
    public String authorJoin(AuthorJoinDTO authorJoinDTO){

        log.info(authorJoinDTO.toString());
        int check = authorService.authorJoin(authorJoinDTO);

        //성공
        if(check == 1){

            return "";
        }
        return "";
    }
    @GetMapping("/authorDeleteForm")    //삭제시 guest와 함께 삭제할지 아니면 author만 삭제할지 선택하는것도 좋을거같음
    public String authorDeleteForm(){return "";}

    @DeleteMapping("/authorDelete")
    public String authorDelete(Long gno){

        authorService.authorDelete(gno);

        return "";
    }

    @GetMapping("/authorModifyForm")
    public String authorModifyForm(){return "";}

    @PutMapping("/authorModify")
    public String authorModify(AuthorModifyDTO authorModifyDTO){

        log.info(authorModifyDTO.toString());
        int check = authorService.authorModify(authorModifyDTO);

        if(check == 0){
            log.info("modify fail");
            return "";
        }
        return "";
    }



}
