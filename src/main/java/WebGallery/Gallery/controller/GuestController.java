package WebGallery.Gallery.controller;

import WebGallery.Gallery.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    //이메일 중복 확인
    @GetMapping("/user-emails/{email}/exists")
    public ResponseEntity<Boolean> checkEmailDuplication(@PathVariable String email){
        return ResponseEntity.ok(guestService.checkEmailDuplication(email));
    }
}
