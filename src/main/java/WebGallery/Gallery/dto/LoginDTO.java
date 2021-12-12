package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    @ApiModelProperty(required = true, value="로그인 할 id", example = "Test0414", dataType = "String")
    @NotBlank(message = "아이디를 입력해주세요")
    private String id;

    @ApiModelProperty(required = true, value="로그인 할 pw", example = "12345678",dataType = "String")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(id, pw);
    }

}
