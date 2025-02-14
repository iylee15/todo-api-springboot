package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.Todo;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 목록 조회 시 우선순위에 따라 우선 정렬 후 해당사항 없는 항목 나열
    @Query("select t from Todo t order by t.priority asc nulls last, t.date desc")
    List<Todo> findTodoList();

}
