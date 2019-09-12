package com.code4ro.legalconsultation.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters")
@Getter
@Setter
public class Chapter extends BaseEntity {

    @Column(name = "chapter_number", unique = true, nullable = false)
    private BigInteger chapterNumber;

    @Column(name = "chapter_title", nullable = false)
    private String chapterTitle;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "articleChapter")
    private List<Article> articles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id")
    private DocumentBreakdown chapterDocument;
}
