package com.code4ro.legalconsultation.repository;

import com.code4ro.legalconsultation.model.persistence.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
}
