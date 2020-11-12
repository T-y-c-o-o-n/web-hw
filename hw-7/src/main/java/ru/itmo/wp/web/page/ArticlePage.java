package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage {
    private final ArticleService articleService = new ArticleService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "To write articles you should Log in");
            throw new RedirectException("/index");
        }
    }

    private void write(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        action(request, view);

        long userId = ((User) request.getSession().getAttribute("user")).getId();
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        Article article = new Article(userId, title, text);
        articleService.validateArticle(article);
        articleService.addArticle(article);

        throw new RedirectException("/index");
    }
}
