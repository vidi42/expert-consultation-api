package com.code4ro.legalconsultation.login.controller;


import com.code4ro.legalconsultation.login.payload.LoginRequest;
import com.code4ro.legalconsultation.login.payload.SignUpRequest;
import com.code4ro.legalconsultation.login.repository.UserRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void signUp() throws Exception {
        final SignUpRequest signUpRequest = RandomObjectFiller.createAndFill(SignUpRequest.class);
        String json = objectMapper.writeValueAsString(signUpRequest);

        // register successfuly
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        assertThat(userRepository.existsByUsername(signUpRequest.getUsername())).isNotNull();

        // fail to register with same username
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nErrors[0].i18nErrorKey")
                        .value("validation.user.Duplicate.username"))
                .andExpect(status().isConflict());

        // fail to register with same email
        signUpRequest.setUsername("userName2");
        json = objectMapper.writeValueAsString(signUpRequest);
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nErrors[0].i18nErrorKey")
                        .value("validation.user.Duplicate.email"))
                .andExpect(status().isConflict());
    }

    @Test
    public void login() throws Exception {
        // register user
        final SignUpRequest signUpRequest = RandomObjectFiller.createAndFill(SignUpRequest.class);
        String json = objectMapper.writeValueAsString(signUpRequest);
        mvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsernameOrEmail(signUpRequest.getUsername());
        loginRequest.setPassword(signUpRequest.getPassword());
        json = objectMapper.writeValueAsString(loginRequest);

        // login successfully with existing user
        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // fail to login
        loginRequest.setUsernameOrEmail("differentUsername");
        json = objectMapper.writeValueAsString(loginRequest);
        mvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.i18nErrors[0].i18nErrorKey")
                        .value("login.user.Bad.credentials"))
                .andExpect(status().isUnauthorized());
    }
}
