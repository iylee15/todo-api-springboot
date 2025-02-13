package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 목록 조회
//    @Query("select t from Todo t where t.user.userSeq = ?1")
//    List<Todo> findTodoByUser(long userSeq);

    @Query("select t from Todo t order by t.date desc")
    List<Todo> findTodoList();

//    @Query("select t from Todo t where t.todoSeq = ?1")
//    Todo findById(long todoSeq);

    // Todo 등록

}
