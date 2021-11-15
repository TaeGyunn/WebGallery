package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class AuthorJoinDTO {

    private Long gno;

    private String sns;
    
    private String comment;

    public AuthorJoinDTO(Long gno, String sns, String comment){
        this.gno = gno;
        this.sns = sns;
        this.comment = comment;
    }
    
}
