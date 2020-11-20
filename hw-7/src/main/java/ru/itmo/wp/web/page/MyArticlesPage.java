package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage {
    private final ArticleService articleService = new ArticleService();
    private final UserService userService = new UserService();

    private void checkAuth(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "To write articles you should Log in");
            throw new RedirectException("/index");
        }
    }

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAllByUserId(
                ((User) request.getSession().getAttribute("user")).getId()));
    }

    private void updateHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        checkAuth(request);

        articleService.validateUpdatingHidden(request.getParameter("articleId"),
                ((User) request.getSession().getAttribute("user")).getId());
        articleService.updateHidden(
                Long.parseLong(request.getParameter("articleId")),
                Boolean.parseBoolean(request.getParameter("hidden")));
    }
}
