package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LogoutDTO {

    @NotEmpty(message = "잘못된 요청입니다")
    private String accessToken;

    @NotEmpty(message = "잘못된 요청입니다")
    private String refreshToken;

    public LogoutDTO(DeleteGuestDTO deleteGuestDTO){
        this.accessToken = deleteGuestDTO.getAccessToken();
        this.refreshToken = deleteGuestDTO.getRefreshToken();
    }

}
