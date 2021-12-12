package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class CreateAlbumDTO {

    @ApiModelProperty(required = true, value="생성할 앨범이름", example = "newAlbum1", dataType = "String")
    private String name;

    @ApiModelProperty(required = true, value="게스트 no", example = "19", dataType = "Long")
    private Long gno;

    public CreateAlbumDTO(String name, Long gno){

        this.name = name;
        this.gno = gno;
    }
}
