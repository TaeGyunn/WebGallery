package WebGallery.Gallery.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
public class A_thumb {

    @Id
    private Long gno;

    @MapsId
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "gno")
    private Author author;                   // 작가

    @NotNull
    private String ori_name;            // 원본명

    @NotNull
    private String stod_name;           // 서버파일명

    private LocalDateTime cdt;          // 생성일자

    private LocalDateTime mdt;          // 수정일자

    public A_thumb(String ori_name, String stod_name, LocalDateTime cdt){
        this.ori_name = ori_name;
        this.stod_name = stod_name;
        this.cdt = cdt;
    }

    public void saveAuthor(Author author){
        this.author = author;
    }

}
