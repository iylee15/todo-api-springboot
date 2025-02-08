package web.mvc.service;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.User;
import web.mvc.dto.SignUpRequest;
import web.mvc.repository.UserRepository;

@Service
@Transactional
@DynamicUpdate
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public String signUp(User user) {
        User result = userRepository.save(user);
        return result.getNickname();
    }
}
