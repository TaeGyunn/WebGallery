package WebGallery.Gallery.entity;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Work_tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wtno;

    @ManyToOne
    @JoinColumn(name = "wno")
    private Work work;

    @ManyToOne
    @JoinColumn(name="tno")
    private Tag tag;

    public Work_tag(Work work, Tag tag){
        this.work = work;
        this.tag = tag;
    }

}
