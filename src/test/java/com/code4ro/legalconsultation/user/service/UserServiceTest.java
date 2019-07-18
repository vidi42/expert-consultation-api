package com.code4ro.legalconsultation.user.service;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.impl.UserService;
import com.code4ro.legalconsultation.util.RandomObjectFiller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void saveUser() {
        final User user = RandomObjectFiller.createAndFill(User.class);

        userService.save(user);

        verify(userRepository).save(user);
    }

    @Test
    public void saveUsers() {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class), RandomObjectFiller.createAndFill(User.class));

        userService.saveAll(users);

        verify(userRepository).saveAll(users);
    }

    @Test
    public void getUser() {
        final String id = UUID.randomUUID().toString();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(new User()));
        userService.getOne(id);

        verify(userRepository).findById(UUID.fromString(id));
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserNotFound() {
        final String id = UUID.randomUUID().toString();
        userService.getOne(id);
    }

    @Test
    public void findAll() {
        final Pageable pageable = mock(Pageable.class);

        userService.findAll(pageable);

        verify(userRepository).findAll(pageable);
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
