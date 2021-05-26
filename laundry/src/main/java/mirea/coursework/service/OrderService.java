package mirea.coursework.service;

import mirea.coursework.entity.Order;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**Сервис заказов. Обладает стандартными функциями для
* сохранения и получения заказов
* Также выбирает заказы с определенным состоянием*/

@Service
public class OrderService {

    /**Поле с JPA репозиторием, отвечающим за связь с таблицей заказов*/

    @Autowired
    private OrderRepository orderRepository;

    /**Базовый пустой конструктор*/

    public OrderService(){}

    /**Конструктор с установкой репозитоия orderRepository
     * @param orderRepository JPA репозиторий, работает с таблицей заказов*/

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    /**Метод отвечает за загрузка нового элемента в базу данных заказов
     * @param order Объект класса Order для вставки в таблицу*/

    public void insertOrder(Order order) {
        orderRepository.save(order);
    }

    /**Метод, который выбирает все заказы из таблицы
     * @return Список заказов*/

    public List<Order> getOrders(){
        return orderRepository.findAll();
    }

    /**Метод, который выбирает заказы из таблицы по их состоянию
     * @param orderState Определяет состояние заказа, по которому происходит выборка
     * @return Список заказов с определенным состоянием*/

    public List<Order> getOrderByState(OrderStateEnum orderState) {return orderRepository.findByState(orderState);}

    /**Метод, который выбирает заказ из таблицы по id
     * @param orderId id заказа для выборки
     * @return Искомый заказ*/

    public Order findOrderById(Long orderId) {
        Optional<Order> userFromDb = orderRepository.findById(orderId);
        return userFromDb.orElse(new Order());
    }

}
