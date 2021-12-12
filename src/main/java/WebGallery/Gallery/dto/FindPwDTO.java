package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindPwDTO {

    @ApiModelProperty(required = true, value="가입 시 기입했던 email", example = "Test04140@naver.com", dataType = "String")
    private String email;

    @ApiModelProperty(required = true, value="가입 시 기입했던 name", example = "Test0414", dataType = "String")
    private String name;
}
