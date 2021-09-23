package mirea.coursework.entity;

import lombok.Getter;
import lombok.Setter;
import mirea.coursework.enumiration.OrderStateEnum;

import javax.persistence.*;
import java.util.Date;

/**
Класс-сущность, отвечающий за хранение информации о заказах
*/

@Entity
@Getter
@Setter
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
}
