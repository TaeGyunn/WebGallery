package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.util.BeanRegistry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.PersistenceUnitUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor

public class PageAlbumDTO {

    private Long ano;

    private Long gno;

    private String name;

    private List<PageAlbumWorkDTO> a_works;

    public PageAlbumDTO(Album album){
        this.ano = album.getAno();
        this.gno = album.getGuest().getGno();
        this.name =album.getName();

        PersistenceUnitUtil utill = BeanRegistry.lookup(PersistenceUnitUtil.class);
        if(utill.isLoaded(album, "a_works")){ //FETCH JOIN
            this.a_works = album.getA_works().stream().map(PageAlbumWorkDTO::new).collect(Collectors.toList());
        }else{
            this.a_works = Collections.emptyList();
        }
    }

    public PageAlbumDTO(Album album, List<A_work> a_workList){
        this.ano = album.getAno();
        this.gno = album.getGuest().getGno();
        this.name =album.getName();
        this.a_works = a_workList.stream().map(PageAlbumWorkDTO::new).collect(Collectors.toList());
    }


}
