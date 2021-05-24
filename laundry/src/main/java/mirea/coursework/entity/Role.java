package mirea.coursework.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**Класс-сущность, хранит все информацию о ролях.
Имплементирует интерфейс GrantedAuthority из
Spring Security
*/

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**Функция возвращает название роли, для проверки ролей пользователя
     * @return String name*/
    @Override
    public String getAuthority() {
        return getName();
    }
}
