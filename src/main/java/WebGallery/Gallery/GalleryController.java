package WebGallery.Gallery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class GalleryController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, HttpSession session){
        log.info("W'Gallery");
        return "/front/main/index";
    }

}
