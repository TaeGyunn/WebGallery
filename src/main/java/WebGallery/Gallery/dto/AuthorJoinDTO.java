package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class AuthorJoinDTO {

    @ApiModelProperty(required = true, value="게스트 no", example = "19", dataType = "Long")
    private Long gno;

    @ApiModelProperty(required = true, value="SNS id", example = "test_gyun", dataType = "String")
    private String sns;

    @ApiModelProperty(required = true, value="작가 소개", example = "Hi I'm TaeGyun", dataType = "String")
    private String comment;

    public AuthorJoinDTO(Long gno, String sns, String comment){
        this.gno = gno;
        this.sns = sns;
        this.comment = comment;
    }
    
}
