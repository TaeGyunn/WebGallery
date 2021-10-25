//package WebGallery.Gallery.entity;
//
//import lombok.Getter;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotEmpty;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//public class Work {
//
//    @Id
//    @GeneratedValue
//    private Long wno;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "guest")
//    private Author author;
//
//    private Integer like;
//
//    private String thema;
//
//    @NotEmpty
//    private String name;
//
//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "pno")
//    private Photo work;
//
//
//    public Work(Author author, String thema, String name){
//        this.author = author;
//        this.thema = thema;
//        this.name = name;
//    }
//
//}
