package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class ChangePwDTO {

    @ApiModelProperty(required = true, value="기존 비밀번호", example = "12345678", dataType = "String")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

    @ApiModelProperty(required = true, value="게스트 아이디", dataType = "String")
    private String id;
}
