package com.code4ro.legalconsultation.invitation.service;

import com.code4ro.legalconsultation.authentication.model.dto.SignUpRequest;
import com.code4ro.legalconsultation.invitation.model.persistence.Invitation;
import com.code4ro.legalconsultation.user.model.persistence.User;

public interface InvitationService {
    Invitation getInvitation(String code);

    Invitation create(User user);

    boolean isValid(SignUpRequest signUpRequest);

    void markAsUsed(String code);
}
