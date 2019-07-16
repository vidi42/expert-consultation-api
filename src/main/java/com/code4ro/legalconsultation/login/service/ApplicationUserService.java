package com.code4ro.legalconsultation.login.service;

import com.code4ro.legalconsultation.common.controller.LegalValidationException;
import com.code4ro.legalconsultation.login.model.ApplicationUser;
import com.code4ro.legalconsultation.login.payload.SignUpRequest;
import com.code4ro.legalconsultation.login.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserService(final ApplicationUserRepository applicationUserRepository,
                                  final PasswordEncoder passwordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ApplicationUser save(SignUpRequest signUpRequest) throws LegalValidationException {
        if (applicationUserRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new LegalValidationException("register.Duplicate.username", HttpStatus.CONFLICT);
        }
        if (applicationUserRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new LegalValidationException("register.Duplicate.email", HttpStatus.CONFLICT);
        }
        ApplicationUser applicationUser = new ApplicationUser(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword());
        applicationUser.setPassword(passwordEncoder.encode(applicationUser.getPassword()));
        return applicationUserRepository.save(applicationUser);
    }

}
