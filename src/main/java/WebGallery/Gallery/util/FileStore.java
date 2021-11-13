package WebGallery.Gallery.util;

import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Photo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class FileStore {

    public A_thumb saveThumbFile(String original, String server) throws IOException{

        return new A_thumb(
                    original,
                    server,
                    LocalDateTime.now()
            );
    }

    public Photo saveWorkFile(String original, String server) throws  IOException{

        return new Photo(
                original,
                server,
                LocalDateTime.now()
        );
    }
}
