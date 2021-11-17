package WebGallery.Gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class GuestInfoDTO{

    private String nickname;
    private Long gno;
    private String f5;

    public GuestInfoDTO(String nickname, Long gno){
        this.nickname = nickname;
        this.gno = gno;
    }

    public GuestInfoDTO(Object details) {
        this.f5 = details.toString();
    }

}
