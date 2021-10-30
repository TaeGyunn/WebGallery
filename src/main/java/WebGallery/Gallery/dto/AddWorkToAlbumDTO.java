package WebGallery.Gallery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class AddWorkToAlbumDTO {

    private Long ano;   //albumNo

    private List<Long> wnos;   //workNo
}