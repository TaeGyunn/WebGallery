package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
import WebGallery.Gallery.dto.CreateAlbumDTO;
import WebGallery.Gallery.dto.PageAlbumDTO;
import WebGallery.Gallery.dto.PageAuthorDTO;
import WebGallery.Gallery.entity.A_work;
import WebGallery.Gallery.entity.Album;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.entity.Work;
import WebGallery.Gallery.repository.A_workRepository;
import WebGallery.Gallery.repository.AlbumRepository;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.repository.WorkRepository;
import WebGallery.Gallery.util.AwsService;
import WebGallery.Gallery.util.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final GuestRepository guestRepository;
    private final A_workRepository a_workRepository;
    private final WorkRepository workRepository;
    private final AwsService awsService;
    private final Response response;

    //앨범리스트 가져오기
    public List<PageAlbumDTO> showAlbumList(Long gno){
        Guest guest = guestRepository.findByGno(gno);
        List<PageAlbumDTO> albums = albumRepository.findByGuest(guest).stream().map(PageAlbumDTO::new).collect(Collectors.toList());
        List<PageAlbumDTO> newAlbum = new ArrayList<>();
        int cnt = 0;
        for(int i=0; i<albums.size(); i =+ 2){
            newAlbum.add(albums.get(i));
            String url = awsService.getFileUrl(albums.get(i).getA_works().get(0).getPageWorkDTO().getPhoto().getStod_name());
            newAlbum.get(cnt).getA_works().get(0).getPageWorkDTO().setUrl(url);
            cnt++;
        }
        return newAlbum;
    }
    
    //앨범 생성
    public ResponseEntity<?> createAlbum(CreateAlbumDTO createAlbum){

        Map<String, Boolean > map = new HashMap<>();
        Guest guest = guestRepository.findByGno(createAlbum.getGno());
        if(!albumRepository.exists(createAlbum.getName(), guest)){
            Album album = new Album(createAlbum.getName(), guest);
            albumRepository.save(album);
            map.put("albumCreate", true);
            return response.success(map, "앨범 생성 성공", HttpStatus.OK);
        }else{
            map.put("albumCreate", false);
            return response.fail(map, "이미 있는 앨범 아이디 입니다.", HttpStatus.BAD_REQUEST);
        }
    }
    
    //앨범에 작업물 추가
    public Integer addWorkToAlbum(AddWorkToAlbumDTO addWorkToAlbumDTO){

        int check = 0;
        Album album = albumRepository.findByAno(addWorkToAlbumDTO.getAno());
        for(int i=0; i<addWorkToAlbumDTO.getWnos().size(); i++) {
            Work work = workRepository.findByWno(addWorkToAlbumDTO.getWnos().get(i));
            if(!a_workRepository.existsByWorkAndAlbum(work,album)){
                A_work a_work = new A_work(album, work);
                a_workRepository.save(a_work);
                check = 1;
            }
        }
        return check;
    }


    // 앨범에 작업물 삭제
    public void deleteWorkToAlbum(Long ano, Long wno){

        try {
            Album album = albumRepository.findByAno(ano);
            Work work = workRepository.findByWno(wno);
            A_work a_work = new A_work(album, work);
            a_workRepository.delete(a_work);
            log.info("앨범에 작업물 삭제 완료");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    // 앨범 삭제
    public void deleteAlbum(Long ano) {

        try {
            Album album = albumRepository.findByAno(ano);
            List<A_work> list = a_workRepository.findByAlbum(album);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    a_workRepository.delete(list.get(i));
                }
                log.info("a_work deletes");
            }
            albumRepository.delete(album);
            log.info("album delete");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }
}
