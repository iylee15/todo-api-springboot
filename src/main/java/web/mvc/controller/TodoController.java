package web.mvc.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.mvc.domain.Todo;
import web.mvc.domain.User;
import web.mvc.dto.TodoDto;
import web.mvc.dto.TodoRequestDto;
import web.mvc.service.TodoService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;
    private final ModelMapper modelMapper;

    // test
    @GetMapping("/hello")
    public String test() {
        return "Hello World";
    }

    // todo 조회
    @GetMapping
    public ResponseEntity<?> getTodoListById(long userSeq){
        log.info("Todo List 조회");
        List<Todo> todoList = todoService.findTodoById(userSeq);
        List<TodoDto> todoDtoList = todoList.stream().map(data -> modelMapper.map(data, TodoDto.class)).toList();
        return new ResponseEntity<>(todoDtoList, HttpStatus.OK);
    }

    // todo 등록
    @PostMapping
    public ResponseEntity<?> insertTodo (@RequestBody TodoDto todoDto){
        log.info("Todo 등록");
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setStatus(0);
        int result = todoService.insertTodo(todo);
        if (result == 1) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // todo 수정
    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDto todoDto){
        log.info("Todo 수정");
        Todo todo = modelMapper.map(todoDto, Todo.class);
        todo.setUser(new User(todoDto.getUserSeq()));
        log.info("Todo 수정정보 : {}", todo.toString());
        Todo result = todoService.updateTodo(todo);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // todo 삭제
    public ResponseEntity<?> deleteTodo() {
        return null;
    }

    // todo 체크


}
