package mirea.coursework.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/*Контроллер для главной страницы. Перенаправляет на
* главную страницу. Также сохраняет модели для проверки
* авторизации и имеющиеся роли*/

@Controller
public class MainPageController {
    @GetMapping("/")
    public String indexController(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        model.addAttribute("authentication", auth.getName());
        model.addAttribute("authorities", auth.getAuthorities());

        return "index";
    }
}
