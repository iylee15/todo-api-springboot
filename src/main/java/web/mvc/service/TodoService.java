package web.mvc.service;

import web.mvc.domain.Todo;

import java.util.List;

public interface TodoService {
    /**
     * Todo 조회
     */
    public List<Todo> findTodoById(long userSeq);

    /**
     * Todo 등록
     */
    public int insertTodo(Todo todo);
}
