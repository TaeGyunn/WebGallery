package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReissueDTO {

    @ApiModelProperty(required = true, value="로그인시 발급되었던 accessToken", dataType = "String")
    @NotEmpty(message = "accessToken을 입력해주세요")
    private String accessToken;

    @ApiModelProperty(required = true, value="로그인시 발급되었던 refreshToken", dataType = "String")
    @NotEmpty(message = "refreshToken을 입력해주세요")
    private String refreshToken;
}
