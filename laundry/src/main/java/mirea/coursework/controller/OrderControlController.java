package mirea.coursework.controller;

import mirea.coursework.entity.Order;
import mirea.coursework.entity.User;
import mirea.coursework.enumiration.OrderStateEnum;
import mirea.coursework.service.MailService;
import mirea.coursework.service.OrderService;
import mirea.coursework.service.UserService;
import mirea.coursework.util.IdSaverForManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;

/*Контроллер для страницы с менеджментом*/

@Controller
public class OrderControlController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @Autowired
    private MailService mailService;

    /*Get запрос передает в модели списки заказов по их статусу.*/

    @GetMapping("/management/order-control")
    public String addOrder(Model model){

        List<Order> awaitToAcceptOrders = orderService.getOrderByState(OrderStateEnum.AWAIT_TO_ACCEPT);
        awaitToAcceptOrders.sort(Comparator.comparing(Order::getDateCreated).reversed());
        List<Order> acceptedReadyToTakeOrders = orderService.getOrderByState(OrderStateEnum.ACCEPTED_READY_TO_TAKE);
        List<Order> takenOrders = orderService.getOrderByState(OrderStateEnum.TAKEN);
        List<Order> processingOrders = orderService.getOrderByState(OrderStateEnum.PROCESSING);
        List<Order> readyToReturnOrders = orderService.getOrderByState(OrderStateEnum.READY_TO_RETURN);
        List<Order> returnedOrders = orderService.getOrderByState(OrderStateEnum.RETURNED);
        returnedOrders.sort(Comparator.comparing(Order::getDateCreated).reversed());

        model.addAttribute("ordersState0", awaitToAcceptOrders);
        model.addAttribute("ordersState1", acceptedReadyToTakeOrders);
        model.addAttribute("ordersState2", takenOrders);
        model.addAttribute("ordersState3", processingOrders);
        model.addAttribute("ordersState4", readyToReturnOrders);
        model.addAttribute("ordersState5", returnedOrders);

        model.addAttribute("orderId", new IdSaverForManagement());

        return "order-control";
    }

    /*Post запрос - смена состояния заказа. С сайта получаем id заказа и меняем его состояние на следующее*/
    @PostMapping("/management/order-control")
    public String updateState(@ModelAttribute("orderId") IdSaverForManagement id){

        Long idL = Long.parseLong(id.getId());
        Order order = orderService.findOrderById(idL);
        order.setState(OrderStateEnum.values()[order.getState().ordinal()+1]);
        orderService.insertOrder(order);

        User owner = order.getUsers();
        mailService.updateMail(owner.getUsername());

        return "redirect:order-control";
    }

}
