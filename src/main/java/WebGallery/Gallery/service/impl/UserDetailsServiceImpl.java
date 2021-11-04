package WebGallery.Gallery.service.impl;

import WebGallery.Gallery.domain.UserDetailImpl;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private GuestRepository guestRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

        Guest guest = guestRepository.findById(id);

        return new UserDetailImpl(guest.getId(), guest.getPw(), guest.getRole().toString());
    }
}
