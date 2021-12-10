package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.dto.PageAlbumDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.service.AlbumService;
import WebGallery.Gallery.util.Response;
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
    @GetMapping("/guest/showAlbumList/{gno}")
    public ResponseEntity<?> showAlbumList(@PathVariable(name = "gno") Long gno){
        List<PageAlbumDTO> albumList= albumService.showAlbumList(gno);
        return response.fail(albumList, "앨범리스트", HttpStatus.OK);
    }

    // 앨범 생성
    @PostMapping("/guest/createAlbum")
    public ResponseEntity<?> createAlbum(@RequestBody  CreateAlbumDTO createAlbumDTO){

        int check = 0;
        check = albumService.createAlbum(createAlbumDTO);
        Map<String, String > map = new HashMap<>();
        if(check == 0){
            map.put("albumCreate", "fail");
            return response.fail(map, "앨범 생성 실패", HttpStatus.BAD_REQUEST);
        }
        map.put("albumCreate","success");

        return response.success(map, "앨범 생성 성공", HttpStatus.OK);
    }
    
    //앨범에 작업물 추가
    @PostMapping("/addWorkToAlbum")
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
    @DeleteMapping("/guest/deleteAlbum/{ano}")
    public ResponseEntity<?> deleteAlbum(@PathVariable(value = "ano") Long ano){

        albumService.deleteAlbum(ano);
        Map<String, String> map = new HashMap<>();
        map.put("album_delete", "success");
        return response.success(map, "앨범 삭제 완료",HttpStatus.OK);
    }

    //앨범에 작업물 삭제
    @DeleteMapping("/guest/deleteWorkToAlbum/{ano}/{wno}")
    public ResponseEntity<?> deleteWorkToAlbum(@PathVariable(value = "ano") Long ano,
                                            @PathVariable(value="wno") Long wno){

        albumService.deleteWorkToAlbum(ano, wno);
        Map<String, String> map = new HashMap<>();
        map.put("album_work_delete", "success");
        return response.success(map, "작업물 삭제 완료", HttpStatus.OK);
    }




}
