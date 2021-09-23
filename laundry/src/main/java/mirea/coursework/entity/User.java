package mirea.coursework.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**Класс-сущность, отвечающий за хранение информации о пользователях.
Имплементирует интерфейс UserDetails из SpringSecurity*/

@Entity
@Getter
@Setter
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    /**Адрес электронной почты*/
    @Column(name = "mail")
    String mail;
    /**Фамилия клиента*/
    @Column(name = "surname")
    String surname;
    /**Телефонный номер клиента*/
    @Column(name = "phone_number")
    String phoneNumber;
    /**Имя клиента*/
    @Column(name = "name_real")
    String name;
    /**Пароль*/
    @Column(name = "password")
    String password;
    /**Поле, используемое для повторного пароля, используется в валидации формы*/
    @Transient
    private String passwordConfirm;
    /**Список ролей пользователя. Используются SpringSecurity для
     * допуска на опроеделенные страницы сайта*/
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
    /**Список заказов, оформленных пользователем*/
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
    private List<Order> orders;
    /**Используется для проверки активации аккаунта*/
    @Column(name = "activated")
    boolean isActive = false;

    /**Метод из UserDetails, всегда возвращает true, т.к. аккаунт не может истеч
     * @return Адрес электронной почты клиента*/
    @Override
    public String getUsername() {
        return mail;
    }

    /**Метод из UserDetails, всегда возвращает true, т.к. аккаунт не может истеч
     * @return Всегда true*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    /**Метод из UserDetails, всегда возвращает true, т.к. в данной программе нельзя заблокировать пользователя
     * @return Всегда true*/
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    /**ФМетод из UserDetails, всегда возвращает true, т.к. данные для входа в аккаунт не могут истеч
     * @return Всегда true*/
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**Функция возвращает 1 если аккаунта активен, иначе 0
     * @return true или false*/
    @Override
    public boolean isEnabled() {
        return isActive;
    }

    /**Функция возвращает список ролей пользователя
     * @return Список ролей пользователя*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

}
