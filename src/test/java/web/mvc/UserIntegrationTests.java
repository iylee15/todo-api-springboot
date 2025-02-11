package web.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.controller.UserController;
import web.mvc.domain.User;
import web.mvc.dto.SignUpRequest;
import web.mvc.repository.TodoRepository;
import web.mvc.repository.UserRepository;
import web.mvc.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
//@WebMvcTest(UserController.class)
public class UserIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserController userController;

    @BeforeEach
    void setUp() {
        // 테스트 전 데이터 초기화
        todoRepository.deleteAll();
        userRepository.deleteAll();

        // 테스트용 데이터
//        User user1 = new User("test1", "1234", "testUser");
//        userRepository.save(user1);
    }

    @Test
    @DisplayName("User SignUp 테스트")
    void signUpTest() throws Exception {
        // given
        SignUpRequest signUpRequest = SignUpRequest.builder().userId("user1").password("1234").nickname("테스트유저").build();
                //("user1", "password", "nickname");
        String successMessage = "테스트유저";

        User testUser = modelMapper.map(signUpRequest, User.class);

//        when(userService.signUp(any(User.class))).thenAnswer(invocation -> {
//            User argumentUser = invocation.getArgument(0); // signUp에 전달된 User 객체 가져오기
//            return argumentUser.getNickname(); // 닉네임 반환
//        });

        // when
        ResultActions result = mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signUpRequest)));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string("테스트유저님 가입을 환영합니다!")).andDo(print());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
//        verify(userService).signUp(userCaptor.capture());
//
//        User capturedUser = userCaptor.getValue();
//        assertNotNull(capturedUser);
//        assertEquals("user1", capturedUser.getUserId());
    }
}
