package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.CreateNoticeDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.entity.Notice;
import WebGallery.Gallery.repository.AdminRepository;
import WebGallery.Gallery.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public Admin findAdminNick(String nick){
        Admin admin = adminRepository.findByNick(nick);
        return admin;
    }

    @Transactional(readOnly = true)
    public Admin Login(LoginDTO loginDTO){

        Admin admin = adminRepository.findById(loginDTO.getId());
        if(admin != null) {

            if (passwordEncoder.matches(loginDTO.getPw(), admin.getPw())) {
                return admin;
            } else {
                log.info("올바르지 않은 패스워드");
            }
        }
        log.info("올바르지 않은 아이디");
        return null;
    }

    public void createNotice(CreateNoticeDTO createNoticeDTO){

        try {
            Admin admin = adminRepository.findByAdno(createNoticeDTO.getAdno());
            Notice notice = new Notice(admin, createNoticeDTO.getTitle(), createNoticeDTO.getTitle());
            noticeRepository.save(notice);
            log.info("게시판 생성 완료");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    public void deleteNotice(Long adno){

        try{
            Admin admin = adminRepository.findByAdno(adno);
            Notice notice = noticeRepository.findByAdmin(admin);
            noticeRepository.delete(notice);
            log.info("공지사항 삭제");
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public Page<Notice> getNotice(Integer page, Integer size){
        try{
            Pageable pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "nno");
           Page<Notice> notices = noticeRepository.findAll(pageable);
            return notices;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
