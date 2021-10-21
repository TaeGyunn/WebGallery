package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Entity
@Getter
public class Author implements Serializable {

    @Id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "gno")
    private Guest guest;       // guest

    private String sns;     // sns id 
    private String comment; // 내용

    @NotEmpty
    private String thumb;   // 썸네일 서버명 이름

    @Enumerated(EnumType.STRING)
    private Role role;      // role

    public Author(Guest guest, String sns, String comment, String thumb, Role role){
        this.guest = guest;
        this.sns = sns;
        this.comment = comment;
        this.thumb = thumb;
        this.role = role;
    }
}
