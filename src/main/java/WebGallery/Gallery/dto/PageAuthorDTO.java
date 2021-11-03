package WebGallery.Gallery.dto;

import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.util.BeanRegistry;
import lombok.Getter;

import javax.persistence.PersistenceUnitUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PageAuthorDTO {

    private Long gno;

    private String sns;

    private String comment;

    private String thumb;

    private List<PageAuthorWorkDTO> pageAuthorWorkDTOS;

    public PageAuthorDTO(Author author){
        this.gno = author.getGuest().getGno();
        this.sns = author.getSns();
        this.comment = author.getComment();
        this.thumb = author.getThumb();

        PersistenceUnitUtil utill = BeanRegistry.lookup(PersistenceUnitUtil.class);
        if(utill.isLoaded(author, "works")){ //FETCH JOIN
            this.pageAuthorWorkDTOS = author.getWorks().stream().map(PageAuthorWorkDTO::new).collect(Collectors.toList());
        }else{
            this.pageAuthorWorkDTOS = Collections.emptyList();
        }
    }

}
