package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthorJoinDTO {

    private Long guestNo;

    private String sns;
    
    private String comment;
    
    @NotEmpty(message = "썸네일을 추가해주세요")
    private MultipartFile tumb;
    
}
