package com.code4ro.legalconsultation.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

@Entity
@Table(name = "articles")
public class Article extends BaseEntity {

    @Column(name = "article_number", unique=true, nullable=false)
    private BigInteger articleNumber;

    @Column(name = "article_title", nullable=false)
    private String articleTitle;

    @Column(name = "article_content", nullable=false)
    private String articleContent;

    public BigInteger getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(BigInteger articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent;
    }
}
