package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel
public class DeleteAuthorDTO {

    @ApiModelProperty(required = true, value="게스트 no", example = "18", dataType = "Long")
    private Long gno;

}
