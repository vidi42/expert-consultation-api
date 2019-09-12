package com.code4ro.legalconsultation.model.persistence;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "document_breakdown")
@Getter
@Setter
public class DocumentBreakdown extends BaseEntity {

    @Column(name = "title", nullable=false)
    private String title;

    @Column(name = "intro", length = 2000)
    private String intro;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapterDocument")
    private List<Chapter> chapters = new ArrayList<>();
}