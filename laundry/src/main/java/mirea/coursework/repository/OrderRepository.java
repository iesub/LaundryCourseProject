package mirea.coursework.repository;

import mirea.coursework.entity.Order;
import mirea.coursework.enumiration.OrderStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByState(OrderStateEnum stateNumber);
}
