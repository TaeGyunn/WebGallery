package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

    @NotBlank(message = "아이디를 입력해주세요")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String pw;

}
