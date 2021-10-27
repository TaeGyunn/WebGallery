package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wno;                           // 작업물 no

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gno")
    private Author author;                      // 작가

    private String comment;                     //내용

    private Integer likes;                       // 좋아요

    private String thema;                       // 테마

    @NotEmpty
    private String name;                        // 작업물 이름

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="pno")
    private Photo photo;                       // 사진

    @OneToMany(mappedBy = "work", cascade = CascadeType.ALL)
    private List<Work_tag> work_tags = new ArrayList<>();


    public Work(Author author,String comment ,String thema, String name, Photo photo){
        this.author = author;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.photo = photo;
    }

}
