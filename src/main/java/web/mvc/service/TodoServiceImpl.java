package web.mvc.service;

import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.mvc.domain.Todo;
import web.mvc.repository.TodoRepository;

import java.util.List;

@Service
@Transactional
@DynamicUpdate
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public List<Todo> findTodoList() {
        List<Todo> todoList = todoRepository.findTodoList();
        return todoList;
    }

    @Override
    public Todo insertTodo(Todo todo) {
        try {
            Todo result = todoRepository.save(todo);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Todo updateTodo(Todo todo) {
        Todo dbTodo = todoRepository.findById(todo.getTodoSeq()).orElseThrow(() -> new RuntimeException("등록된 정보가 없습니다."));
        dbTodo.setTitle(todo.getTitle());
        dbTodo.setDescription(todo.getDescription());
        dbTodo.setPriority(todo.getPriority());
        dbTodo.setDate(todo.getDate());
        return dbTodo;
    }

    @Override
    public int deleteTodo(long todoSeq) {
        todoRepository.deleteById(todoSeq);
        return 1;
    }

    @Override
    public void toggleTodo(long todoSeq) {
        Todo todo = todoRepository.findById(todoSeq).orElseThrow(() -> new RuntimeException("등록된 항목이 없습니다."));
        todo.setStatus(!todo.isStatus());
    }
}
