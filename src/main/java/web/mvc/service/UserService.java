package web.mvc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import web.mvc.domain.User;
import web.mvc.dto.SignUpRequest;

public interface UserService {

    // 회원가입 : 성공시 가입한 사람의 닉네임을 반환
    public String signUp(User user);

    // 로그인
    public String login(String userId, String password);

}
