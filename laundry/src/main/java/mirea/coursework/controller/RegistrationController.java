package mirea.coursework.controller;

import mirea.coursework.entity.User;
import mirea.coursework.service.MailService;
import mirea.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Контроллер регистрации*/

@Controller
public class RegistrationController {

    /**Сервис пользователя, позволяющий классу работать с пользователями*/

    @Autowired
    private UserService userService;

    /**Сервис писем, позволяет классу отправлять письмо*/

    @Autowired
    private MailService mailService;

    /**Метод возвращает страницу регистрации
     * @param model Модель, которая хранит информацию о новом пользователе
     * @return Страница регистрации*/

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    /**Получаем информацию для регистрации, проводим валидацию введенных данных
    * Если ошибок нет - добавляем нового пользователя и отправляем письмо с
    * ссылкой для активации аккаунта
     * @param userForm Данные о пользователе
     * @param bindingResult Хранит информацию об ошибках присвоения значений к атрибутам
     * @param model Хранит в себе информацию об ошибках
     * @return Страница логина или регистрации*/

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        String emailPattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(userForm.getUsername());

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(!matcher.matches()){
            model.addAttribute("Error", "Неверный формат электронной почты");
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("Error", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.registerUser(userForm)){
            model.addAttribute("Error", "Пользователь с такой электронной почтой уже существует");
            return "registration";
        }

        User userFromBD = userService.loadUserByUsername(userForm.getUsername());
        mailService.sendActivationURL(userFromBD.getUsername(), userFromBD.getId());
        return "registration-mail-notification";
    }

    /**
     * Метод активации аккаунта пользователя
     * @param userId - id пользователя, аккаунт которого требуется активировать
     * @return Страница авторизации
     */
    @GetMapping("registration/activate/{userId}")
    public String activation(@PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        user.setActive(true);
        userService.updateRow(user);
        return "redirect:/login";
    }

}

