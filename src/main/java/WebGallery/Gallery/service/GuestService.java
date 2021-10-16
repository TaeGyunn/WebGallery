package WebGallery.Gallery.service;

import WebGallery.Gallery.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestService {

    private final GuestRepository guestRepository;

    @Transactional(readOnly = true)
    public boolean checkEmailDuplication(String email){
        return guestRepository.existsByEmail(email);
    }
}
