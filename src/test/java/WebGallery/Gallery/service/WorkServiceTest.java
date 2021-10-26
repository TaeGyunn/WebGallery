package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Photo;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.repository.PhotoRepository;
import WebGallery.Gallery.repository.WorkRepository;
import WebGallery.Gallery.util.FileStore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WorkServiceTest {

    @Autowired FileStore fileStore;
    @Autowired PhotoRepository photoRepository;
    @Autowired AuthorRepository authorRepository;
    @Autowired WorkRepository workRepository;

    @Test
    public void 작업물추가(){

        try {
            MockMultipartFile thumb = new MockMultipartFile("content","sample_images_01.png","multipart/mixed", new FileInputStream("/Users/taeku/Desktop/샘플이미지_12가지_모음/sample_images_01.png"));
            InsertWorkDTO insertWorkDTO = new InsertWorkDTO(8L, "성원이 공부하자", "공부", "Study", thumb);

            Photo photo = fileStore.saveWorkFile(insertWorkDTO.getFile());

            Photo photos = photoRepository.save(photo);

            Author author = authorRepository.findByGno(insertWorkDTO.getGuest());
            Work work = new Work(
                    author,
                    insertWorkDTO.getComment(),
                    insertWorkDTO.getThema(),
                    insertWorkDTO.getName(),
                    photos
            );

            Work works = workRepository.save(work);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}