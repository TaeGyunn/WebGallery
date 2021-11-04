//package WebGallery.Gallery.service;
//
//import WebGallery.Gallery.dto.AddWorkToAlbumDTO;
//import WebGallery.Gallery.dto.CreateAlbumDTO;
//import WebGallery.Gallery.entity.A_work;
//import WebGallery.Gallery.entity.Album;
//import WebGallery.Gallery.entity.Guest;
//import WebGallery.Gallery.entity.Work;
//import WebGallery.Gallery.repository.A_workRepository;
//import WebGallery.Gallery.repository.AlbumRepository;
//import WebGallery.Gallery.repository.GuestRepository;
//import WebGallery.Gallery.repository.WorkRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//class AlbumServiceTest {
//
//    @Autowired AlbumRepository albumRepository;
//    @Autowired A_workRepository a_workRepository;
//    @Autowired GuestRepository guestRepository;
//    @Autowired WorkRepository workRepository;
//
//    @Test
//    @Rollback(value = false)
//    public void 앨범생성(){
//        String name = "성원";
//        CreateAlbumDTO createAlbum = new CreateAlbumDTO(name,8L);
//        Guest guest = guestRepository.findByGno(createAlbum.getGno());
//        if(!albumRepository.existsByName(createAlbum.getName())){
//            Album album = new Album(createAlbum.getName(), guest);
//            albumRepository.save(album);
//        }else{
//            System.out.println("Album name exist");
//        }
//    }
//
//    @Test
//    public void 앨범삭제(){
//        Long ano = 4L;
//        try {
//            Album album = albumRepository.findByAno(ano);
//            List<A_work> list = a_workRepository.findByAlbum(album);
//            if (list != null) {
//                for (int i = 0; i < list.size(); i++) {
//                    a_workRepository.delete(list.get(i));
//                }
//            }
//            albumRepository.delete(album);
//        }catch (IllegalArgumentException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    @Rollback(value = false)
//    public void 앨범_작업물추가(){
//        Long ano = 4L;
//        List<Long> list = new ArrayList<>();
//        list.add(5L);
//        list.add(6L);
//
//        AddWorkToAlbumDTO addWorkToAlbumDTO = new AddWorkToAlbumDTO(ano, list);
//
//        Album album = albumRepository.findByAno(addWorkToAlbumDTO.getAno());
//        for(int i=0; i<addWorkToAlbumDTO.getWnos().size(); i++) {
//            Work work = workRepository.findByWno(addWorkToAlbumDTO.getWnos().get(i));
//            if(!a_workRepository.existsByWorkAndAlbum(work,album)){
//                A_work a_work = new A_work(album, work);
//                a_workRepository.save(a_work);
//            }
//        }
//    }
//
//    @Test
//    public void 앨범_작업물_삭제(){
//        Long ano = 4L;
//        Long wno = 5L;
//
//        Album album = albumRepository.findByAno(ano);
//        Work work = workRepository.findByWno(wno);
//        A_work a_work = new A_work(album, work);
//        a_workRepository.delete(a_work);
//
//    }
//
//
//}