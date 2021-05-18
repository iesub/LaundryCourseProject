package mirea.coursework.service;

import mirea.coursework.entity.Order;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/*Сервис заказов. Обладает стандартными функциями для
* сохранения и получения заказов
* Также выбирает заказы с определенным состоянием*/

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderService(){}

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    public List<Order> getOrderByState(OrderStateEnum orderState) {return orderRepository.findByState(orderState);}

    public Order findOrderById(Long orderId) {
        Optional<Order> userFromDb = orderRepository.findById(orderId);
        return userFromDb.orElse(new Order());
    }

}
