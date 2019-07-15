package com.code4ro.legalconsultation.user.service;

import com.code4ro.legalconsultation.common.controller.LegalValidationException;
import com.code4ro.legalconsultation.user.model.User;
import com.code4ro.legalconsultation.user.repository.UserRepository;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void saveUsers() {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class), RandomObjectFiller.createAndFill(User.class));

        userService.save(users);

        verify(userRepository).saveAll(users);
    }

    @Test
    public void extractUsersFromCsv() throws IOException {
        final User user = RandomObjectFiller.createAndFill(User.class);
        user.setEmail("john@email.com");

        final MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(
                new ByteArrayInputStream("john,doe,john@email.com,42345,district,org".getBytes()));
        when(userRepository.findAllByEmailIn(Collections.singletonList("john@email.com")))
                .thenReturn(Collections.singletonList(user));

        final List<User> results = userService.extract(file);

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getId()).isEqualTo(user.getId());
    }

    @Test(expected = LegalValidationException.class)
    public void extractUsersInvalidFile() {
        userService.extract(null);
    }
}
