package mirea.coursework.repository;

import mirea.coursework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Репозиторий для пользователей. Имеет дополнительный метод для
поиска по имени пользователя
 */

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
