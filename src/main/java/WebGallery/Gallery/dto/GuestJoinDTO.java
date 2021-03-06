package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Query;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class GuestJoinDTO {

    @ApiModelProperty(required = true, value="가입 할 id", example = "Test0414", dataType = "String")
    @NotBlank(message = "아이디를 입력해주세요")
    @Size(min = 2, max = 10, message = "아이디는 2자 이상 10자 이하로 입력해주세요.")
    private String id;

    @ApiModelProperty(required = true, value="가입 할 pw", example = "12345678", dataType = "String")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

    @ApiModelProperty(required = true, value="가입 할 name", example = "Test0414", dataType = "String")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @ApiModelProperty(required = true, value="가입 할 nick", example = "Test", dataType = "String")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nick;

    @ApiModelProperty(required = true, value="가입 할 email", example = "Test@naver.com", dataType = "String")
    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

}
