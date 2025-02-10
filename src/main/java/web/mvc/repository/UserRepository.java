package web.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import web.mvc.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // 아이디 중복여부 확인
    @Query("select count(u) from User u where u.userId = ?1")
    int existsById(String userId);

    // 닉네임 중복여부 확인
    @Query("select count(u) from User u where u.nickname = ?1")
    int existsByNickname(String nickname);

    // 아이디 검색
    @Query("select u from User u where u.userId = ?1")
    User findByUserId(String userId);
}
