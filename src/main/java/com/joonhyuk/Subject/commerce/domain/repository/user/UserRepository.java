package com.joonhyuk.Subject.commerce.domain.repository.user;

import com.joonhyuk.Subject.commerce.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByEmail(String email);

  Optional<User> findByEmail(String email);

  Optional<User> findByNickname(String nickname);

  Optional<User> findByPhone(String phone);

  Optional<User> findByName(String name);
}
