package web.mvc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Todo;
import web.mvc.repository.TodoRepository;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TodoIntegrationTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    void setUp() {
        // 테스트 전 데이터 초기화
        todoRepository.deleteAll();
//        userRepository.deleteAll();

        // 테스트용 데이터
//        User user1 = new User("test1", "1234", "testUser");
//        userRepository.save(user1);
        Todo todo1 = new Todo("Todo1", "할일1", false);
        Todo todo2 = new Todo("Todo2", "할일2", false);
        todoRepository.saveAll(Arrays.asList(todo1, todo2));
    }

    @Test
    @DisplayName("Todo 리스트 조회 테스트")
    void getTodoListById() throws Exception {
        // given
        long userSeq = 1L;

        // when
        ResultActions result = mockMvc.perform(
                get("/todo")
//                        .param("userSeq", String.valueOf(userSeq))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // then
        result
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("Todo1"))
                .andExpect(jsonPath("$[0].status").value(0))
                .andExpect(jsonPath("$[1].title").value("Todo2"))
                .andExpect(jsonPath("$[1].status").value(0))
                .andDo(print());  // 테스트 결과를 콘솔에 출력
    }
}
