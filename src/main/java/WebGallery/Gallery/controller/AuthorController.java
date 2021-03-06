package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.AuthorModifyDTO;
import WebGallery.Gallery.dto.DeleteAuthorDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.util.Response;
import io.swagger.annotations.ApiOperation;
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

    
    //작가수정
    @ApiOperation(value="작가 정보 수정", notes = "작가 정보를 수정한다.")
    @PutMapping("/authorModify")
    public ResponseEntity<?> authorModify(@RequestPart("modify") AuthorModifyDTO authorModifyDTO,
                                                            @RequestPart("thumb") MultipartFile thumb){


        Map<String, Boolean > map = new HashMap<>();
        int check = authorService.authorModify(authorModifyDTO, thumb);

        if(check == 0){
            map.put("modify", false);
            return response.fail(map, "작가 수정 실패", HttpStatus.BAD_REQUEST);
        }
        map.put("modify", true);
        return response.success(map, "작가 수정 성공", HttpStatus.OK);
    }
}
