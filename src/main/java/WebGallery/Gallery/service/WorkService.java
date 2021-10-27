package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.entity.*;
import WebGallery.Gallery.repository.*;
import WebGallery.Gallery.util.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkService {

    private AuthorRepository authorRepository;
    private WorkRepository workRepository;
    private PhotoRepository photoRepository;
    private TagRepository tagRepository;
    private Work_tagRepository work_tagRepository;
    private FileStore fileStore;

    public Integer InsertWork(InsertWorkDTO insertWorkDTO){

        int check = 0;

        try {
            Photo photo = fileStore.saveWorkFile(insertWorkDTO.getFile());
            photo = photoRepository.save(photo);

            Author author = authorRepository.findByGno(insertWorkDTO.getGuest());

            Work work = new Work(
                    author,
                    insertWorkDTO.getComment(),
                    insertWorkDTO.getThema(),
                    insertWorkDTO.getName(),
                    photo
            );
            Work works = workRepository.save(work);

            for(int i=0; i<insertWorkDTO.getTags().size(); i++){
                Tag tag = new Tag(insertWorkDTO.getTags().get(i));
                tagRepository.save(tag);
                Work_tag work_tag = new Work_tag(works, tag);
                work_tagRepository.save(work_tag);
                if(i == insertWorkDTO.getTags().size()-1){
                    check = 1;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return check;
    }

    public void deleteWork(Long workNo){

        // Photo delete -> Work_tag delete -> work delete
        Work work = workRepository.getById(workNo);

        //Photo 제거
        Photo photo = photoRepository.getById(work.getPhoto().getPno());
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
