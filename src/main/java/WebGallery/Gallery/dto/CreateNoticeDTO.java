package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class CreateNoticeDTO {
    
    
    private Long adno;  //admin no
    
    private String title;   //제목
    
    private String content; //내용

    public CreateNoticeDTO(Long adno, String title, String content){

        this.adno = adno;
        this.title = title;
        this.content = content;
    }
    

}
