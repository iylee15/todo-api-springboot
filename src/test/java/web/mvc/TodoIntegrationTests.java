package web.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.Todo;
import web.mvc.dto.TodoDto;
import web.mvc.repository.TodoRepository;
import web.mvc.service.TodoService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @Autowired
    private TodoService todoService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoDto sampleTodoDto;

    @BeforeEach
    void setUp() {
        // 테스트 전 데이터 초기화
        todoRepository.deleteAll();
        sampleTodoDto = TodoDto.builder()
                .title("테스트 할일")
                .description("테스트 설명")
                .priority(1)
                .isRecurring(false)
                .build();
    }

    @Test
    @DisplayName("Todo 등록 테스트")
    void insertTodoTest() throws Exception {
        // Todo 등록 테스트
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleTodoDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("테스트 할일"))
                .andExpect(jsonPath("$.status").value(0))
                .andReturn();
    }

    @Test
    @DisplayName("Todo 리스트 조회 테스트")
    void getTodoListTest() throws Exception {
        // 테스트용 데이터 given
        Todo todo1 = new Todo("Todo1", "할일1", false);
        Todo todo2 = new Todo("Todo2", "할일2", false);
        todoRepository.saveAll(Arrays.asList(todo1, todo2));

        // when
        ResultActions result = mockMvc.perform(
                get("/todo")
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

    @Test
    @DisplayName("Todo 수정 테스트")
    void updateTodoTest() throws Exception {
        // given
        Todo savedTodo = todoRepository.save(modelMapper.map(sampleTodoDto, Todo.class));

        TodoDto updateDto = modelMapper.map(savedTodo, TodoDto.class);
        updateDto.setTitle("수정된 할일");

        // when // then
        mockMvc.perform(put("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("수정된 할일"));
    }

    @Test
    @DisplayName("Todo 상태 토글 테스트")
    void toggleTodoTest() throws Exception {
        // given
        Todo savedTodo = todoRepository.save(modelMapper.map(sampleTodoDto, Todo.class));

        // when
        mockMvc.perform(patch("/todo/" + savedTodo.getTodoSeq())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // 토글 후 상태 확인 then
        Todo toggledTodo = todoRepository.findById(savedTodo.getTodoSeq())
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        assertTrue(toggledTodo.isStatus());
    }

    @Test
    @DisplayName("Todo 삭제 테스트")
    void deleteTodoTest() throws Exception {
        // given
        Todo savedTodo = todoRepository.save(modelMapper.map(sampleTodoDto, Todo.class));

        // when
        mockMvc.perform(delete("/todo/" + savedTodo.getTodoSeq())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // then
        assertFalse(todoRepository.existsById(savedTodo.getTodoSeq()));
    }

    @Test
    @DisplayName("반복 Todo 생성 테스트")
    void testInsertRecurringTodo() {
        // given
        Todo todo = Todo.builder()
                .title("매일 운동하기")
                .description("스쿼트 30개")
                .isRecurring(true)
                .build();

        // when
        Todo savedTodo = todoService.insertTodo(todo);

        // then
        assertNotNull(savedTodo);
        assertTrue(savedTodo.isRecurring());
        assertEquals(LocalDate.now(), savedTodo.getLastCreatedAt());
    }

    @Test
    @DisplayName("스케줄러 실행 로직 테스트")
    void testCreateRecurringTodos() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);

        Todo recurringTodo = Todo.builder()
                .title("매일 운동하기")
                .description("스쿼트 30개")
                .isRecurring(true)
                .lastCreatedAt(yesterday)
                .build();

        todoRepository.save(recurringTodo);

        // when
        todoService.createRecurringTodos();

        // then
        List<Todo> allTodos = todoRepository.findAll();
        assertEquals(2, allTodos.size());

        // 원본 Todo 검증
        Todo originalTodo = todoRepository.findById(recurringTodo.getTodoSeq()).get();
        assertEquals(LocalDate.now(), originalTodo.getLastCreatedAt());

        // 새로 생성된 Todo 검증
        Todo newTodo = allTodos.stream()
                .filter(t -> !t.isRecurring())
                .findFirst()
                .orElseThrow();

        assertEquals(recurringTodo.getTitle(), newTodo.getTitle());
        assertEquals(recurringTodo.getDescription(), newTodo.getDescription());
        assertFalse(newTodo.isStatus());
        assertFalse(newTodo.isRecurring());
    }

    @Test
    @DisplayName("같은 날짜 중복 생성 방지 테스트")
    void testPreventDuplicateCreation() {
        // given
        Todo recurringTodo = Todo.builder()
                .title("매일 운동하기")
                .description("스쿼트 30개")
                .isRecurring(true)
                .lastCreatedAt(LocalDate.now())  // 오늘 날짜로 설정
                .build();

        todoRepository.save(recurringTodo);

        // when
        todoService.createRecurringTodos();

        // then
        List<Todo> allTodos = todoRepository.findAll();
        assertEquals(1, allTodos.size());  // 새로운 Todo가 생성되지 않아야 함
    }

    @Test
    @DisplayName("여러 개의 반복 Todo 처리 테스트")
    void testMultipleRecurringTodos() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);

        Todo recurringTodo1 = Todo.builder()
                .title("매일 운동하기")
                .description("스쿼트 30개")
                .isRecurring(true)
                .lastCreatedAt(yesterday)
                .build();

        Todo recurringTodo2 = Todo.builder()
                .title("매일 독서하기")
                .description("30분")
                .isRecurring(true)
                .lastCreatedAt(yesterday)
                .build();

        todoRepository.saveAll(Arrays.asList(recurringTodo1, recurringTodo2));

        // when
        todoService.createRecurringTodos();

        // then
        List<Todo> allTodos = todoRepository.findAll();
        assertEquals(4, allTodos.size());

        List<Todo> newTodos = allTodos.stream()
                .filter(t -> !t.isRecurring())
                .toList();
        assertEquals(2, newTodos.size());
    }

    @Test
    @DisplayName("Todo 유효성 검사 테스트")
    void validationTest() throws Exception {
        // given
        TodoDto invalidTodo = TodoDto.builder()
                .title("")
                .build();

        // when // then
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidTodo)))
                .andExpect(status().isBadRequest());
    }
}
