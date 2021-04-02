package mirea.coursework.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import mirea.coursework.enumiration.OrderStateEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = Long.valueOf(0);
    @Column(name = "clothesForWork")
    String clothesForWork;
    @Column(name = "dateToTake")
    String dateToTake;
    @Column(name = "dateCreated")
    Date dateCreated;
    @Column(name = "state")
    OrderStateEnum state;
    @Column(name = "returnDate")
    Date returnDate;
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

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
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

    public Date getReturnDate() {
        return returnDate;
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
