package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.service.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AlbumController {

    private AlbumService albumService;

    @GetMapping("/createAlbumForm")
    public String createAlbumForm(){
        return "";
    }

    @GetMapping("/addWorkToAlbumForm")
    public String addWorkToAlbumForm(){
        return "";
    }

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




}
