package web.mvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Todo;
import web.mvc.dto.TodoDto;
import web.mvc.service.TodoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "TodoController API", description = "Swagger 테스트용 API")
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final ModelMapper modelMapper;

    // todo 조회
    @Operation(summary = "Todo List 조회", description = "사용자가 등록한 Todo List의 조회")
    @GetMapping
    public ResponseEntity<?> getTodoListById(){
        log.info("Todo List 조회");
        List<Todo> todoList = todoService.findTodoList();
        List<TodoDto> todoDtoList = todoList.stream().map(data -> modelMapper.map(data, TodoDto.class)).toList();
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    // todo 등록
    @Operation(summary = "Todo 등록", description = "사용자의 할 일 등록")
    @PostMapping
    public ResponseEntity<?> insertTodo (@RequestBody TodoDto todoDto){
        log.info("Todo 등록");
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setStatus(false);
        Todo result = todoService.insertTodo(todo);
        TodoDto resultDto = modelMapper.map(result, TodoDto.class);
        if (result != null) {
            return new ResponseEntity<>(resultDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // todo 수정
    @Operation(summary = "Todo 수정", description = "내용 수정")
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto todoDto){
        log.info("Todo 수정");
        Todo todo = modelMapper.map(todoDto, Todo.class);
        //todo.setUser(new User(todoDto.getUserSeq()));
        log.info("Todo 수정정보 : {}", todo.toString());
        Todo result = todoService.updateTodo(todo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // todo 삭제
    @Operation(summary = "Todo 삭제", description = "Todo 항목 삭제")
    @DeleteMapping("/{todoSeq}")
    public ResponseEntity<?> deleteTodo(@PathVariable long todoSeq) {
        log.info("Todo 삭제");
        todoService.deleteTodo(todoSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // todo 체크
    @Operation(summary = "Todo 완료/미완료 체크", description = "Todo의 상태를 변경합니다")
    @PatchMapping("/{todoSeq}")
    public ResponseEntity<?> toggleTodo (@PathVariable long todoSeq) {
        log.info(("체크상태 변경"));
        todoService.toggleTodo(todoSeq);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
