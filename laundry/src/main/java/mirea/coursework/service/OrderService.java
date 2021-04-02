package mirea.coursework.service;

import mirea.coursework.entity.Order;
import mirea.coursework.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void insertOrder(Order order) {
        orderRepository.save(order);
    }
}