package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.dto.ModifyWorkDTO;
import WebGallery.Gallery.dto.PageWorkDTO;
import WebGallery.Gallery.service.AuthorService;
import WebGallery.Gallery.service.WorkService;
import WebGallery.Gallery.util.Response;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class workController {

    private final WorkService workService;
    private final AuthorService authorService;
    private final Response response;

    //작가 닉네임 검색
    @ApiOperation(value="작가 닉네임 검색", notes = "작가 검색시 작가에 대한 정보, 작품이 함께 페이징을 한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name ="page", value = "페이지 번호", example= "1", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name="nick", value = "검색할 작가 닉네임", example="Gyun0940", dataType = "String", paramType = "path")})
    @GetMapping("/author_work/{page}/{nick}")
    public ResponseEntity<?> showWork(@PathVariable(value = "page") int page,
                                      @PathVariable(value = "nick") String nick){

        return response.success(authorService.showWork(nick,page),"작가 닉네임 검색",HttpStatus.OK);
    }

    //작업물 전체 페이징
    @ApiOperation(value="작업물 전체 페이징", notes = "모든 작업물 페이징을 한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name ="page", value = "페이지 번호", example="1", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name="size", value = "한 페이지에 나타낼 갯수", example="5", dataType = "int", paramType = "path")})
    @GetMapping("/workPage/{page}/{size}")
    public ResponseEntity<?> getWorks(@PathVariable(value = "page") Integer page,
                               @PathVariable(value = "size") Integer size){

        Page<PageWorkDTO> works = workService.workPage(page, size);

        return response.success(works,"작업물 전체 페이징", HttpStatus.OK);
    }
    
    //테마별 아이템 페이징
    @ApiOperation(value="테마별 아이템 페이징", notes = "테마 검색시 테마별 작업별 페이징 한다.")
    @ApiImplicitParams({
        @ApiImplicitParam(name ="page", value = "페이지 번호", example="1", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name="size", value = "한 페이지에 나타낼 갯수", example="5", dataType = "int", paramType = "path")
            ,@ApiImplicitParam(name="thema", value = "테마 이름", example = "test", dataType = "String", paramType = "path")})
    @GetMapping("/workThemaPage/{page}/{size}/{thema}")
    public ResponseEntity<?> getThemaWorks(@PathVariable(value = "page") Integer page,
                                           @PathVariable(value = "size") Integer size,
                                           @PathVariable(value = "thema") String thema){

        Page<PageWorkDTO> works = workService.workThemaPage(page,size,thema);
        return response.success(works,"테마별 아이템 페이징", HttpStatus.OK);
    }

    
    //작업물 추가
    @ApiOperation(value="작업물 추가", notes = "작가가 작업물을 추가한다.")
    @PostMapping("/author/insertWork")
    public ResponseEntity<?> insertWork(@RequestPart("insert") InsertWorkDTO insertWorkDTO,
                                     @RequestPart("photo")@ApiParam MultipartFile photo){

        Map<String, String> map = new HashMap<>();
        if(insertWorkDTO.getTags().size() > 3){
            map.put("add", "fail");
            return response.fail(map,"tag는 3개 까지만 가능합니다", HttpStatus.BAD_REQUEST);
        }

        int check = workService.InsertWork(insertWorkDTO, photo);
        if(check == 0){
            map.put("add", "fail");
            return response.fail(map,"작업물 추가 실패하였습니다", HttpStatus.BAD_REQUEST);
        }
        map.put("add", "success");
        return response.success(map,"작업물 추가 성공하였습니다.", HttpStatus.OK);
    }
    
    //작업물 수정
    @ApiOperation(value="작업물 수정", notes = "작업물 수정을 한다.")
    @PutMapping("/author/modifyWork")
    public ResponseEntity<?> modifyWork(@RequestPart("modify") ModifyWorkDTO modifyWorkDTO,
                                     @RequestPart("photo") MultipartFile photo){

        Map<String, String> map = new HashMap<>();
        int check = workService.modifyWork(modifyWorkDTO,photo);
        if(check == 0){
            map.put("modify","fail");
            response.fail(map, "작업물 수정 실패", HttpStatus.BAD_REQUEST);
        }

        return response.success(map, "작업물 수정 성공", HttpStatus.OK);
    }
    
    //작업물 삭제
    @ApiOperation(value="작업물 삭제", notes = "작업물을 삭제한다.")
    @ApiImplicitParam(name ="workNo", value = "작품 번호", example="8", dataType = "Long", paramType = "path")
    @DeleteMapping("/deleteWork/{workNo}")
    public ResponseEntity<?> deleteWork(@PathVariable(value = "workNo") Long workNo){

        return workService.deleteWork(workNo);

    }


}
