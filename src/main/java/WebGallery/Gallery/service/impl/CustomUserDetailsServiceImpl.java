package WebGallery.Gallery.service.impl;

import WebGallery.Gallery.domain.UserDetailImpl;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
            return guestRepository.findById(id).orElseThrow(() ->
                    new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    }

    private UserDetails createUserDetails(Guest guest){
        return new Guest(guest.getId(), guest.getPw(), guest.getRole());
    }
}
