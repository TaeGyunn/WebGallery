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

import java.util.List;

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
    @GetMapping("/showAlbumList/{gno}")
    public ResponseEntity<List<PageAlbumDTO>> showAlbumList(@PathVariable(name = "gno") Long gno){
        List<PageAlbumDTO> albumList= albumService.showAlbumList(gno);
        return ResponseEntity.ok(albumList);
    }

    // 앨범 생성
    @PostMapping("/createAlbum")
    public String createAlbum(CreateAlbumDTO createAlbumDTO){

        int check = 0;
        check = albumService.createAlbum(createAlbumDTO);
        if(check == 0){
            log.info("return createAlbumForm");
            return "/";
        }
        return "";
    }
    
    //앨범에 작업물 추가
    @PostMapping("/addWorkToAlbum")
    public String addWorkToAlbum(AddWorkToAlbumDTO addWorkToAlbumDTO){
        int check = 0;
        check = albumService.addWorkToAlbum(addWorkToAlbumDTO);
        if(check == 0){
            log.info("return addWorkToAlbumForm");
            return"/";
        }
        return "";
    }

    // 앨범 삭제
    @DeleteMapping("/deleteAlbum/{ano}")
    public ResponseEntity deleteAlbum(@PathVariable(value = "ano") Long ano){

        albumService.deleteAlbum(ano);
        return new ResponseEntity(HttpStatus.OK);
    }

    //앨범에 작업물 삭제
    @DeleteMapping("/deleteWorkToAlbum/{ano}/{wno}")
    public ResponseEntity deleteWorkToAlbum(@PathVariable(value = "ano") Long ano,
                                            @PathVariable(value="wno") Long wno){

        albumService.deleteWorkToAlbum(ano, wno);
        return new ResponseEntity(HttpStatus.OK);
    }


}
