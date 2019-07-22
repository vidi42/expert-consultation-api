package com.code4ro.legalconsultation.service.impl;


import com.code4ro.legalconsultation.common.security.UserPrincipal;
import com.code4ro.legalconsultation.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.model.persistence.UserRole;
import com.code4ro.legalconsultation.repository.ApplicationUserRepository;
import com.code4ro.legalconsultation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private ApplicationUserRepository applicationUserRepository;
    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(final ApplicationUserRepository applicationUserRepository,
                                    final UserRepository userRepository) {
        this.applicationUserRepository = applicationUserRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // let people login with either username or email
        ApplicationUser applicationUser = applicationUserRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("ApplicationUser not found with username or email : " + usernameOrEmail)
                );

        // TODO: remove this call and get the role directly via the 1-1 mapping between ApplicationUser and User
        final User user = userRepository.findByEmail(applicationUser.getEmail());
        final UserRole role = user != null ? user.getRole() : UserRole.CONTRIBUTOR;
        return UserPrincipal.create(applicationUser, role);
    }

    // used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("ApplicationUser not found with id : " + id)
        );

        // TODO: remove this call and get the role directly via the 1-1 mapping between ApplicationUser and User
        final User user = userRepository.findByEmail(applicationUser.getEmail());
        final UserRole role = user != null ? user.getRole() : UserRole.CONTRIBUTOR;
        return UserPrincipal.create(applicationUser, role);
    }
}
