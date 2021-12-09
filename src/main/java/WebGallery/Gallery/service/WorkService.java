package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.InsertWorkDTO;
import WebGallery.Gallery.dto.ModifyWorkDTO;
import WebGallery.Gallery.dto.PageWorkDTO;
import WebGallery.Gallery.entity.*;
import WebGallery.Gallery.repository.*;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkService {

    private final AuthorRepository authorRepository;
    private final WorkRepository workRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;
    private final Work_tagRepository work_tagRepository;
    private final GuestRepository guestRepository;
    private final LikeRepository likeRepository;
    private final AwsService awsService;
    private final Response response;

    public Integer InsertWork(InsertWorkDTO insertWorkDTO, MultipartFile photos){

        int check = 0;

        try {

            Photo photo = awsService.uploadFileToPhoto(photos);
            photo = photoRepository.save(photo);

            Author author = authorRepository.findByGno(insertWorkDTO.getGno());

            Work work = new Work(
                    author,
                    insertWorkDTO.getComment(),
                    insertWorkDTO.getThema(),
                    insertWorkDTO.getName(),
                    photo
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
                    if (i == insertWorkDTO.getTags().size() - 1) {
                        check = 1;
                    }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return check;
    }

    public ResponseEntity<?> deleteWork(Long workNo){

        Map<String ,String > map = new HashMap<>();

        try {
            // Photo delete -> Work_tag delete -> work delete
            Work work = workRepository.getById(workNo);

            //Photo 제거
            Photo photo = photoRepository.getById(work.getPhoto().getPno());
            photoRepository.delete(photo);

            //Work_tag 제거
            List<Work_tag> work_tags = new ArrayList<>();
            work_tags = work_tagRepository.findByWork(work);
            for (int i = 0; i < work_tags.size(); i++) {
                work_tagRepository.delete(work_tags.get(i));
            }

            //work 제거
            workRepository.delete(work);
            map.put("delete", "success");
            return response.success(map, "work delete success", HttpStatus.OK);
        }catch(IllegalArgumentException e){
            e.printStackTrace();
        }
        map.put("delete", "fail");
        return response.fail(map, "work delete fail", HttpStatus.BAD_REQUEST);
    }

    public Integer modifyWork(ModifyWorkDTO modifyWorkDTO, MultipartFile photos){

        int check = 0;
        try {
            Work work = workRepository.findByWno(modifyWorkDTO.getWno());
            Photo savedPhoto = photoRepository.findByPno(work.getPhoto().getPno());
            Photo notSavePhoto = new Photo();
            if(photos != null) {
                awsService.delete(savedPhoto.getStod_name());
                notSavePhoto = awsService.uploadFileToPhoto(photos);
            }
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

        return check;
    }

    public Page<PageWorkDTO> workPage(Integer page, Integer size){

            Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "author");
            List<PageWorkDTO> pageWorkDTOS = workRepository.findAllWithAuthor().stream().map(PageWorkDTO::new).collect(Collectors.toList());
            for (PageWorkDTO pageWorkDTO : pageWorkDTOS) {
                pageWorkDTO.setUrl(awsService.getFileUrl(pageWorkDTO.getPhoto().getStod_name()));
            }
            final int start = (int) pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), pageWorkDTOS.size());
            final Page<PageWorkDTO> Result = new PageImpl<>(pageWorkDTOS.subList(start, end), pageable, pageWorkDTOS.size());

            return Result;
        }

    public Page<PageWorkDTO> workThemaPage(Integer page, Integer size, String thema){
        Pageable pageable = PageRequest.of(page - 1, size, Sort.Direction.DESC, "likes");
        List<PageWorkDTO> pageWorkDTOS = workRepository.findByThemaWithAuthor(thema).stream().map(PageWorkDTO::new).collect(Collectors.toList());
        for (PageWorkDTO pageWorkDTO : pageWorkDTOS) {
            pageWorkDTO.setUrl(awsService.getFileUrl(pageWorkDTO.getPhoto().getStod_name()));
        }
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), pageWorkDTOS.size());
        final Page<PageWorkDTO> Result = new PageImpl<>(pageWorkDTOS.subList(start, end), pageable, pageWorkDTOS.size());

        return Result;
    }


    public ResponseEntity<?> likeWork(Long gno, Long wno){

        Guest guest = guestRepository.findByGno(gno);
        if(guest == null){
            return response.fail("해당하는 유저가 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }
        Work work = workRepository.findByWno(wno);

        if(work == null){
            return response.fail("해당하는 작품이 존재하지 않습니다", HttpStatus.BAD_REQUEST);
        }
        boolean check = likeRepository.existsByGuestAndWork(guest, work);

        if(check){
            work.changeLike(work.getLikes()-1);
            workRepository.save(work);
            Likes likes = likeRepository.findByGuestAndWork(guest,work);
            likeRepository.delete(likes);
            return response.success("아이템 좋아요 취소 성공");

        }else{
            work.changeLike(work.getLikes()+1);
            workRepository.save(work);
            Likes likes = new Likes(work, guest);
            likeRepository.save(likes);
            return response.success("아이템 좋아요 성공");

        }

    }
}
