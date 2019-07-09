package com.code4ro.legalconsultation.login.service;

import com.code4ro.legalconsultation.common.controller.LegalValidationException;
import com.code4ro.legalconsultation.login.model.ApplicationUser;
import com.code4ro.legalconsultation.login.payload.SignUpRequest;
import com.code4ro.legalconsultation.login.repository.UserRepository;
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
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    private final SignUpRequest signUpRequest = RandomObjectFiller.createAndFill(SignUpRequest.class);

    @Test
    public void save() {
        userService.save(signUpRequest);

        verify(passwordEncoder).encode(signUpRequest.getPassword());
        verify(userRepository).save(any(ApplicationUser.class));
    }

    @Test(expected = LegalValidationException.class)
    public void saveDuplicateUser() {
        when(userRepository.existsByUsername(signUpRequest.getUsername())).thenReturn(true);

        userService.save(signUpRequest);
    }

    @Test(expected = LegalValidationException.class)
    public void saveDuplicateEmail() {
        when(userRepository.existsByEmail(signUpRequest.getEmail())).thenReturn(true);

        userService.save(signUpRequest);
    }
}