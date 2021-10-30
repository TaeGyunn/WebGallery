package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class A_work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awno;

    @ManyToOne
    @JoinColumn(name = "ano")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "wno")
    private Work work;

    public A_work(Album album, Work work) {
        this.album = album;
        this.work = work;
    }
}
