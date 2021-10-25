package WebGallery.Gallery.util;

import WebGallery.Gallery.entity.A_Tumb;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Data
@Slf4j
public class FileStore {

    @Value("${file.upload.location}")
    private String fileDir;

    public String getFullPath(String fileName){
        return fileDir + fileName;
    }

    public A_Tumb saveThumbFile(MultipartFile multipartFile) throws IOException{

        if(!multipartFile.isEmpty()){
            String originalFileName = multipartFile.getOriginalFilename();

            int delimter = originalFileName.lastIndexOf(".");
            String extension = originalFileName.substring(delimter);

            String uuid = UUID.randomUUID().toString();
            String saveFileName = uuid + "." + extension;

            multipartFile.transferTo(new File(getFullPath(saveFileName)));
            return new A_Tumb(
                    originalFileName,
                    saveFileName,
                    LocalDateTime.now()
            );
        }
        return null;
    }

//    public Photo saveWorkFile(MultipartFile multipartFile, Work work) throws  IOException{
//
//        if(!multipartFile.isEmpty()){
//            String originalFileName = multipartFile.getOriginalFilename();
//
//            int delimter = originalFileName.lastIndexOf(".");
//            String extension = originalFileName.substring(delimter);
//
//            String uuid = UUID.randomUUID().toString();
//            String saveFileName = uuid + "." + extension;
//
//            multipartFile.transferTo(new File(getFullPath(saveFileName)));
//
//            return new Photo(
//                    work,
//                    originalFileName,
//                    saveFileName,
//                    LocalDateTime.now()
//            );
//        }
//        return null;
//    }
}
