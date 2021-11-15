package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ModifyWorkDTO {

    private Long wno;

    private String comment;

    private String thema;

    private String name;


    private List<String> tags;

    public ModifyWorkDTO(Long wno, String comment, String thema, String name, List<String> tags) {
        this.wno = wno;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.tags = tags;
    }
}
