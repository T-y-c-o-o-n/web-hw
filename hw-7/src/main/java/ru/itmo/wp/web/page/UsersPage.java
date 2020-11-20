package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage {
    private final UserService userService = new UserService();

    private void updateSessionUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", userService.find(user.getId()));
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        updateSessionUser(request, view);
    }

    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void checkAuth(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "To change admins you should log in");
            throw new RedirectException("/index");
        }
    }

    private void updateAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = (User) request.getSession().getAttribute("user");

        checkAuth(request);
        updateSessionUser(request, view);

        userService.validateUpdatingAdmin(user.getId(), request.getParameter("targetUserId"));
        userService.updateAdmin(Long.parseLong(request.getParameter("targetUserId")),
                Boolean.parseBoolean(request.getParameter("admin")));

        updateSessionUser(request, view);
    }
}
