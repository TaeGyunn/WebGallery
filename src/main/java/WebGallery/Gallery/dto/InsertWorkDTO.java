package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InsertWorkDTO {

    private Long gno;           // 게스트
    
    private String comment;     // 내용

    private String thema;       // 테마

    private String name;        // 이름


    private List<String> tags;

    public InsertWorkDTO(Long gno, String comment, String thema, String name, List<String> tags) {
        this.gno = gno;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.tags = tags;
    }
}
