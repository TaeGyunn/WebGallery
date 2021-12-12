package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel
public class ModifyWorkDTO {

    @ApiModelProperty(required = true, value="작품 번호", example = "8", dataType = "Long")
    private Long wno;

    @ApiModelProperty(required = true, value = "작품 내용", example = "수정할 내용", dataType = "String")
    private String comment;

    @ApiModelProperty(required = true, value = "작품 테마", example = "수정할 테마", dataType = "String")
    private String thema;
    
    @ApiModelProperty(required = true, value = "작품 이름", example = "수정할 이름", dataType = "String")
    private String name;

    @ApiModelProperty(required = true, value="작품 태그", example = "[\"tag1\",\"tag2\"]", dataType = "List<String>")
    private List<String> tags;

    public ModifyWorkDTO(Long wno, String comment, String thema, String name, List<String> tags) {
        this.wno = wno;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.tags = tags;
    }
}
