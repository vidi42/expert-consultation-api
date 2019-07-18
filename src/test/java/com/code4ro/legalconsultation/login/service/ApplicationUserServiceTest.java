package com.code4ro.legalconsultation.login.service;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.dto.SignUpRequest;
import com.code4ro.legalconsultation.repository.ApplicationUserRepository;
import com.code4ro.legalconsultation.service.impl.ApplicationUserService;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationUserServiceTest {
    @Mock
    private ApplicationUserRepository applicationUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private ApplicationUserService applicationUserService;

    private final SignUpRequest signUpRequest = RandomObjectFiller.createAndFill(SignUpRequest.class);

    @Test
    public void save() {
        applicationUserService.save(signUpRequest);

        verify(passwordEncoder).encode(signUpRequest.getPassword());
        verify(applicationUserRepository).save(any(ApplicationUser.class));
    }

    @Test(expected = LegalValidationException.class)
    public void saveDuplicateUser() {
        when(applicationUserRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        applicationUserService.save(signUpRequest);
    }

    @Test(expected = LegalValidationException.class)
    public void saveDuplicateEmail() {
        when(applicationUserRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        applicationUserService.save(signUpRequest);
    }
}
