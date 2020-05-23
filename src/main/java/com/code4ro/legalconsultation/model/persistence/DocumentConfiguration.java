package com.code4ro.legalconsultation.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "document_configuration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DocumentConfiguration extends BaseEntity {

    @Column(name = "is_open_for_commenting")
    private Boolean openForCommenting;

    @Column(name = "is_open_for_voting_comments")
    private Boolean openForVotingComments;
}
