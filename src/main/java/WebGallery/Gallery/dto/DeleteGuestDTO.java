package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@ApiModel
public class DeleteGuestDTO {

    @ApiModelProperty(required = true, value="로그인 했을때 발급받았던 accessToken", dataType = "String")
    @NotEmpty(message = "잘못된 요청입니다")
    private String accessToken;

    @ApiModelProperty(required = true, value="로그인 했을때 발급받았던 refreshToken", dataType = "String")
    @NotEmpty(message = "잘못된 요청입니다")
    private String refreshToken;

    @ApiModelProperty(required = true, value="탈퇴할 id", example = "TG0414", dataType = "String")
    @NotEmpty(message = "아이디를 입력해주세요")
    private String id;

    @ApiModelProperty(required = true, value="탈퇴할 pw", example = "12345678", dataType = "String")
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

}
