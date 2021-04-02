package mirea.coursework.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id = Long.valueOf(0);
    @Column(name = "clothesFor")
    String ;
    @Column(name = "zipCode")
    String zipCode;
    @OneToOne
    @Fetch(FetchMode.JOIN)
    private User owner;
}
