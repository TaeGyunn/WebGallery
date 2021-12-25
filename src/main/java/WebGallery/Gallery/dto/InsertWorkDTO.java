package WebGallery.Gallery.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class InsertWorkDTO {

    @ApiModelProperty(required = true, value="게스트 아이디", dataType = "String")
    private String id;           // 게스트

    @ApiModelProperty(required = true, value="기입할 내용", example = "작품에 대한 설명을 기입", dataType = "String")
    private String comment;     // 내용

    @ApiModelProperty(required = true, value="작품 테마", example = "test", dataType = "String")
    private String thema;       // 테마

    @ApiModelProperty(required = true, value="작품 이름", example = "해강산바람", dataType = "String")
    private String name;        // 이름

    @ApiModelProperty(required = true, value="작품 태그", example = "[\"tag1\",\"tag2\"]", dataType = "List<String>")
    private List<String> tags;

    public InsertWorkDTO(String id, String comment, String thema, String name, List<String> tags) {
        this.id = id;
        this.comment = comment;
        this.thema = thema;
        this.name = name;
        this.tags = tags;
    }
}
