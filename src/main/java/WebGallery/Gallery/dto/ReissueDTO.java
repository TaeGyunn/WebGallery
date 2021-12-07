package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReissueDTO {

    @NotEmpty(message = "accessToken을 입력해주세요")
    private String accessToken;

    @NotEmpty(message = "refreshToken을 입력해주세요")
    private String refreshToken;
}
