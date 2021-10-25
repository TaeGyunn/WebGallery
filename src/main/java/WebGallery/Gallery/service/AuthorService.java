package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.Role;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.util.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final GuestRepository guestRepository;
    private final FileStore fileStore;

    @Transactional
    public Integer authorJoin(AuthorJoinDTO authorJoinDTO){

        int check = 0;

        try {
            Guest guest = guestRepository.findByGno(authorJoinDTO.getGuestNo());
            String stodName = fileStore.saveThumbFile(authorJoinDTO.getThumb()).getStod_name();

            Author author = new Author(
                    guest,
                    authorJoinDTO.getSns(),
                    authorJoinDTO.getComment(),
                    stodName
            );

            Author save = authorRepository.save(author);

            if(save != null){
                guest.ChangeRole(Role.AUTHOR);
                guestRepository.save(guest);
                check = 1;
                log.info("Author Save Success");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }


}
