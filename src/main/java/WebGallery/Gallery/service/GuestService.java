package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.GuestJoinDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GuestService implements UserDetailsService {

    private final GuestRepository guestRepository;

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email){
        return guestRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public boolean checkIdDuplication(Long id) {return guestRepository.existsById(id);}

    @Transactional(readOnly = true)
    public boolean checkNickDuplication(String nick) {return guestRepository.existsByNick(nick);}

    @Transactional
    public Long join(GuestJoinDTO guestJoinDTO) {

        Guest guest = new Guest(
                guestJoinDTO.getName(),
                guestJoinDTO.getId(),
                guestJoinDTO.getPw(),
                guestJoinDTO.getNick(),
                guestJoinDTO.getEmail(),
                guestJoinDTO.getRole());
        
        guestRepository.save(guest);
        return guest.getGno();
    }



    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException{
        log.info(" loadUserByUsername ");
        Optional<Guest> userEntityWrapper = guestRepository.findById(id);
        Guest guest = userEntityWrapper.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if(guest.getRole() == 1){
            authorities.add(new SimpleGrantedAuthority(Role.GUEST.getValue()));
        }else if(guest.getRole() == 2){
            authorities.add(new SimpleGrantedAuthority(Role.AUTHOR.getValue()));
        }

        return new User(guest.getId(), guest.getPw(), authorities);
    }
}
