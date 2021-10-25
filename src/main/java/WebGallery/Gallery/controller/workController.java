//package WebGallery.Gallery.controller;
//
//import WebGallery.Gallery.dto.InsertWorkDTO;
//import WebGallery.Gallery.service.WorkService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class workController {
//
//    private WorkService workService;
//
//    @PostMapping("/insertWork")
//    public String insertWork(InsertWorkDTO insertWorkDTO){
//
//        log.info(insertWorkDTO.toString());
//
//        int check = workService.InsertWork(insertWorkDTO);
//
//        return "";
//    }
//
//
//}
