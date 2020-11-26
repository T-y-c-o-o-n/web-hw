package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisableEnableCredentials;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersPage extends Page {
    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("users")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/all")
    public String users() {
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String updateDisable(@Valid @ModelAttribute("disableEnableForm")
                                            DisableEnableCredentials disableEnableForm,
                                BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            try {
                long id = Long.parseLong(disableEnableForm.getIdString());
                boolean disable = "Disable".equals(disableEnableForm.getAction());
                userService.updateDisabled(id, disable);
            } catch (NumberFormatException ignored) {}
        }

        return "redirect:/users/all";
    }
}
