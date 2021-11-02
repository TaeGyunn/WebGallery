package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Work_tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wno")
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tno")
    private Tag tag;

    public Work_tag(Work work, Tag tag){
        this.work = work;
        this.tag = tag;
    }

}
