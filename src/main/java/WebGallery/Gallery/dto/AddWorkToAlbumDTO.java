package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AddWorkToAlbumDTO {

    @ApiModelProperty(required = true, value="앨범 no", example = "2", dataType = "Long")
    private Long ano;   //albumNo

    @ApiModelProperty(required = true, value="작가 no", example = "[\"8\"]", dataType = "List<Long>")
    private List<Long> wnos;   //workNo

    public AddWorkToAlbumDTO(Long ano, List<Long> wnos){
        this.ano = ano;
        this.wnos = wnos;
    }
}
