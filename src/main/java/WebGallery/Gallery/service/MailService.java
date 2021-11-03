package WebGallery.Gallery.service;

import WebGallery.Gallery.dto.MailDTO;
import WebGallery.Gallery.entity.Guest;
import WebGallery.Gallery.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;
    private final GuestRepository guestRepository;
    private final PasswordEncoder passwordEncoder;


    private static final String FROM_ADDRESS = "본인의 이메일 주소를 입력하세요!";



    public MailDTO createMailAndChangePassword(String userEmail, String userName){
        String str = getTempPassword();
        MailDTO DTO = new MailDTO();
        DTO.setAddress(userEmail);
        DTO.setTitle(userName+"님의 Gallery 임시비밀번호 안내 이메일 입니다.");
        DTO.setMessage("안녕하세요. Gallery 임시비밀번호 안내 관련 이메일 입니다." + "[" + userName + "]" +"님의 임시 비밀번호는 "
                + str + " 입니다.");
        updatePassword(str,userEmail);
        return DTO;
    }

    public void updatePassword(String str,String userEmail){
        String pw = passwordEncoder.encode(str);
        Guest guest = guestRepository.findByEmail(userEmail);
        guest.changePw(pw);
        guestRepository.save(guest);
    }


    public String getTempPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }


    public void sendMail(MailDTO mailDTO){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDTO.getAddress());
        message.setSubject(mailDTO.getTitle());
        message.setText(mailDTO.getMessage());
        mailSender.send(message);
    }

}