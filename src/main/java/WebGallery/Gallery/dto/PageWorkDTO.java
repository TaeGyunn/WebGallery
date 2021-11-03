package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Photo;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.util.BeanRegistry;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.PersistenceUnitUtil;
import java.util.Optional;

@Getter
public class PageWorkDTO {

    private Long wno;
    private Integer likes;
    private String thema;
    private String name;
    private String comment;
    private Photo photo;
    private Long gno;

    private PageAuthorDTO pageAuthorDTO;

    public PageWorkDTO(Work work){
        this.wno = work.getWno();
        this.likes = work.getLikes();
        this.thema = work.getThema();
        this.name = work.getName();
        this.comment = work.getComment();
        this.photo = work.getPhoto();
        this.gno = work.getAuthor().getGno();

        PersistenceUnitUtil util = BeanRegistry.lookup(PersistenceUnitUtil.class);
        if(util.isLoaded(work, "author")){
            this.pageAuthorDTO = Optional.ofNullable(work.getAuthor()).map(PageAuthorDTO::new).orElse(null);
        }
    }
}
