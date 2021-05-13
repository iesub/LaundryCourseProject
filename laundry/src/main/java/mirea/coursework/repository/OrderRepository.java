package mirea.coursework.repository;

import mirea.coursework.entity.Order;
import mirea.coursework.enumiration.OrderStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
Репозиторий для заказов. Имеет дополнительный метод для
возврата списка заказов по состоянию
*/

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByState(OrderStateEnum stateNumber);
}
