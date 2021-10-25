//package WebGallery.Gallery.service;
//
//import WebGallery.Gallery.dto.InsertWorkDTO;
//import WebGallery.Gallery.entity.Author;
//import WebGallery.Gallery.entity.Photo;
//import WebGallery.Gallery.entity.Work;
//import WebGallery.Gallery.repository.AuthorRepository;
//import WebGallery.Gallery.repository.PhotoRepository;
//import WebGallery.Gallery.repository.WorkRepository;
//import WebGallery.Gallery.util.FileStore;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class WorkService {
//
//    private AuthorRepository authorRepository;
//    private WorkRepository workRepository;
//    private PhotoRepository photoRepository;
//    private FileStore fileStore;
//
//    @Transactional
//    public Integer InsertWork(InsertWorkDTO insertWorkDTO){
//
//        int check = 0;
//
//        try {
//            Author author = authorRepository.selectByGno(insertWorkDTO.getGuest());
//
//            Work work = new Work(
//                    author,
//                    insertWorkDTO.getThema(),
//                    insertWorkDTO.getName()
//            );
//
//            Work works = workRepository.save(work);
//            if(works != null) {
//
//                Photo photo = fileStore.saveWorkFile(insertWorkDTO.getFile(), works);
//
//                Photo photos = photoRepository.save(photo);
//                if(photos != null){
//                    log.info("Work & Photo Success");
//                    check = 1;
//                }else{
//                    log.info("photo 확인바람");
//                }
//            }else{
//                log.info("InsertWork 확인바람");
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return check;
//    }
//}
