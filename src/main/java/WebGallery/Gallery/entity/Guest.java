package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Guest {

    @Id
    @GeneratedValue
    private Long gno;       // no

    @NotEmpty
    private String name;    // name

    @NotEmpty
    private String id;      // id

    @NotEmpty
    private String pw;      // password

    @NotEmpty
    private String nick;    // nickname

    @NotEmpty
    private String email;   // email

    @Enumerated(EnumType.STRING)
    private Role role;      // role
}
