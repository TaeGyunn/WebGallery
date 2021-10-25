package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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
    @Enumerated(EnumType.STRING)
    private Role role;    // role

    public Guest(String name, String id, String pw, String nick, String email, Role role){
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.email = email;
        this.role = role;
    }

    public Guest(Long gno, String name, String id, String pw, String nick, String email, Role role){
        this.gno = gno;
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.nick = nick;
        this.email = email;
        this.role = role;
    }

    public void ChangeRole(Role role){
        this.role = role;
    }

}
