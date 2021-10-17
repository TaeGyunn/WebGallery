package WebGallery.Gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    AUTHOR("ROLE_AUTHOR");

    private String value;

}
