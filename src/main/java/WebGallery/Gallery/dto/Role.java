package WebGallery.Gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Getter
public enum Role {
    ADMIN("ADMIN"),
    GUEST("GUEST"),
    AUTHOR("AUTHOR");

    private String value;


}
