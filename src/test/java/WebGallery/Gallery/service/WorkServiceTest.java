package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.dto.ModifyWorkDTO;
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
    public void 작업물추가(){

        try {
            MockMultipartFile thumb = new MockMultipartFile("content","sample_images_01.png","multipart/mixed", new FileInputStream("/Users/taeku/Desktop/샘플이미지_12가지_모음/sample_images_01.png"));
            List<String> tags = new ArrayList<>();
            tags.add("공부");
            tags.add("카페");
            tags.add("아메리카노");
            InsertWorkDTO insertWorkDTO = new InsertWorkDTO(8L, "HOLLYS COFFEE", "cafe", "Coffee", thumb, tags);

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
                Tag tag = new Tag();
                if(!tagRepository.existsByName(insertWorkDTO.getTags().get(i))) {
                    tag = new Tag(insertWorkDTO.getTags().get(i));
                    tagRepository.save(tag);
                }else{
                    tag = tagRepository.findByName(insertWorkDTO.getTags().get(i));
                }
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

    @Test
    public void 아이템수정(){

        try {
            //given
            MockMultipartFile thumb = new MockMultipartFile("content","sample_images_01.png","multipart/mixed", new FileInputStream("/Users/taeku/Desktop/샘플이미지_12가지_모음/sample_images_01.png"));
            List<String> tags = new ArrayList<>();
            tags.add("공부");
            tags.add("cafe");
            tags.add("아메리카노");
            ModifyWorkDTO modifyWorkDTO = new ModifyWorkDTO(6L, "STARBUCKS", "cafe", "StarCoffee", thumb, tags );
            int check = 0;

            //when
            Work work = workRepository.findByWno(modifyWorkDTO.getWno());
            Photo savedPhoto = photoRepository.findByPno(work.getPhoto().getPno());
            Photo notSavePhoto = fileStore.saveWorkFile(modifyWorkDTO.getFile());

            if (!work.getComment().equals(modifyWorkDTO.getComment())) {
                work.changeComment(modifyWorkDTO.getComment());
                check = 1;
            }
            if (!work.getThema().equals(modifyWorkDTO.getThema())) {
                work.changeTheam(modifyWorkDTO.getThema());
                check = 1;
            }
            if (!work.getName().equals(modifyWorkDTO.getName())) {
                work.changeName(modifyWorkDTO.getName());
                check = 1;
            }
            if (!savedPhoto.getOri_name().equals(notSavePhoto.getOri_name())) {
                notSavePhoto = photoRepository.save(notSavePhoto);
                photoRepository.delete(savedPhoto);
                work.changePhoto(notSavePhoto);
                check = 1;
            }
            if(check == 1){
                workRepository.save(work);
            }

            // 태그 비교 (태그 싹다 지우고 다시 추가)
            List<Work_tag> work_tags = new ArrayList<>();
            work_tags = work_tagRepository.findByWork(work);
            for(int i=0; i<work_tags.size(); i++){
                work_tagRepository.delete(work_tags.get(i));
            }

            for (int i = 0; i < modifyWorkDTO.getTags().size(); i++) {
                if(!tagRepository.existsByName(modifyWorkDTO.getTags().get(i))){
                    Tag tag = new Tag(modifyWorkDTO.getTags().get(i));
                    tagRepository.save(tag);
                    Work_tag work_tag = new Work_tag(work, tag);
                    work_tagRepository.save(work_tag);
                }
            }

        }catch (IOException | IllegalArgumentException e){
            e.printStackTrace();
        }
        //then
    }

}