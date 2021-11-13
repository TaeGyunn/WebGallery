package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class GuestModifyDTO {

    private Long gno;

    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
    private String nick;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    public GuestModifyDTO(Long gno, String nick, String pw, String email) {
        this.gno = gno;
        this.nick = nick;
        this.pw = pw;
        this.email = email;
    }
}
