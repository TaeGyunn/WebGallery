package WebGallery.Gallery.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Work {

    @Id
    @GeneratedValue
    private Long wno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gno")
    private Author author;

    private Integer like;

    private String thema;

    @NotEmpty
    private String name;

}
