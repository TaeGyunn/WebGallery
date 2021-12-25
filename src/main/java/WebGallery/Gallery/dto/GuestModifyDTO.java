package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class GuestModifyDTO {

    @ApiModelProperty(required = true, value="게스트 아이디", dataType = "String")
    private String id;

    @ApiModelProperty(required = true, value="변경할 nick", example = "changeNick", dataType = "String")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nick;

    @ApiModelProperty(required = true, value="변경할 email", example = "ChangeTest@naver.com", dataType = "String")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식에 맞지 않습니다.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    public GuestModifyDTO(String id, String nick, String email) {
        this.id = id;
        this.nick = nick;
        this.email = email;
    }
}
