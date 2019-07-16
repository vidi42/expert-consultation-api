package com.code4ro.legalconsultation.user.controller;

import com.code4ro.legalconsultation.user.model.User;
import com.code4ro.legalconsultation.user.repository.UserRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void extractUsers() throws Exception {
        final String csvContent = "john,doe,john@email.com,42345,district,org";
        final MockMultipartFile firstFile = new MockMultipartFile("csvFile", "users.csv",
                "text/plain", csvContent.getBytes());

        mvc.perform(MockMvcRequestBuilders.multipart("/api/users/extract")
                .file(firstFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("john"))
                .andExpect(jsonPath("$[0].lastName").value("doe"))
                .andExpect(jsonPath("$[0].email").value("john@email.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("42345"))
                .andExpect(jsonPath("$[0].district").value("district"))
                .andExpect(jsonPath("$[0].organisation").value("org"));
    }

    @Test
    @WithMockUser
    public void saveUsers() throws Exception {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class), RandomObjectFiller.createAndFill(User.class));

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isEqualTo(2);
    }
}
