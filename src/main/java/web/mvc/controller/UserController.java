package web.mvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.User;
import web.mvc.dto.SignUpRequest;
import web.mvc.service.UserService;

//@RestController
//@RequiredArgsConstructor
//@Validated
//@Slf4j
//@Tag(name = "UserController API", description = "Swagger 테스트용 API")
//public class UserController {
//
//    private final UserService userService;
//    private final ModelMapper modelMapper;
//
//    // 회원가입
//    @Operation(summary = "회원가입", description = "")
//    @PostMapping("/user")
//    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
//        log.info("회원가입 진행합니다.");
//        User user = modelMapper.map(signUpRequest, User.class);
//        log.info("사용자 정보 : id: {} / nickname : {}", user.getUserId(), user.getNickname());
//        String result = userService.signUp(user);
//        if(result != null) {
//            return new ResponseEntity<>(result + "님 가입을 환영합니다!", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    // 로그인
//    @Operation(summary = "로그인", description = "")
//    @GetMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String userId, @RequestParam String password) {
//        log.info("로그인 진행합니다.");
//        String result = userService.login(userId, password);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    // 로그아웃
//}
