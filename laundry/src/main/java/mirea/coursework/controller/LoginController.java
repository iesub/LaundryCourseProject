package mirea.coursework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**Контроллер авторизации. Перенаправляет на страницу авторизации и
* отображает ошибки на странице*/

@Controller
public class LoginController {

    /**Метод возвращает страницу авторизации
     * @return Страница авторизации* */

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    /**Метод возвращает страницу авторизации с сообщением об ошибке
     * @param model Модель, хранит в себе сообщение для отображения об
     * ошибке на странице
     * @return Возвращает страницу авторизации*/

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("Error", true);
        return "login";
    }
}
