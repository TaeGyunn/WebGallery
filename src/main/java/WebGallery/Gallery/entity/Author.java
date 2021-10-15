package WebGallery.Gallery.entity;

import WebGallery.Gallery.dto.Role;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
public class Author{

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
}
