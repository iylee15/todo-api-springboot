package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import web.mvc.domain.Todo;
import web.mvc.dto.TodoDto;
import web.mvc.dto.TodoRequestDto;
import web.mvc.service.TodoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;
    private final ModelMapper modelMapper;

    // test
    @GetMapping("/hello")
    public String test() {
        return "Hello World";
    }

    // todo 조회
    @GetMapping("/todoList")
    public ResponseEntity<?> getTodoListById(long userSeq){
        log.info("Todo List 조회");
        List<Todo> todoList = todoService.findTodoById(userSeq);
        List<TodoDto> todoDtoList = todoList.stream().map(data -> modelMapper.map(data, TodoDto.class)).toList();
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    // todo 등록
    @PostMapping("/todo")
    public ResponseEntity<?> insertTodo (@RequestBody TodoDto todoDto){
        log.info("Todo 등록");
        Todo todo = modelMapper.map(todoDto, Todo.class);
        int result = todoService.insertTodo(todo);
        if (result == 1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // todo 수정

    // todo 삭제


}
