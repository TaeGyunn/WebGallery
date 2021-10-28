package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthorModifyDTO {

    private Long gno;

    private String sns;

    private String comment;

    private MultipartFile thumb;
}
