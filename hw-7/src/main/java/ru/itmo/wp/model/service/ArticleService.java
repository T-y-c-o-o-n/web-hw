package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.UserRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;
import ru.itmo.wp.model.repository.impl.UserRepositoryImpl;

import java.text.NumberFormat;
import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final UserRepository userRepository = new UserRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (userRepository.find(article.getUserId()) == null) {
            throw new ValidationException("You are hacker! You are not registered user!");
        }

        if (Strings.isNullOrEmpty(article.getTitle()) || article.getTitle().matches("\\s+")) {
            throw new ValidationException("Article title is empty");
        }
        if (article.getTitle().length() >= 100) {
            throw new ValidationException("Title length is too big. Should be less than 100 characters");
        }

        if (Strings.isNullOrEmpty(article.getText()) || article.getText().matches("\\s+")) {
            throw new ValidationException("Article text is empty");
        }
        if (article.getText().length() >= 1000) {
            throw new ValidationException("Text length is too big. Should be less than 1000 characters");
        }
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUserId(userId);
    }

    public void validateUpdatingHidden(String stringArticleId, long userId) throws ValidationException {
        boolean incorrectId = false;
        try {
            long id = Long.parseLong(stringArticleId);
            Article article = articleRepository.find(id);
            if (article == null || article.getUserId() != userId) {
                incorrectId = true;
            }
        } catch (NumberFormatException e) {
            incorrectId = true;
        }
        if (incorrectId) {
            throw new ValidationException("You are hacker! Incorrect article");
        }
    }

    public void updateHidden(long id, boolean hidden) {
        articleRepository.updateHidden(id, hidden);
    }
}
