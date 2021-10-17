package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.repository.GuestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class GuestServiceTest {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    GuestService guestService;

    @Test
    public void 회원가입(){

        //given
        GuestJoinDTO guestJoinDTO = new GuestJoinDTO();
        guestJoinDTO.setId("Test");
        guestJoinDTO.setPw("1234");
        guestJoinDTO.setName("name");
        guestJoinDTO.setNick("짱구");
        guestJoinDTO.setEmail("test1234@naver.com");
        guestJoinDTO.setRole(1);

        //when
        Long saveNo = guestService.join(guestJoinDTO);

        //then
        System.out.println("아이템 No : " + saveNo);
    }




}