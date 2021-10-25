package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class InsertWorkDTO {

    private Long guest;           // 게스트
    
    private String content;     // 내용

    private String thema;       // 테마

    private String name;        // 이름

    private MultipartFile file;

}
