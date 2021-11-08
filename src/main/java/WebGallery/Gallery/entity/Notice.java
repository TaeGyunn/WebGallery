package WebGallery.Gallery.entity;

import lombok.Getter;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;

@Entity
@Getter
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nno;

    @ManyToOne(fetch = FetchType.LAZY)
    private Admin admin;

    private String title;

    private String content;


    public Notice(Admin admin, String title, String content){
        this.admin = admin;
        this.title = title;
        this.content = content;
    }
}
