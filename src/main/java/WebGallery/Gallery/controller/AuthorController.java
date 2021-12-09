package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.AuthorModifyDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/author")
@Slf4j
public class AuthorController {

    private final AuthorService authorService;
    private final Response response;

    //작가 닉네임 검색
    @GetMapping("/author_work/{page}/{nick}")
    public ResponseEntity<?> showWork(@PathVariable(value = "page") int page,
                                                        @PathVariable(value = "nick") String nick){

        return response.success(authorService.showWork(nick,page),"작가 닉네임 검색",HttpStatus.OK);
    }
    
    //작가 가입
    @PostMapping("/authorJoin")
    public ResponseEntity<?> authorJoin(@RequestPart("join") AuthorJoinDTO authorJoinDTO,
                                        @RequestPart("thumb") MultipartFile thumb){


        return authorService.authorJoin(authorJoinDTO, thumb);
    }
    
    //작가수정
    @PutMapping("/authorModify")
    public ResponseEntity<?> authorModify(@RequestPart("modify") AuthorModifyDTO authorModifyDTO,
                                                            @RequestPart("thumb") MultipartFile thumb){


        Map<String, String > map = new HashMap<>();
        int check = authorService.authorModify(authorModifyDTO, thumb);

        if(check == 0){
            map.put("modify", "fail");
            return response.fail(map, "작가 수정 실패", HttpStatus.BAD_REQUEST);
        }
        map.put("modify", "success");
        return response.success(map, "작가 수정 성공", HttpStatus.OK);
    }
    
    //작가 삭제
    @DeleteMapping("/authorDelete/{gno}")
    public ResponseEntity<?> authorDelete(@PathVariable(value = "gno") Long gno){

        return authorService.authorDelete(gno);
    }






}
