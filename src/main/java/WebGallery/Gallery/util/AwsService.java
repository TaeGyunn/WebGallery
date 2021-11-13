package WebGallery.Gallery.util;

import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Photo;
import WebGallery.Gallery.util.FileStore;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AwsService {
    
    private final AmazonS3 s3Client;
    private final FileStore fileStore;
    
    @Value("${cloud.aws.s3.bucket}")
    private final String bucketName;  //s3 버켓경로

    //파일 업로드
    public A_thumb uploadFileToA_thumb(MultipartFile file) throws IOException{
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            this.s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch(IOException e){
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다."));
        }

        A_thumb aThumb =  new A_thumb(
                file.getOriginalFilename(),
                fileName,
                LocalDateTime.now()
        );
        return aThumb;

    }

    public Photo uploadFileToPhoto(MultipartFile file) throws IOException{
        String fileName = createFileName(file.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try(InputStream inputStream = file.getInputStream()){
            this.s3Client.putObject(new PutObjectRequest(bucketName, fileName, inputStream,objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }catch(IOException e){
            throw new IllegalArgumentException(String.format("파일 변환 중 에러가 발생하였습니다."));
        }
        Photo photo = new Photo(
                file.getOriginalFilename(),
                fileName,
                LocalDateTime.now()
        );
        return photo;

    }

    // 확장명을 유지, 유니크한 파일의 이름을 생성
    private String createFileName(String originalFileName){
        return UUID.randomUUID().toString().concat(getFileExtension(originalFileName));
    }

    // 파일의 확장자명을 가져오는 로직
    private String getFileExtension(String fileName){
        try{
            return fileName.substring(fileName.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e){
            throw new IllegalArgumentException(String.format("잘못된 형식의 파일 (%s)입니다.", fileName));
        }
    }

    //url 불러오기
    public String getFileUrl(String fileName){

        return s3Client.getUrl(bucketName, fileName).toString();
    }
    
    //s3 파일 삭제
    public void delete(String fileName){
        try{
            DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(this.bucketName, fileName);
            this.s3Client.deleteObject(deleteObjectRequest);
        }catch (AmazonServiceException e){
            e.printStackTrace();
        }
    }
}
