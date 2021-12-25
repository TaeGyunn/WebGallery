package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.A_TumbRepository;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final GuestRepository guestRepository;
    private final AwsService awsService;
    private final A_TumbRepository a_tumbRepository;
    private final Response response;

    // 작가 삭제
    public ResponseEntity<?> authorDelete(String id){

        Map<String, String> map = new HashMap<>();

        try {
            Guest guest = guestRepository.findById(id).orElse(null);
            Author author = authorRepository.findByGno(guest.getGno());
            if(author == null) return response.fail("작가가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);

            authorRepository.delete(author);
            map.put("delete", "success");
            return response.success(map, "작가 삭제 성공", HttpStatus.OK);

        } catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
        map.put("delete", "fail");
        return response.fail(map,"작가 삭제 실패", HttpStatus.BAD_REQUEST);

    }
    
    // 작가 수정 
    public Integer authorModify(AuthorModifyDTO authorModifyDTO, MultipartFile thumbs){

        Guest guest = guestRepository.findById(authorModifyDTO.getId()).orElse(null);
        Author author = authorRepository.findByGno(guest.getGno());
        int check = 0;
        String oldStod_name = "1";
        A_thumb a_thumb = new A_thumb();
        try {
            if (!author.getSns().equals(authorModifyDTO.getSns())) {
                author.changeSns(authorModifyDTO.getSns());
                check = 1;
            }
            if (!author.getComment().equals(authorModifyDTO.getComment())) {
                author.changeComment(authorModifyDTO.getComment());
                check = 1;
            }

            if(thumbs != null){
                A_thumb thumb = a_tumbRepository.findByAuthor(author);
                awsService.delete(thumb.getStodname());
                a_thumb = awsService.uploadFileToA_thumb(thumbs);
            }
            String stodName = a_thumb.getStodname();

            if (!author.getThumb().equals(stodName)) {
                oldStod_name = author.getThumb();
                author.changeThumb(stodName);
                check = 2;
            }
            if (check == 1 || check == 2) {
                authorRepository.save(author);
                if (check == 2) {
                    A_thumb aThumb = a_tumbRepository.findByStodname(oldStod_name);
                    aThumb.changeStod_name(stodName);
                    a_tumbRepository.save(aThumb);
                }
                return check;
            }
        }catch (IllegalArgumentException | IOException exception){
            exception.printStackTrace();
        }

        return check;
    }
    
    //작가 작품 보여주기
    public List<PageAuthorDTO> showWork(String nick, int page){

        Pageable pageable = PageRequest.of(page -1, 5, Sort.Direction.DESC, "guest");
        Guest guest = guestRepository.findByNick(nick);
//        Page<Author> a = authorRepository.findByGno(guest.getGno(),pageable);
        List<Author> a = authorRepository.findAllWithWork();

        List<PageAuthorDTO> authorPage = a.stream().map(PageAuthorDTO::new).collect(Collectors.toList());

        for (PageAuthorDTO pageAuthorDTO : authorPage) {
            pageAuthorDTO.setUrl(awsService.getFileUrl(pageAuthorDTO.getThumb()));

            int size = pageAuthorDTO.getPageAuthorWorkDTOS().size();
            for(int i=0; i<size; i++){
                pageAuthorDTO.getPageAuthorWorkDTOS().get(i)
                        .setUrl(awsService.getFileUrl(pageAuthorDTO.getPageAuthorWorkDTOS().get(i).getPhoto().getStod_name()));
            }
        }



        return authorPage;

    }


}
