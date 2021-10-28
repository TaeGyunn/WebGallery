package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AuthorJoinDTO {

    private Long gno;

    private String sns;
    
    private String comment;
    
    @NotEmpty(message = "썸네일을 추가해주세요")
    private MultipartFile thumb;

    public AuthorJoinDTO(Long gno, String sns, String comment, MultipartFile thumb){
        this.gno = gno;
        this.sns = sns;
        this.comment = comment;
        this.thumb = thumb;
    }
    
}
