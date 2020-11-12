package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;

import java.util.List;

public interface ArticleRepository {
    public Article find(long id);
    List<Article> findAll();
    List<Article> findByUserId();
    void save(Article article);
}
