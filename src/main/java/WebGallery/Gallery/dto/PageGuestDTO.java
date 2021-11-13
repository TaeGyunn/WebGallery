package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.Guest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor

public class PageGuestDTO {

    private Long gno;
    private String nick;

    private List<PageAlbumDTO> pageAlbumDTOS;

    public PageGuestDTO (Guest guest){
        this.gno = guest.getGno();
        this.nick = guest.getNick();
    }
}
