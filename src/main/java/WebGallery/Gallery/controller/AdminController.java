package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.CreateNoticeDTO;
import WebGallery.Gallery.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/board")
    public ResponseEntity createNotice(CreateNoticeDTO createNoticeDTO){

        log.info(createNoticeDTO.toString());
        adminService.createNotice(createNoticeDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

}
