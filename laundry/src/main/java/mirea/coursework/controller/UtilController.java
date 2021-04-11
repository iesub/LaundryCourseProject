package mirea.coursework.controller;

import mirea.coursework.entity.Role;
import mirea.coursework.entity.User;
import mirea.coursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class UtilController {

    @Autowired
    UserService userService;

    @GetMapping("/hellYea")
    public @ResponseBody
    Set<Role> someThing(Model model) {
        model.addAttribute("userForm", new User());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();

        User user = userService.loadUserByUsername(name);

        return user.getRoles();
    }
}
