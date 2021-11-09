//package WebGallery.Gallery.service;
//
//import WebGallery.Gallery.dto.CreateNoticeDTO;
//import WebGallery.Gallery.entity.Admin;
//import WebGallery.Gallery.entity.Notice;
//import WebGallery.Gallery.repository.AdminRepository;
//import WebGallery.Gallery.repository.NoticeRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//class AdminServiceTest {
//
//    @Autowired AdminRepository adminRepository;
//    @Autowired NoticeRepository noticeRepository;
//
//
//
//    @Test
//    @Rollback(value = false)
//    public void 게시판등록(){
//
//        CreateNoticeDTO createNoticeDTO = new CreateNoticeDTO(1l, "title", "content");
//        Admin admin = adminRepository.findByAdno(createNoticeDTO.getAdno());
//        Notice notice = new Notice(admin, createNoticeDTO.getTitle(), createNoticeDTO.getContent());
//        noticeRepository.save(notice);
//
//    }
//
//    @Test
//    public void 게시판삭제(){
//
//        Long adno = 1L;
//        Admin admin = adminRepository.findByAdno(adno);
//        Notice notice = noticeRepository.findByAdmin(admin);
//        noticeRepository.delete(notice);
//    }
//}