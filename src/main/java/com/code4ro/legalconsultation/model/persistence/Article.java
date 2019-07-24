package com.code4ro.legalconsultation.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigInteger;

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
}
