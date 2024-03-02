package blogExample.blog.repository;

import blogExample.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //NullPointerException을 방지를 위해 Null값이 들어가도 되게 함
    Optional<User> findByEmail(String email);
}