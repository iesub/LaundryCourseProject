package mirea.coursework.controller;

import mirea.coursework.entity.Order;
import mirea.coursework.entity.User;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.service.OrderService;
import mirea.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static mirea.coursework.enumiration.OrderStateEnum.AWAIT_TO_ACCEPT;

/** Контроллер, отвечающий за страницу
* списка заказов и страницу создания заказов */

@Controller
public class OrderOpsController {

    /**Сервис заказов, позволяющий классу работат с заказами*/

    @Autowired
    OrderService orderService;

    /**Сервис пользователя, позволяющий классу работать с пользователями*/

    @Autowired
    UserService userService;

    /**Метод открывает страницу создания заказа
     * @param model Модель, что хранит информацию о заказе
     * @return Страница создания заказов*/

    @GetMapping("/add-order")
    public String addOrder(Model model){
        model.addAttribute("orderForm", new Order());
        return "add-order";
    }

    /** Создание заказа. Получаем информацию о заказе с сайта.
    * Устанавливаем заказу начальное состояние и присваиваем id владельца
     * @param order Заказ,который создал пользователь
     * @return Возврат на главную страницу*/

    @PostMapping("/add-order")
    public String addOrder(@ModelAttribute("orderForm") Order order){

        order.setDateCreated(new Date());
        OrderStateEnum ose = AWAIT_TO_ACCEPT;
        order.setState(ose);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(auth.getName());
        order.setUsers(user);

        orderService.insertOrder(order);

        return "redirect:/";
    }

    /**Выводим все заказы пользователя. Сначала новые заказы.
     * @param model Модель, в которую записывается список заказов
     * @return Страница заказов*/

    @GetMapping("/show-orders")
    public String showOrders(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(auth.getName());

        List<Order> listOrders = user.getOrders();
        listOrders.sort(Comparator.comparing(Order::getDateCreated).reversed());

        model.addAttribute("orderList", listOrders);
        return "show-orders";
    }
}
