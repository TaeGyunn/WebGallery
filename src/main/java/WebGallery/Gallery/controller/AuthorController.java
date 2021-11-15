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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/authorJoinForm")
    public String authorJoinForm(){return "/authorJoin";}

    @GetMapping("/authorDeleteForm")    //삭제시 guest와 함께 삭제할지 아니면 author만 삭제할지 선택하는것도 좋을거같음
    public String authorDeleteForm(){return "";}

    @GetMapping("/authorModifyForm")
    public String authorModifyForm(){return "";}
    
    //작가 닉네임 검색
    @GetMapping("/author_work/{page}/{nick}")
    public ResponseEntity<List<PageAuthorDTO>> showWork(@PathVariable(value = "page") int page,
                                                        @PathVariable(value = "nick") String nick){

        return ResponseEntity.ok(authorService.showWork(nick, page));
    }
    
    //작가 가입
    @PostMapping("/authorJoin")
    public ResponseEntity<Map<String, String>> authorJoin(@RequestPart("join") AuthorJoinDTO authorJoinDTO,
                                                          @RequestPart("thumb") MultipartFile thumb){

        log.info(authorJoinDTO.toString());
        Map<String, String> map = new HashMap<>();

        int check = authorService.authorJoin(authorJoinDTO, thumb);

        //성공
        if(check == 1){
            map.put("가입", "성공");
            return ResponseEntity.ok(map);
        }
        map.put("가입", "실패");
        return ResponseEntity.ok(map);
    }
    
    //작가수정
    @PutMapping("/authorModify")
    public ResponseEntity<Map<String, String>> authorModify(@RequestPart("modify") AuthorModifyDTO authorModifyDTO,
                                                            @RequestPart("thumb") MultipartFile thumb){

        log.info(authorModifyDTO.toString());
        Map<String, String > map = new HashMap<>();
        int check = authorService.authorModify(authorModifyDTO, thumb);

        if(check == 0){
            log.info("modify fail");
            map.put("작가 수정", "실패");
            return ResponseEntity.ok(map);
        }
        map.put("작가 수정", "성공");
        return ResponseEntity.ok(map);
    }
    
    //작가 삭제
    @DeleteMapping("/authorDelete/{gno}")
    public ResponseEntity authorDelete(@PathVariable(value = "gno") Long gno){

        authorService.authorDelete(gno);

        return new ResponseEntity(HttpStatus.OK);
    }






}
