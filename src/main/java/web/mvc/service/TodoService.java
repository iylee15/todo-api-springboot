package web.mvc.service;

import web.mvc.domain.Todo;

import java.util.List;

public interface TodoService {
    /**
     * Todo 조회
     */
//    public List<Todo> findTodoById(long userSeq);

    public List<Todo> findTodoList();

    /**
     * Todo 등록
     */
    public int insertTodo(Todo todo);


    /**
     * Todo 수정
     */
    public Todo updateTodo(Todo todo);

    /**
     * Todo 삭제
     */
    public int deleteTodo (long todoSeq);

    /**
     * Todo 토글
     */
    public void toggleTodo (long todoSeq);
}
