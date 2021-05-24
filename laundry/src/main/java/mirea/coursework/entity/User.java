package mirea.coursework.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**Класс-сущность, отвечающий за хранение информации о пользователях.
Имплементирует интерфейс UserDetails из SpringSecurity*/

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    /**Имя пользователя(хотя пользователя просят ввести адрес электронной почты)*/
    @Column(name = "name")
    String username;
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
    /**ИСпользуется для проверки активации аккаунта*/
    @Column(name = "activated")
    boolean isActive = false;


    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean getActive() {
        return isActive;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**Функция возвращает 1 если аккаунта активен, иначе 0
     * @return true или false*/
    @Override
    public boolean isEnabled() {
        return isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

}
