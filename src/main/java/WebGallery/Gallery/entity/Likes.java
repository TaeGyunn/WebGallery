package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wno")
    private Work work;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gno")
    private Guest guest;

    public Likes(Work work, Guest guest){
        this.work = work;
        this.guest = guest;
    }
}
