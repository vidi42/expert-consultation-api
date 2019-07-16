package com.code4ro.legalconsultation.user.controller;

import com.code4ro.legalconsultation.common.controller.AbstractControllerIntegrationTest;
import com.code4ro.legalconsultation.user.model.User;
import com.code4ro.legalconsultation.user.repository.UserRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIntegrationTest extends AbstractControllerIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser
    public void saveUser() throws Exception {
        final User user = RandomObjectFiller.createAndFill(User.class);
        user.setId(null);

        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value(user.getFirstName()))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isEqualTo(1);
    }

    @Test
    @WithMockUser
    public void saveUsers() throws Exception {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class), RandomObjectFiller.createAndFill(User.class));

        mvc.perform(put("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isEqualTo(2);
    }

    @Test
    @WithMockUser
    public void getUser() throws Exception {
        final User user = userRepository.save(RandomObjectFiller.createAndFill(User.class));

        mvc.perform(get("/api/users/" + user.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void list() throws Exception {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class),
                RandomObjectFiller.createAndFill(User.class),
                RandomObjectFiller.createAndFill(User.class));
        userRepository.saveAll(users);

        mvc.perform(get("/api/users?page=0")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(2))
                .andExpect(status().isOk());

        mvc.perform(get("/api/users?page=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content.size()").value(1))
                .andExpect(status().isOk());

        users.sort(Comparator.comparing(User::getFirstName));
        mvc.perform(get("/api/users?sort=firstName")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].firstName").value(users.get(0).getFirstName()))
                .andExpect(status().isOk());

        users.sort(Comparator.comparing(User::getEmail));
        mvc.perform(get("/api/users?sort=email")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].email").value(users.get(0).getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteUser() throws Exception {
        final User user = userRepository.save(RandomObjectFiller.createAndFill(User.class));
        assertThat(userRepository.count()).isEqualTo(1);

        mvc.perform(delete("/api/users/" + user.getId().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isEqualTo(0);
    }

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
}
