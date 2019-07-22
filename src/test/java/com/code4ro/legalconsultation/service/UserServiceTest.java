package com.code4ro.legalconsultation.service;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.model.persistence.UserRole;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.impl.MailService;
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
    @Mock
    private MailService mailService;

    @InjectMocks
    private UserService userService;

    @Test
    public void saveUserAndSendRegistrationMail() {
        final User user = RandomObjectFiller.createAndFill(User.class);

        userService.saveAndSendRegistrationMail(user);

        verify(mailService).sendRegisterMail(Collections.singletonList(user));
        verify(userRepository).save(user);
    }

    @Test
    public void saveUsersAndSendRegistrationMail() {
        final List<User> users = Arrays.asList(
                RandomObjectFiller.createAndFill(User.class), RandomObjectFiller.createAndFill(User.class));

        userService.saveAndSendRegistrationMail(users);

        verify(mailService).sendRegisterMail(users);
        verify(userRepository).saveAll(users);
    }

    @Test
    public void getUser() {
        final String id = UUID.randomUUID().toString();
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(new User
                ("email@email.com", UserRole.CONTRIBUTOR)));
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
    public void extractExistingUsersFromCsv() throws IOException {
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
        assertThat(results.get(0).getRole()).isEqualTo(user.getRole());
    }

    @Test
    public void extractNewUsersFromCsv() throws IOException {
        final User user = RandomObjectFiller.createAndFill(User.class);
        user.setEmail("john@email.com");
        user.setId(null);

        final MultipartFile file = mock(MultipartFile.class);
        when(file.getInputStream()).thenReturn(
                new ByteArrayInputStream("john,doe,john@email.com,42345,district,org".getBytes()));

        final List<User> results = userService.extract(file);

        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getId()).isNull();
        assertThat(results.get(0).getRole()).isEqualTo(UserRole.CONTRIBUTOR);
    }

    @Test(expected = LegalValidationException.class)
    public void extractUsersInvalidFile() {
        userService.extract(null);
    }
}
