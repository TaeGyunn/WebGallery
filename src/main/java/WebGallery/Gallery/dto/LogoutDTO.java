package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LogoutDTO {

    @ApiModelProperty(required = true, value="로그인시 발급되었던 accessToken", dataType = "String")
    @NotEmpty(message = "잘못된 요청입니다")
    private String accessToken;

    @ApiModelProperty(required = true, value="로그인시 발급되었던 refreshToken", dataType = "String")
    @NotEmpty(message = "잘못된 요청입니다")
    private String refreshToken;

    public LogoutDTO(DeleteGuestDTO deleteGuestDTO){
        this.accessToken = deleteGuestDTO.getAccessToken();
        this.refreshToken = deleteGuestDTO.getRefreshToken();
    }

    public LogoutDTO(String accessToken, String refreshToken){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

}
