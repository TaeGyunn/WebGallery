package WebGallery.Gallery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adno")
    private Admin admin;

    private String title;

    private String content;


    public Notice(Admin admin, String title, String content){
        this.admin = admin;
        this.title = title;
        this.content = content;
    }
}
