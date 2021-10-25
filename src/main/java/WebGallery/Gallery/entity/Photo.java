//package WebGallery.Gallery.entity;
//
//import lombok.Getter;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter
//public class Photo {
//
//    @Id
//    @GeneratedValue
//    private Long pno;                   // No
//
//    @OneToOne(mappedBy = "wno", fetch = FetchType.LAZY)
//    private Work work;                  // Work
//
//    @NotNull
//    private String ori_name;            // 원본명
//
//    @NotNull
//    private String stod_name;           // 서버파일명
//
//    @NotEmpty
//    private LocalDateTime cdt;          // 생성일자
//
//    private LocalDateTime mdt;          // 수정일자
//
//
//    public Photo(Work work, String ori_name, String stod_name, LocalDateTime cdt){
//        this.work = work;
//        this.ori_name = ori_name;
//        this.stod_name = stod_name;
//        this.cdt = cdt;
//    }
//}
