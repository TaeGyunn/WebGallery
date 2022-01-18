package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ano;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="gno")
    private Guest guest;

    private String name;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private final List<A_work> a_works = new ArrayList<>();

    public Album(String name, Guest guest) {
        this.name = name;
        this.guest = guest;
    }


}
