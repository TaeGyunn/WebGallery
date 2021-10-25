package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.AuthorJoinDTO;
import WebGallery.Gallery.dto.Role;
import WebGallery.Gallery.entity.Author;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.AuthorRepository;
import WebGallery.Gallery.repository.GuestRepository;
import WebGallery.Gallery.util.FileStore;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AuthorServiceTest {

    @Autowired AuthorRepository authorRepository;
    @Autowired GuestRepository guestRepository;
    @Autowired FileStore fileStore;


    @Test
    @Rollback(value = false)
    public void 작가회원가입(){

        MockMultipartFile thumb = null;
        try {
            thumb = new MockMultipartFile("content","sample_images_01.png","multipart/mixed", new FileInputStream("/Users/taeku/Desktop/샘플이미지_12가지_모음/sample_images_01.png"));
            AuthorJoinDTO authorJoinDTO = new AuthorJoinDTO(8L, "sexual", "안녕하세요", thumb );
            Guest guest = guestRepository.findByGno(authorJoinDTO.getGuestNo());
            String stodName = fileStore.saveThumbFile(authorJoinDTO.getThumb()).getStod_name();

            Author author = new Author(
                    guest,
                    authorJoinDTO.getSns(),
                    authorJoinDTO.getComment(),
                    stodName
            );
            Author save = authorRepository.save(author);
            guest.ChangeRole(Role.AUTHOR);
            guestRepository.save(guest);
            assertNotNull(save);

        } catch (IOException e) {
            e.printStackTrace();
        }




    }

}