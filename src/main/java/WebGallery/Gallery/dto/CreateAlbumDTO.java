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

    @ApiModelProperty(required = true, value="게스트 id", example = "19", dataType = "Long")
    private String id;

    public CreateAlbumDTO(String name, String id){

        this.name = name;
        this.id = id;
    }
}
