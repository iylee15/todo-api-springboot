package web.mvc.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import web.mvc.domain.Todo;
import web.mvc.repository.TodoRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@DynamicUpdate
@Slf4j
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
        log.info("insertTodo 서비스 시작");
        if (todo.isRecurring()) {
            todo.setLastCreatedAt(LocalDate.now());
        }
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

    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void createRecurringTodos() {
        LocalDate today = LocalDate.now();

        List<Todo> recurringTodos = todoRepository.findByIsRecurringTrue();

        for (Todo originalTodo : recurringTodos) {
            LocalDate lastCreated = originalTodo.getLastCreatedAt();

            if (lastCreated.isBefore(today)) {
                Todo newTodo = new Todo();
                newTodo.setTitle(originalTodo.getTitle());
                newTodo.setDescription(originalTodo.getDescription());
                newTodo.setStatus(false);
                newTodo.setRecurring(false);

                todoRepository.save(newTodo);

                originalTodo.setLastCreatedAt(LocalDate.now());
                todoRepository.save(originalTodo);
            }
        }
    }
}
