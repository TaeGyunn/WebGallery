package WebGallery.Gallery.entity;


import WebGallery.Gallery.dto.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adno;

    private String id;

    private String pw;

    private String nick;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Notice> list = new ArrayList<>();

}
