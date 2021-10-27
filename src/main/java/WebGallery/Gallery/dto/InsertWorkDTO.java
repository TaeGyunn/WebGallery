package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class InsertWorkDTO {

    private Long guest;           // 게스트
    
    private String comment;     // 내용

    private String thema;       // 테마

    private String name;        // 이름

    private MultipartFile file;

    private List<String> tags;

    public InsertWorkDTO(Long guest, String comment, String thema, String name, MultipartFile file, List<String> tags) {
        this.guest = guest;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.file = file;
        this.tags = tags;
    }
}
