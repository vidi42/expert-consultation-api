package com.code4ro.legalconsultation.security.service;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;

public interface CurrentUserService {
    ApplicationUser getCurrentUser();
}
