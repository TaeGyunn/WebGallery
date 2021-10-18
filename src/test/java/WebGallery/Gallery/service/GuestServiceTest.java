package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.GuestJoinDTO;
import WebGallery.Gallery.dto.LoginDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional()
public class GuestServiceTest {

    @Autowired
    GuestRepository guestRepository;
    @Autowired
    GuestService guestService;

    @Test
    @Rollback(value = false)
    public void 회원가입(){

        //given
        GuestJoinDTO guestJoinDTO = new GuestJoinDTO();
        guestJoinDTO.setId("Test");
        guestJoinDTO.setPw("1234");
        guestJoinDTO.setName("name");
        guestJoinDTO.setNick("짱구");
        guestJoinDTO.setEmail("test1234@naver.com");
        //when
        Long saveNo = guestService.join(guestJoinDTO);

        //then
        System.out.println("아이템 No : " + saveNo);
    }

    @Test
    public void 로그인(){

        //given
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setId("Test");
        loginDTO.setPw("1234");
        //when
        String loginGuestNick = guestService.Login(loginDTO);
        Guest guest = guestService.findGuestNick(loginGuestNick);

        //then
        if(loginGuestNick != null){
            System.out.println("로그인 성공");
        }

    }




}