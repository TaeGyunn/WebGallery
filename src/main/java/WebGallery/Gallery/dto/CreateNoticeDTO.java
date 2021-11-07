package WebGallery.Gallery.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class CreateNoticeDTO {
    
    
    private Long adno;  //admin no
    
    private String title;   //제목
    
    private String content; //내용
    

}
