package WebGallery.Gallery.controller;

import WebGallery.Gallery.dto.*;
import WebGallery.Gallery.service.GuestService;
import WebGallery.Gallery.service.Helper;
import WebGallery.Gallery.service.MailService;
import WebGallery.Gallery.util.Response;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JoinController {

    private final GuestService guestService;
    private final MailService mailService;
    private final Response response;


    // 아이디 중복 확인
    @ApiOperation(value="아이디 중복 체크", notes = "회원가입 시 id가 이미 존재하는지 확인합니다.")
    @ApiImplicitParam(name ="id", value = "회원가입 시 사용 id", example="testId", dataType = "String", paramType = "path")
    @GetMapping("/guest-id/{id}/exists")
    public ResponseEntity<?> checkIdDuplication(@PathVariable String id){

        //true면 중복 false면 중복x
        boolean check = guestService.checkIdDuplication(id);
        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("duplication", true);
            return response.success(map,"아이디가 중복입니다.", HttpStatus.OK);
        }else{
            map.put("duplication", false);
            return response.success(map,"아이디 사용 가능합니다.",HttpStatus.OK);
        }

    }

    // 이메일 중복 확인
    @ApiOperation(value="이메일 중복 체크", notes = "회원가입 시 email이 이미 존재하는지 확인합니다.")
    @ApiImplicitParam(name ="email", value = "회원가입 시 사용 email", example="Test04140@naver.com", dataType = "String", paramType = "path")
    @GetMapping("/guest-emails/{email}/exists")
    public ResponseEntity<?> checkEmailDuplication(@PathVariable String email){

        boolean check = guestService.checkEmailDuplication(email);
        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("duplication", true);
            return response.success(map,"이메일이 중복입니다.",HttpStatus.OK);
        }else{
            map.put("duplication", false);
            return response.success(map,"이메일 사용 가능합니다.",HttpStatus.OK);
        }
    }

    // 닉네임 중복 확인
    @ApiOperation(value="닉네임 중복 체크", notes = "회원가입 시 nick이 이미 존재하는지 확인한다.")
    @ApiImplicitParam(name ="nick", value = "회원가입 시 사용 nick", example="Test0414", dataType = "String", paramType = "path")
    @GetMapping("/guest-nick/{nick}/exists")
    public ResponseEntity<?> checkNickDuplication(@PathVariable String nick){
        boolean check = guestService.checkNickDuplication(nick);
        Map<String, Boolean> map = new HashMap<>();


        if(check){
            map.put("duplication", true);
            return response.success(map,"닉네임 중복입니다.",HttpStatus.OK);
        }else{
            map.put("duplication", false);
            return response.success(map,"닉네임 사용 가능합니다.",HttpStatus.OK);

        }
    }

    //이메일 이름 일치 확인
    @ApiOperation(value="이메일 이름 일치 확인", notes = "비밀번호 찾기 전 이메일과 이름이 일치하는지 확인한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="email", value = "등록했던 email", example="Test04140@naver.com", dataType = "String", paramType = "path")
            ,@ApiImplicitParam(name="id", value = "등록했던 id", example="Test0414", dataType = "String", paramType = "path")})
    @GetMapping("/check/findpw/{email}/{id}")
    public ResponseEntity<?> pw_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "id") String id){

        boolean check = guestService.checkEmailAndId(email, id);
        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("coincide", true);
            return response.success(map,"이메일과 이름이 일치합니다.",HttpStatus.OK);
        }else{
            map.put("coincide", false);
            return response.success(map,"이메일과 이름이 일치하지 않습니다.",HttpStatus.OK);
        }
    }

    //id 찾기
    @ApiOperation(value="아이디 체크", notes = "아이디 체크")
    @ApiImplicitParams({
            @ApiImplicitParam(name ="email", value = "등록했던 id", example="Test04140@naver.com",dataType = "String", paramType = "path")
            ,@ApiImplicitParam(name="name", value = "등록했던 name", example="Test0414", dataType = "String", paramType = "path")})
    @GetMapping("/check/findId/{email}/{name}")
    public ResponseEntity<?> id_find(@PathVariable(value = "email") String email,
                                        @PathVariable(value = "name") String name){


        Map<String, String> map = new HashMap<>();
        String id = guestService.findGuestId(email, name);
        if(id == null){
            map.put("id", "null");
            return response.fail(map,"아이디가 존재하지 않습니다",HttpStatus.OK);
        }else{
            map.put("id",id);
            return response.success(map,"아이디는 "+id+" 입니다.",HttpStatus.OK);
        }
    }

    //비번 찾기 메일
    @ApiOperation(value="비밀번호 찾기 메일 전송", notes = "비밀번호 찾기 이메일 전송한다.")
    @PostMapping("/check/findPw/sendmail")
    public ResponseEntity<?> sendMail(@RequestBody FindPwDTO findPwDTO){

        MailDTO mailDTO = mailService.createMailAndChangePassword(findPwDTO);
        mailService.sendMail(mailDTO);
        Map<String, Boolean> map = new HashMap<>();
        map.put("mail", true);

        return response.success(map,"메일 전송",HttpStatus.OK);
    }


    // 회원가입
    @ApiOperation(value="게스트 회원가입", notes = "게스트 회원가입을 한다.")
    @PostMapping("/guestJoin")
    public ResponseEntity<?> guestJoin(@Validated @RequestBody GuestJoinDTO guestJoinDTO,
                                                         Errors errors){

        if(errors.hasErrors()){
            return response.invalidFields(Helper.refineErrors(errors));
        }

        Map<String, Boolean> map = new HashMap<>();

        if(guestService.checkIdDuplication(guestJoinDTO.getId())){
            map.put("join", false);
            return response.fail(map,"아이디가 중복입니다.", HttpStatus.BAD_REQUEST);
        }else if( guestService.checkEmailDuplication(guestJoinDTO.getEmail())){
            map.put("join", false);
            return response.fail(map,"이메일이 중복입니다.", HttpStatus.BAD_REQUEST);
        }else if(guestService.checkNickDuplication(guestJoinDTO.getNick())){
            map.put("join",false);
            return response.fail(map,"닉네임이 중복입니다.", HttpStatus.BAD_REQUEST);
        }


        Long gno = guestService.join(guestJoinDTO);

        if(gno > 0){
            map.put("join", true);
            return response.success(map, "회원가입 성공", HttpStatus.OK);
        }else{
            map.put("join", false);
            return response.fail(map, "회원가입 실패", HttpStatus.BAD_REQUEST);
        }

    }


    // 로그인
    @ApiOperation(value="로그인", notes = "로그인을 한다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO,
                                   Errors errors){

            if(errors.hasErrors()){
                return response.invalidFields(Helper.refineErrors(errors));
            }
        return guestService.login(loginDTO);

    }

    // 로그아웃
    @ApiOperation(value="로그아웃", notes = "로그아웃을 한다.")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody  LogoutDTO logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return guestService.logout(logout);
    }
    
    // 재발급
    @ApiOperation(value="토큰 재발급", notes = "토큰 재발급을 한다.")
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@Validated @RequestBody ReissueDTO reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return guestService.reissue(reissue);
    }

//    //관리자 로그인
//    @PostMapping("/adminLogin")
//    public ResponseEntity adminLogin(@Valid @RequestBody LoginDTO loginDTO, BindingResult bindingResult,
//                          @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request){
//
//        log.info("login : {}, {}", loginDTO.getId(), loginDTO.getPw());
//
//        if(bindingResult.hasFieldErrors()){
//            log.info("로그인 바인딩 에러");
//        }
//
//        //로그인 성공
//        Admin admin = adminService.Login(loginDTO);
//        HttpSession session = request.getSession();
//        session.setAttribute("loggedIn",admin.getNick());
//        session.setAttribute("role",admin.getRole());
//        log.info("관리자 로그인 성공");
//
//        return new ResponseEntity(HttpStatus.OK);
//    }



}
