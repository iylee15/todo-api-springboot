package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.domain.User;
import web.mvc.dto.SignUpRequest;
import web.mvc.service.UserService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    // 회원가입
    @PostMapping("/user")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        log.info("회원가입 진행합니다.");
        User user = modelMapper.map(signUpRequest, User.class);
        log.info("사용자 정보 : id: {} / nickname : {}", user.getUserId(), user.getNickname());
        String result = userService.signUp(user);
        if(result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // 로그인

    // 로그아웃
}
