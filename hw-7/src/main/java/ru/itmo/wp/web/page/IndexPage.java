package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Collectors;

/** @noinspection unused*/
public class IndexPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        putMessage(request, view);
        findAll(request, view);
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
        view.put("userIdToLogin", userService.findAll().stream().collect(Collectors.toMap(User::getId, User::getLogin)));
    }

    @Json
    private void findAuthor(HttpServletRequest request, Map<String, Object> view) {
        view.put("author", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }
}
