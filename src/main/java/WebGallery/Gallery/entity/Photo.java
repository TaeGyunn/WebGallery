package WebGallery.Gallery.entity;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;                   // No

    @NotNull
    private String ori_name;            // 원본명

    @NotNull
    private String stod_name;           // 서버파일명

    private LocalDateTime cdt;          // 생성일자

    private LocalDateTime mdt;          // 수정일자


    public Photo(String ori_name, String stod_name, LocalDateTime cdt){
        this.ori_name = ori_name;
        this.stod_name = stod_name;
        this.cdt = cdt;
    }
}
