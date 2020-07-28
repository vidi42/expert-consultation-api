package com.code4ro.legalconsultation.service;

import com.code4ro.legalconsultation.common.security.CurrentUser;
import com.code4ro.legalconsultation.config.security.CurrentUserService;
import com.code4ro.legalconsultation.factory.RandomObjectFiller;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.service.impl.ApplicationUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CurrentUserServiceTest {

    @Mock
    private ApplicationUserService applicationUserService;
    @InjectMocks
    private CurrentUserService currentUserService;

    @Test
    public void getCurrentUser() {
        final ApplicationUser applicationUser = RandomObjectFiller.createAndFill(ApplicationUser.class);
        final SecurityContext securityContext = mock(SecurityContext.class);
        final Authentication authentication = mock(Authentication.class);
        final CurrentUser currentuser = CurrentUser.create(applicationUser);
        when(authentication.getPrincipal()).thenReturn(currentuser);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        currentUserService.getCurrentUser();

        verify(applicationUserService).getByUsernameOrEmail(applicationUser.getUsername());
    }
}
