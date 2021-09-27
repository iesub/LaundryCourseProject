package mirea.coursework.repository;

import mirea.coursework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
Репозиторий для пользователей.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    /**Метод осуществляет выборку по полю username в таблице
     * @param username Ключ для поиска
     * @return Искомый пользователь*/
<<<<<<< Updated upstream
    User findByUsername(String username);
=======
    User findByMail(String username);
>>>>>>> Stashed changes
}
