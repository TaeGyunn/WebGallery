package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.dto.PageAlbumDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.service.AlbumService;
import WebGallery.Gallery.util.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private final AlbumService albumService;
    private final Response response;

    //앨범리스트 가져오기
    @ApiOperation(value="앨범 리스트", notes = "앨범 리스트를 가져온다.")
    @ApiImplicitParam(name ="gno", value = "게스트 no", example= "19", dataType = "Long", paramType = "path")
    @GetMapping("/guest/showAlbumList/{gno}")
    public ResponseEntity<?> showAlbumList(@PathVariable(name = "gno") Long gno){
        List<PageAlbumDTO> albumList= albumService.showAlbumList(gno);
        return response.success(albumList, "앨범리스트", HttpStatus.OK);
    }

    // 앨범 생성
    @ApiOperation(value="앨범 생성", notes = "앨범을 생성한다.")
    @PostMapping("/guest/createAlbum")
    public ResponseEntity<?> createAlbum(@RequestBody  CreateAlbumDTO createAlbumDTO){

        return albumService.createAlbum(createAlbumDTO);
    }
    
    //앨범에 작업물 추가
    @ApiOperation(value="앨범에 작업물 추가", notes = "앨범에 작업물을 추가한다.")
    @PostMapping("/guest/addWorkToAlbum")
    public ResponseEntity<?> addWorkToAlbum(@RequestBody AddWorkToAlbumDTO addWorkToAlbumDTO){
        int check = 0;
        Map<String, String > map = new HashMap<>();
        check = albumService.addWorkToAlbum(addWorkToAlbumDTO);
        if(check == 0){
            map.put("workToalbum", "fail");
            return response.fail(map, "작업물 추가 실패", HttpStatus.BAD_REQUEST);
        }
        map.put("workToalbum", "success");
        return response.success(map, "작업물 추가 성공", HttpStatus.OK);
    }


    // 앨범 삭제
    @ApiOperation(value="앨범 삭제", notes = "앨범을 삭제한다.")
    @ApiImplicitParam(name ="ano", value = "앨범 no", example= "2", dataType = "Long", paramType = "path")
    @DeleteMapping("/guest/deleteAlbum/{ano}")
    public ResponseEntity<?> deleteAlbum(@PathVariable(value = "ano") Long ano){

        albumService.deleteAlbum(ano);
        Map<String, String> map = new HashMap<>();
        map.put("album_delete", "success");
        return response.success(map, "앨범 삭제 완료",HttpStatus.OK);
    }

    //앨범에 작업물 삭제
    @ApiOperation(value="앨범에 작업물 삭제", notes = "앨범 안에 있는 작업물을 삭제한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="ano", value = "앨범 no", example="2", dataType = "Long", paramType = "path")
            ,@ApiImplicitParam(name="wno", value = "작품 no", example="8", dataType = "Long", paramType = "path")})
    @DeleteMapping("/guest/deleteWorkToAlbum/{ano}/{wno}")
    public ResponseEntity<?> deleteWorkToAlbum(@PathVariable(value = "ano") Long ano,
                                            @PathVariable(value="wno") Long wno){

        albumService.deleteWorkToAlbum(ano, wno);
        Map<String, String> map = new HashMap<>();
        map.put("album_work_delete", "success");
        return response.success(map, "작업물 삭제 완료", HttpStatus.OK);
    }


}
