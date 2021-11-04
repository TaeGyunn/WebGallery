//package WebGallery.Gallery.service;
//
//import WebGallery.Gallery.dto.GuestJoinDTO;
//import WebGallery.Gallery.dto.GuestModifyDTO;
//import WebGallery.Gallery.dto.LoginDTO;
//import WebGallery.Gallery.entity.Guest;
//import WebGallery.Gallery.repository.GuestRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional()
//public class GuestServiceTest {
//
//    @Autowired
//    GuestRepository guestRepository;
//    @Autowired
//    GuestService guestService;
//
//    @Test
//    public void 회원가입(){
//
//        //given
//        GuestJoinDTO guestJoinDTO = new GuestJoinDTO();
//        guestJoinDTO.setId("Test1102");
//        guestJoinDTO.setPw("1234");
//        guestJoinDTO.setName("name");
//        guestJoinDTO.setNick("짱구1102");
//        guestJoinDTO.setEmail("test1102@naver.com");
//        //when
//        Long saveNo = guestService.join(guestJoinDTO);
//
//        //then
//        System.out.println("gusetNumber : " + saveNo);
//    }
//
//    @Test
//    public void 로그인(){
//
//        //given
//        LoginDTO loginDTO = new LoginDTO();
//        loginDTO.setId("Test");
//        loginDTO.setPw("1234");
//        //when
//        String loginGuestNick = guestService.Login(loginDTO);
//        Guest guest = guestService.findGuestNick(loginGuestNick);
//
//        //then
//        if(loginGuestNick != null){
//            System.out.println("로그인 성공");
//        }
//    }
//
//    @Test
//    public void 개인정보변경(){
//
//        //given
//        GuestModifyDTO guestModifyDTO = new GuestModifyDTO(8L, "짱구", "1234","test123@naver.com");
//        int check = 0;
//
//        //when
//        Guest guest = guestRepository.findByGno(guestModifyDTO.getGno());
//
//        if (!guest.getNick().equals(guestModifyDTO.getNick())) {
//            guest.changeNick(guestModifyDTO.getNick());
//            check = 1;
//        }
//        if (!guest.getPw().equals(guestModifyDTO.getPw())) {
//            guest.changePw(guestModifyDTO.getPw());
//            check = 1;
//        }
//        if (!guest.getEmail().equals(guestModifyDTO.getEmail())) {
//            guest.changeEmail(guestModifyDTO.getEmail());
//            check = 1;
//        }
//        if (check == 1) {
//           guest = guestRepository.save(guest);
//        } else {
//            System.out.println("수정사항 없음");
//        }
//
//        //then
//        assertEquals(guest.getEmail(), guestModifyDTO.getEmail());
//    }
//
//    @Test
//    public void 이메일아이디체크(){
//        String email = "test1234@naver.com";
//        String name = "name";
//        boolean test = guestService.checkEmailAndName(email, name);
//        System.out.println("====================="+ test+"============================");
//    }
//
//
//
//
//}