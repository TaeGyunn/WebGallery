package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DeleteGuestDTO {

    @NotEmpty(message = "잘못된 요청입니다")
    private String accessToken;

    @NotEmpty(message = "잘못된 요청입니다")
    private String refreshToken;

    @NotEmpty(message = "아이디를 입력해주세요")
    private String id;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String pw;

}
