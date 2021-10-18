package WebGallery.Gallery.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class Work_Tag implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private Work work;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag tag;
}

