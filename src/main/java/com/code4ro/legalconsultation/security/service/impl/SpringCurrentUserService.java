package com.code4ro.legalconsultation.security.service.impl;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.authentication.service.ApplicationUserService;
import com.code4ro.legalconsultation.security.service.CurrentUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SpringCurrentUserService implements CurrentUserService {
    private final ApplicationUserService applicationUserService;

    @Autowired
    public SpringCurrentUserService(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public ApplicationUser getCurrentUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication auth = securityContext.getAuthentication();
        if (auth == null) {
            return null;
        }
        final Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            return applicationUserService.getByUsernameOrEmail(((UserDetails) principal).getUsername());
        }
        return null;
    }
}
