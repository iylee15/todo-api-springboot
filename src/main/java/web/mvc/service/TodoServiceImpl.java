package web.mvc.service;

import jakarta.transaction.Transactional;
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
    public List<Todo> findTodoById(long userSeq) {
        List<Todo> todoList = todoRepository.findTodoByUser(userSeq);
        return todoList;
    }

    @Override
    public int insertTodo(Todo todo) {
        try {
            Todo result = todoRepository.save(todo);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }
}
