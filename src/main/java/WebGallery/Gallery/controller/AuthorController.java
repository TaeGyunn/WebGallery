package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.AuthorModifyDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authorJoinForm")
    public String authorJoinForm(){return "/authorJoin";}

    @GetMapping("/authorDeleteForm")    //삭제시 guest와 함께 삭제할지 아니면 author만 삭제할지 선택하는것도 좋을거같음
    public String authorDeleteForm(){return "";}

    @GetMapping("/authorModifyForm")
    public String authorModifyForm(){return "";}

    @GetMapping("/author_work/{page}/{nick}")
    public ResponseEntity<List<PageAuthorDTO>> showWork(@PathVariable(value = "page") int page,
                                                        @PathVariable(value = "nick") String nick){

        return ResponseEntity.ok(authorService.showWork(nick, page));
    }

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

    @DeleteMapping("/authorDelete/{gno}")
    public String authorDelete(@PathVariable(value = "gno") Long gno){

        authorService.authorDelete(gno);

        return "";
    }






}
