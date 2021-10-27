package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.entity.*;
import WebGallery.Gallery.repository.*;
import WebGallery.Gallery.util.FileStore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class WorkServiceTest {

    @Autowired FileStore fileStore;
    @Autowired PhotoRepository photoRepository;
    @Autowired AuthorRepository authorRepository;
    @Autowired WorkRepository workRepository;
    @Autowired TagRepository tagRepository;
    @Autowired Work_tagRepository work_tagRepository;

    @Test
    @Rollback(value = false)
    public void 작업물추가(){

        try {
            MockMultipartFile thumb = new MockMultipartFile("content","sample_images_01.png","multipart/mixed", new FileInputStream("/Users/taeku/Desktop/샘플이미지_12가지_모음/sample_images_01.png"));
            List<String> tags = new ArrayList<>();
            tags.add("공부");
            tags.add("열공");
            InsertWorkDTO insertWorkDTO = new InsertWorkDTO(8L, "성원이 공부하자", "공부", "Study", thumb, tags);

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

            for(int i=0; i<insertWorkDTO.getTags().size(); i++){
                Tag tag = new Tag(insertWorkDTO.getTags().get(i));
                tagRepository.save(tag);
                Work_tag work_tag = new Work_tag(works, tag);
                work_tagRepository.save(work_tag);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void 아이템삭제(){
        Long workNo = 5L;

        // Photo delete -> Work_tag delete -> work delete
        Work work = workRepository.findByWno(workNo);
        //Photo 제거
        Photo photo = photoRepository.findByPno(work.getPhoto().getPno());
        photoRepository.delete(photo);

        //Work_tag 제거
        List<Work_tag> work_tags = new ArrayList<>();
        work_tags = work_tagRepository.findByWork(work);
        for(int i=0; i<work_tags.size(); i++){
            work_tagRepository.delete(work_tags.get(i));
        }

        //work 제거
        workRepository.delete(work);
    }

}