package mirea.coursework.entity;

import mirea.coursework.enumiration.OrderStateEnum;

import javax.persistence.*;
import java.util.Date;

/**
Класс-сущность, отвечающий за хранение информации о заказах
*/

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    /**Вещи для обработки*/
    @Column(name = "clothesForWork")
    String clothesForWork;
    /**Время, когда клиент готов сдать вещи в чистку*/
    @Column(name = "dateToTake")
    String dateToTake;
    /**Время создания заказа*/
    @Column(name = "dateCreated")
    Date dateCreated;
    /**Состояние заказа*/
    @Column(name = "state")
    OrderStateEnum state;
    /**Пользователь, сделавший заказ*/
    @ManyToOne()
    @JoinColumn(name="user_id")
    private User users;

    public void setClothesForWork(String clothesForWork) {
        this.clothesForWork = clothesForWork;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setDateToTake(String dateToTake) {
        this.dateToTake = dateToTake;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(OrderStateEnum state) {
        this.state = state;
    }

    public OrderStateEnum getState() {
        return state;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Long getId() {
        return id;
    }

    public String getClothesForWork() {
        return clothesForWork;
    }

    public String getDateToTake() {
        return dateToTake;
    }

    public void setUsers(User users) {
        this.users = users;
    }

    public User getUsers() {
        return users;
    }
}
