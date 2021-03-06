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

            Guest guest = guestRepository.findById(insertWorkDTO.getId()).orElse(null);

            Author author = authorRepository.findByGno(guest.getGno());

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
            Work work = workRepository.findByWno(workNo);
            if(work == null){
                return response.fail(map, "???????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST);

            }

            //Work_tag ??????
            List<Work_tag> work_tags = new ArrayList<>();
            work_tags = work_tagRepository.findByWork(work);
            for (Work_tag work_tag : work_tags) {
                work_tagRepository.delete(work_tag);
            }

            //work ??????
            workRepository.delete(work);

            //Photo ??????
            Photo photo = photoRepository.findByPno(work.getPhoto().getPno());
            photoRepository.delete(photo);

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
            
            // ?????? ?????? (?????? ?????? ????????? ?????? ??????)
            List<Work_tag> work_tags = new ArrayList<>();
            work_tags = work_tagRepository.findByWork(work);
            for (Work_tag workTag : work_tags) {
                work_tagRepository.delete(workTag);
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
        Map<String, Boolean> map = new HashMap<>();
        if(guest == null){
            map.put("like", false);
            return response.fail(map,"???????????? ????????? ???????????? ????????????", HttpStatus.BAD_REQUEST);
        }
        Work work = workRepository.findByWno(wno);

        if(work == null){
            map.put("like", false);
            return response.fail(map,"???????????? ????????? ???????????? ????????????", HttpStatus.BAD_REQUEST);
        }
        boolean check = likeRepository.existsByGuestAndWork(guest, work);

        if(check){
            work.changeLike(work.getLikes()-1);
            workRepository.save(work);
            Likes likes = likeRepository.findByGuestAndWork(guest,work);
            likeRepository.delete(likes);
            map.put("unLike", true);
            return response.success("????????? ????????? ?????? ??????");

        }else{
            work.changeLike(work.getLikes()+1);
            workRepository.save(work);
            Likes likes = new Likes(work, guest);
            likeRepository.save(likes);
            map.put("like", true);
            return response.success("????????? ????????? ??????");

        }

    }
}
