package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.dto.PageAlbumDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.service.AlbumService;
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

    @GetMapping("/createAlbumForm")
    public String createAlbumForm(){
        return "";
    }

    @GetMapping("/addWorkToAlbumForm")
    public String addWorkToAlbumForm(){
        return "";
    }

    @GetMapping("/deleteAlbumForm")
    public String deleteAlbumForm(){
        return "";
    }
    
    //앨범리스트 가져오기
    @GetMapping("/guest/showAlbumList/{gno}")
    public ResponseEntity<List<PageAlbumDTO>> showAlbumList(@PathVariable(name = "gno") Long gno){
        List<PageAlbumDTO> albumList= albumService.showAlbumList(gno);
        return ResponseEntity.ok(albumList);
    }

    // 앨범 생성
    @PostMapping("/guest/createAlbum")
    public ResponseEntity<Map<String, String>> createAlbum(CreateAlbumDTO createAlbumDTO){

        int check = 0;
        check = albumService.createAlbum(createAlbumDTO);
        Map<String, String > map = new HashMap<>();
        if(check == 0){
            log.info("앨범 생성 실패");
            map.put("앨범생성", "실패");
            return ResponseEntity.ok(map);
        }
        map.put("앨범생성","성공");
        return ResponseEntity.ok(map);
    }
    
    //앨범에 작업물 추가
    @PostMapping("/addWorkToAlbum")
    public ResponseEntity<Map<String, String>> addWorkToAlbum(AddWorkToAlbumDTO addWorkToAlbumDTO){
        int check = 0;
        Map<String, String > map = new HashMap<>();
        check = albumService.addWorkToAlbum(addWorkToAlbumDTO);
        if(check == 0){
            log.info("addWorkToAlbumForm");
            map.put("앨범_작업물생성", "실패");
            return ResponseEntity.ok(map);
        }
        map.put("앨범_작업물생성", "성공");
        return ResponseEntity.ok(map);
    }

    // 앨범 삭제
    @DeleteMapping("/guest/deleteAlbum/{ano}")
    public ResponseEntity deleteAlbum(@PathVariable(value = "ano") Long ano){

        albumService.deleteAlbum(ano);
        return new ResponseEntity(HttpStatus.OK);
    }

    //앨범에 작업물 삭제
    @DeleteMapping("/guest/deleteWorkToAlbum/{ano}/{wno}")
    public ResponseEntity deleteWorkToAlbum(@PathVariable(value = "ano") Long ano,
                                            @PathVariable(value="wno") Long wno){

        albumService.deleteWorkToAlbum(ano, wno);
        return new ResponseEntity(HttpStatus.OK);
    }




}
