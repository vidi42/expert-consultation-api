package com.code4ro.legalconsultation.invitation.model.persistence;


import com.code4ro.legalconsultation.core.model.persistence.BaseEntity;
import com.code4ro.legalconsultation.user.model.persistence.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invitations")
@Getter
@Setter
public class Invitation extends BaseEntity {
    @NotNull
    @OneToOne(fetch = FetchType.EAGER)
    private User user;
    @NotNull
    private String code;
    @NotNull
    @Enumerated(EnumType.STRING)
    private InvitationStatus status = InvitationStatus.PENDING;
}
