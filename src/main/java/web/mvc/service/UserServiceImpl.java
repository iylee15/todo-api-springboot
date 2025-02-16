package web.mvc.service;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import web.mvc.domain.User;
import web.mvc.repository.UserRepository;

@Service
@Transactional
@DynamicUpdate
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //    private PasswordEn

    @Override
    public String signUp(User user) {
        // 중복체크
        if(userRepository.existsById(user.getUserId()) > 0) {
            return "아이디 중복입니다";
        } else if (userRepository.existsByNickname(user.getNickname()) > 0) {
            return "닉네임 중복입니다";
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        return result.getNickname();
    }

    @Override
    public String login(String userId, String password) {
        bCryptPasswordEncoder.matches(password, userRepository.findByUserId(userId).getPassword());
        return "로그인 성공";
    }
}