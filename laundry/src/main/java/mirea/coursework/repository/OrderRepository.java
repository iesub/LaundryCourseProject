package mirea.coursework.repository;

import mirea.coursework.entity.Order;
import mirea.coursework.enumiration.OrderStateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
Репозиторий для заказов.
*/

public interface OrderRepository extends JpaRepository<Order, Long> {
    /**Метод осуществляет выборку по полю state в таблице
     * @param stateNumber Ключ для поиска
     * @retutn Искомый заказ*/
    List<Order> findByState(OrderStateEnum stateNumber);
}
