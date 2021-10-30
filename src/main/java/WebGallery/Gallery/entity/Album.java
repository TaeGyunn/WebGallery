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

    private String name;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private final List<A_work> a_works = new ArrayList<>();

    public Album(String name) {
        this.name = name;
    }
}
