package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.dto.GuestModifyDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.dto.Role;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GuestService {

    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email) {
        return guestRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkIdDuplication(Long id) {
        return guestRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean checkNickDuplication(String nick) {
        return guestRepository.existsByNick(nick);
    }

    public boolean checkEmailAndName(String email, String name){
        log.info("check");
        Guest guest = guestRepository.findByEmail(email);
        if(guest.getName().equals(name)){
            return true;
        }
        return false;
    }

    public Long join(GuestJoinDTO guestJoinDTO) {

        String encodedPassword = passwordEncoder.encode(guestJoinDTO.getPw());
        Guest guest = new Guest(
                guestJoinDTO.getName(),
                guestJoinDTO.getId(),
                encodedPassword,
                guestJoinDTO.getNick(),
                guestJoinDTO.getEmail(),
                Role.GUEST);
        guestRepository.save(guest);
        return guest.getGno();
    }

    @Transactional(readOnly = true)
    public String Login(LoginDTO loginDTO){

        Guest guest = guestRepository.findById(loginDTO.getId());
        if(guest != null) {

            if (passwordEncoder.matches(loginDTO.getPw(), guest.getPw())) {
                return guest.getNick();
            } else {
                log.info("올바르지 않은 패스워드");
            }
        }
        log.info("올바르지 않은 아이디");
        return null;
    }

    @Transactional(readOnly = true)
    public Guest findGuestNick(String nick){
        Guest guest = guestRepository.findByNick(nick);
        return guest;
    }


    public String logout(HttpSession session) {
        if(session != null){
            session.invalidate();
            return "true";
        }else{
            log.info("로그인 후 로그아웃 해주세요");
            return "false";
        }
    }

    public void deleteGuest(Long gno){
        try {
            Guest guest = guestRepository.findByGno(gno);
            guestRepository.delete(guest);
            log.info("guest delete success");
        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
    }

    public Integer modifyGuest(GuestModifyDTO guestModifyDTO){

        log.info(guestModifyDTO.toString());
        int check = 0;
        try {
            Guest guest = guestRepository.findByGno(guestModifyDTO.getGno());

            if (!guest.getNick().equals(guestModifyDTO.getNick())) {
                guest.changeNick(guestModifyDTO.getNick());
                check = 1;
            }
            if (!guest.getPw().equals(guestModifyDTO.getPw())) {
                guest.changePw(guestModifyDTO.getPw());
                check = 1;
            }
            if (!guest.getEmail().equals(guestModifyDTO.getEmail())) {
                guest.changeEmail(guestModifyDTO.getEmail());
                check = 1;
            }
            if (check == 1) {
                guestRepository.save(guest);
            } else {
                log.info("guest 수정사항 없음");
            }
        }catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }
        return check;
    }

}
