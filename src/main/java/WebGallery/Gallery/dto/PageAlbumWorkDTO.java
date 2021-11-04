package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Work;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageAlbumWorkDTO {

    private Long awno;
    private PageWorkDTO pageWorkDTO;

    public PageAlbumWorkDTO(A_work a_work){
        this.awno = a_work.getAwno();
        PageWorkDTO pageWorkDTO = new PageWorkDTO(a_work);
        this.pageWorkDTO = pageWorkDTO;
    }
}
