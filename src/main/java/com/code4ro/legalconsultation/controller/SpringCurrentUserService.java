package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.common.security.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SpringCurrentUserService implements CurrentUserService {

    @Override
    public CurrentUser getCurrentUser() {
        final SecurityContext securityContext = SecurityContextHolder.getContext();
        final Authentication auth = securityContext.getAuthentication();
        //getAuthentication may return null if no auth info is available
        if (auth != null) {
            final Object principal = auth.getPrincipal();
            //getPrincipal returns a string object for anonymous users
            if (principal instanceof CurrentUser) {
                return (CurrentUser) principal;
            }
        }
        return null;
    }
}
