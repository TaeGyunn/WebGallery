package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.service.AuthorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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
}
