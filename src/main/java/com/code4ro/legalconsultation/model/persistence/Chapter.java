package com.code4ro.legalconsultation.model.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Chapter extends BaseEntity {

    @Column(name = "chapter_number",unique=true, nullable=false)
    private BigInteger chapterNumber;

    @Column(name = "chapter_title", nullable=false)
    private String chapterTitle;

    @OneToMany
    private List<Article> articles = new ArrayList<>();

    public BigInteger getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(BigInteger chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterTitle() {
        return chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
