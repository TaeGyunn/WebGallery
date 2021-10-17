package WebGallery.Gallery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GalleryController {

    @RequestMapping("/")
    @ResponseBody
    public String home(){
        return "helloHome";
    }
}
