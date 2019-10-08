package com.code4ro.legalconsultation.model.persistence;

import java.math.BigInteger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "articles")
@Getter
@Setter
public class Article extends BaseEntity {

    @Column(name = "article_number", unique = true, nullable = false)
    private BigInteger articleNumber;

    @Column(name = "article_title", nullable = false)
    private String articleTitle;

    @Column(name = "article_content", nullable = false)
    private String articleContent;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "chapter_id")
    private Chapter articleChapter;
  
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        if (!super.equals(o)) return false;
        Article article = (Article) o;
        return super.equals(article) && Objects.equals(articleNumber, article.articleNumber) &&
                Objects.equals(articleTitle, article.articleTitle) &&
                Objects.equals(articleContent, article.articleContent);
    }
}
