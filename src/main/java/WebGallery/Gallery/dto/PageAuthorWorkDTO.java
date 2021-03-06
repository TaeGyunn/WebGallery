package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.Photo;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.util.BeanRegistry;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.PersistenceUnitUtil;
import java.util.Optional;

@Getter
@NoArgsConstructor

public class PageAuthorWorkDTO {
    private Long wno;
    private Integer likes;
    private String thema;
    private String name;
    private String comment;
    private Photo photo;
    private String url;


    public PageAuthorWorkDTO(Work work){
        this.wno = work.getWno();
        this.likes = work.getLikes();
        this.thema = work.getThema();
        this.name = work.getName();
        this.comment = work.getComment();
        this.photo = work.getPhoto();
    }

    public void setUrl(String url){
        this.url = url;
    }

}
