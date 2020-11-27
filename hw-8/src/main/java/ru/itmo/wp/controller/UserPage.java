package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{idString}")
    String userInfo(Model model, @PathVariable String idString) {
        try {
            long id = Long.parseLong(idString);
            User userInfo = userService.findById(id);
            if (userInfo != null) {
                model.addAttribute("userInfo", userInfo);
            }
        } catch (NumberFormatException ignored) {
        }
        return "UserPage";
    }
}
