package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.MailDTO;
import WebGallery.Gallery.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;
    
    //메일보내기(삭제 예정)
    @PostMapping("/mail")
    public void sendMail(MailDTO mailDTO){
        log.info(mailDTO.toString());
        mailService.sendMail(mailDTO);
    }

}