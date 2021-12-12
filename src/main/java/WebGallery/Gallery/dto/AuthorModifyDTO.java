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
public class AuthorModifyDTO {

    @ApiModelProperty(required = true, value="작가 no", example = "18", dataType = "Long")
    private Long gno;

    @ApiModelProperty(required = true, value="변경할 sns id", dataType = "String")
    private String sns;

    @ApiModelProperty(required = true, value="변경할 작가 소개" , dataType = "String")
    private String comment;


    public AuthorModifyDTO(Long gno, String sns, String comment) {
        this.gno = gno;
        this.sns = sns;
        this.comment = comment;
    }
}
