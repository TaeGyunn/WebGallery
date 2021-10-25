package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Author{

    @Id
    private Long gno;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "gno")
    private Guest guest;       // guest

    private String sns;     // sns id
    private String comment; // 내용

    @NotEmpty
    private String thumb;   // 썸네일 서버명 이름


//    @OneToMany(mappedBy = "authors", cascade = CascadeType.ALL)
//    private List<Work> works = new ArrayList<>();
//
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "author")
//    private A_Tumb author;
//
    public Author(Guest guest, String sns, String comment, String thumb){
        this.guest = guest;
        this.sns = sns;
        this.comment = comment;
        this.thumb = thumb;
    }
}
