package WebGallery.Gallery.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AwsService {
    
    private final AmazonS3 s3Client;
    
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;  //s3 버켓경로

    public void uploadMultipartFile(MultipartFile[] files, String bucketKey) throws IOException{

        ObjectMetadata omd = new ObjectMetadata();

        for(int i=0; i<files.length; i++){
            omd.setContentType(files[i].getContentType());
            omd.setContentLength(files[i].getSize());
            omd.setHeader("filename", files[i].getOriginalFilename());
            s3Client.putObject(new PutObjectRequest(bucketName+bucketKey, files[i].getOriginalFilename(),
                    files[i].getInputStream(),omd));
            log.info(bucketName+bucketKey);
            log.info(files[i].getOriginalFilename());
        }

    }

    public String getPhotoPath(String path){
        return s3Client.getUrl(bucketName, path).toString();
    }


}
