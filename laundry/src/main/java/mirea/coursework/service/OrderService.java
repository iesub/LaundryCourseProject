package mirea.coursework.service;

import mirea.coursework.entity.Order;
import mirea.coursework.entity.User;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

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
