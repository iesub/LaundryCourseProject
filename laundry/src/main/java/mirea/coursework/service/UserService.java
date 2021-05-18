package mirea.coursework.service;

import mirea.coursework.entity.Role;
import mirea.coursework.entity.User;
import mirea.coursework.repository.RoleRepository;
import mirea.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

/** Сервис пользователей
* Позволяет сохранить пользователя, загрузить по
* имени пользователя, а также имеет отдельны метод
* для регистрации пользователя
 * */

@Service
public class UserService implements UserDetailsService {
    /**Поле с JPA репозиторием, отвечающим за связь с таблицей пользователей*/
    @Autowired
    private UserRepository userRepository;
    /**Поле с методом кодирования*/
    @Autowired
    BCryptPasswordEncoder encoder;

    /**Базовый конструктор*/
    public UserService(){}

    /**Конструктор с указанием JPA репозитория, отвечающего за связь с таблицей пользователей
     * @param userRepository - JPA репозиторий*/
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /** Функция отвечает за загрузку пользователя по его имени из базы данных
     * @param username - ключ, по которому происходит поиск
     * @return возвращает объект класса User
     * */
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    /** Функция отвечает за регистрацию пользователя
     * @param user - объект класса User
     * @return возвращает true, если регистрация прошла успешно, иначе false
     * */
    public boolean registerUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB != null) {
            return false;
        }

        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /** Функция отвечает за поиск пользователя по id
     * @param userId - id пользователя
     * @return возвращает объект класса User
     * */
    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    /** Функция обновляет содержимое ряда в базе данных
     * @param user - объект класса User
     * */
    public void updateRow(User user){
        userRepository.save(user);
    }
}