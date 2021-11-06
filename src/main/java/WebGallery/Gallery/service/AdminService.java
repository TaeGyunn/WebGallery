package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.entity.Admin;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
