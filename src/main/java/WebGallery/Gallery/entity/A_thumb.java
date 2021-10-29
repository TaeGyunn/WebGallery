package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
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
    @Column(name = "stod_name")
    private String stodname;           // 서버파일명

    private LocalDateTime cdt;          // 생성일자

    private LocalDateTime mdt;          // 수정일자

    public A_thumb(String ori_name, String stodname, LocalDateTime cdt){
        this.ori_name = ori_name;
        this.stodname = stodname;
        this.cdt = cdt;
    }

    public void saveAuthor(Author author){
        this.author = author;
    }

    public void changeStod_name(String stod_name){
        this.stodname = stod_name;
    }

}
