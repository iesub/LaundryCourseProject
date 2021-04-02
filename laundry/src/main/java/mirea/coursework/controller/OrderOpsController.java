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

import java.util.Date;
import java.util.List;

@Controller
public class OrderOpsController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    @GetMapping("/add-order")
    public String addOrder(Model model){
        model.addAttribute("orderForm", new Order());
        return "add-order";
    }

    @PostMapping("/add-order")
    public String addOrder(@ModelAttribute("orderForm") Order order){

        order.setDateCreated(new Date());
        OrderStateEnum ose = OrderStateEnum.AWAIT_TO_ACCEPT;
        order.setState(ose);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.loadUserByUsername(auth.getName());
        order.setUsers(user);

        orderService.insertOrder(order);

        return "redirect:/";
    }
}
