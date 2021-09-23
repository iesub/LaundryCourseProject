package mirea.coursework.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**Класс-сущность, хранит все информацию о ролях.
Имплементирует интерфейс GrantedAuthority из
Spring Security
*/

@Entity
@Getter
@Setter
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    /**Название роли*/
    private String name;
    /**Пользователи с данной ролью*/
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
    /**Стандартный пустой конструктор*/
    public Role() {
    }

    /**Конструктор с указанием id*/
    public Role(Long id) {
        this.id = id;
    }

    /**Конструктор с указанием id и названия*/
    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    /**Функция возвращает название роли, для проверки ролей пользователя
     * @return Строка с названием роли*/
    @Override
    public String getAuthority() {
        return getName();
    }
}
