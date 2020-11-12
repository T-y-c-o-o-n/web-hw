package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void validateArticle(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("Article title is empty");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("Article text is empty");
        }

    }

    public void addArticle(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public List<Article> findByUserId() {
        return articleRepository.findByUserId();
    }
}
