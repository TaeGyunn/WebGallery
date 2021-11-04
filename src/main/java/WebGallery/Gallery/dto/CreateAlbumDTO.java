package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAlbumDTO {

    private String name;
    private Long gno;

    public CreateAlbumDTO(String name, Long gno){

        this.name = name;
        this.gno = gno;
    }
}
