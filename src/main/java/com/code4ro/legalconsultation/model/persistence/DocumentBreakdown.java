package com.code4ro.legalconsultation.model.persistence;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "document_chapters")
@Getter
@Setter
public class DocumentBreakdown extends BaseEntity {

    @OneToMany
    @JoinColumn(name = "chapter_id", referencedColumnName = "id")
    private List<Chapter> chapters = new ArrayList<>();
}
