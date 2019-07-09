package com.code4ro.legalconsultation.login.security;


import com.code4ro.legalconsultation.login.model.ApplicationUser;
import com.code4ro.legalconsultation.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // let people login with either username or email
        ApplicationUser applicationUser = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("ApplicationUser not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(applicationUser);
    }

    // used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(UUID id) throws UsernameNotFoundException {
        ApplicationUser applicationUser = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("ApplicationUser not found with id : " + id)
        );

        return UserPrincipal.create(applicationUser);
    }
}
