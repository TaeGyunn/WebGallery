package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.AuthorModifyDTO;
import WebGallery.Gallery.dto.Role;
import WebGallery.Gallery.entity.A_thumb;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.A_TumbRepository;
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
@Transactional
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final GuestRepository guestRepository;
    private final A_TumbRepository a_tumbRepository;
    private final FileStore fileStore;

    public Integer authorJoin(AuthorJoinDTO authorJoinDTO){

        int check = 0;

        try {
            Guest guest = guestRepository.findByGno(authorJoinDTO.getGno());
            A_thumb a_thumb = fileStore.saveThumbFile(authorJoinDTO.getThumb());
            String stodName = a_thumb.getStodname();

            Author author = new Author(
                    guest,
                    authorJoinDTO.getSns(),
                    authorJoinDTO.getComment(),
                    stodName
            );

            Author save = authorRepository.save(author);

            if(save != null){
                a_thumb.saveAuthor(author);
                a_tumbRepository.save(a_thumb);
                guest.changeRole(Role.AUTHOR);
                guestRepository.save(guest);
                check = 1;
                log.info("Author Save Success");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    public void authorDelete(Long gno){

        try {
            Author author = authorRepository.findByGno(gno);
            authorRepository.delete(author);
            log.info("author delete success");
            Guest guest = guestRepository.findByGno(gno);
            guest.changeRole(Role.GUEST);
            guestRepository.save(guest);
            log.info("Role Change");
        } catch (IllegalArgumentException exception){
            exception.printStackTrace();
        }

    }

    public Integer authorModify(AuthorModifyDTO authorModifyDTO){

        Author author = authorRepository.findByGno(authorModifyDTO.getGno());
        int check = 0;
        String oldStod_name = "1";
        try {
            if (!author.getSns().equals(authorModifyDTO.getSns())) {
                author.changeSns(authorModifyDTO.getSns());
                check = 1;
            }
            if (!author.getComment().equals(authorModifyDTO.getComment())) {
                author.changeComment(authorModifyDTO.getComment());
                check = 1;
            }

            A_thumb a_thumb = fileStore.saveThumbFile(authorModifyDTO.getThumb());
            String stodName = a_thumb.getStodname();

            if (!author.getThumb().equals(stodName)) {
                oldStod_name = author.getThumb();
                author.changeThumb(stodName);
                check = 2;
            }
            if (check == 1 || check == 2) {
                authorRepository.save(author);
                if (check == 2) {
                    A_thumb aThumb = a_tumbRepository.findByStodname(oldStod_name);
                    aThumb.changeStod_name(stodName);
                    a_tumbRepository.save(aThumb);
                }
                return check;
            }
        }catch (IllegalArgumentException | IOException exception){
            exception.printStackTrace();
        }

        return check;
    }


}
