package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull
    private Integer role;    // role

    public Guest(String name, String id, String pw, String nick, String email, Integer role){
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.email = email;
        this.role = role;
    }


}
