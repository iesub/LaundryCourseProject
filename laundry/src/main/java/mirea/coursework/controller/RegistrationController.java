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

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        String emailPattern =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                        "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(userForm.getMail());

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(!matcher.matches()){
            model.addAttribute("mailError", "Неверный формат электронной почты");
            return "registration";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.registerUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "registration";
        }

        User userFromBD = userService.loadUserByUsername(userForm.getUsername());
        mailService.sendActivationURL(userFromBD.getMail(), userFromBD.getId());
        return "login";
    }

    @GetMapping("registration/activate/{userId}")
    public String activation(@PathVariable("userId") Long userId) {
        User user = userService.findUserById(userId);
        user.setActive(true);
        userService.updateRow(user);
        return "/login";
    }

}
