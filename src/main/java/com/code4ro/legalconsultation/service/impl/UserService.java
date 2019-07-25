package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.model.dto.UserDto;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.model.persistence.UserRole;
import com.code4ro.legalconsultation.repository.UserRepository;
import com.code4ro.legalconsultation.service.MapperService;
import com.code4ro.legalconsultation.service.api.MailApi;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CsvMapper csvMapper = new CsvMapper();
    private final MailApi mailApi;
    private final MapperService mapperService;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final MailApi mailApi,
                       final MapperService mapperService) {
        this.userRepository = userRepository;
        this.mailApi = mailApi;
        this.mapperService = mapperService;
    }

    public User saveEntity(final User user) {
        return userRepository.save(user);
    }

    public UserDto saveAndSendRegistrationMail(final UserDto userDto) throws LegalValidationException {
        final User user = mapperService.map(userDto, User.class);
        final User savedUser = userRepository.save(user);
        if (user.isNew()) {
            mailApi.sendRegisterMail(Collections.singletonList(user));
        }
        return mapperService.map(savedUser, UserDto.class);
    }

    public List<UserDto> saveAndSendRegistrationMail(final List<UserDto> userDtos) throws LegalValidationException {
        final List<User> users = mapperService.mapList(userDtos, User.class);
        final List<User> newUsers = users.stream()
                .filter(User::isNew)
                .collect(Collectors.toList());
        final List<User> savedUsers = userRepository.saveAll(users);
        if (!newUsers.isEmpty()) {
            mailApi.sendRegisterMail(newUsers);
        }

        return mapperService.mapList(savedUsers, UserDto.class);
    }

    public UserDto getOne(final String id) {
        final User user = userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
        return mapperService.map(user, UserDto.class);
    }

    public Page<UserDto> findAll(final Pageable pageable) {
        final Page<User> userPage = userRepository.findAll(pageable);
        return mapperService.mapPage(userPage, UserDto.class);
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    public void deleteById(final String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    public List<UserDto> extract(final MultipartFile csvFile) throws LegalValidationException {
        try {
            final List<User> users = read(csvFile.getInputStream());
            final List<String> userEmails = users.stream()
                    .map(User::getEmail)
                    .collect(Collectors.toList());
            final Map<String, User> alreadySaved = userRepository.findAllByEmailIn(userEmails).stream()
                    .collect(Collectors.toMap(User::getEmail, user -> user));

            users.forEach(user -> {
                if (alreadySaved.containsKey(user.getEmail())) {
                    user.setId(alreadySaved.get(user.getEmail()).getId());
                    user.setRole(alreadySaved.get(user.getEmail()).getRole());
                } else {
                    user.setRole(UserRole.CONTRIBUTOR);
                }
            });

            return mapperService.mapList(users, UserDto.class);
        } catch (Exception e) {
            LOG.error("Exception while parsing the csv file", e);
            throw new LegalValidationException("user.Extract.csv.failed", HttpStatus.BAD_REQUEST);
        }
    }

    private List<User> read(InputStream stream) throws IOException {
        final CsvSchema schema = CsvSchema.builder()
                .addColumn("firstName")
                .addColumn("lastName")
                .addColumn("email")
                .addColumn("phoneNumber")
                .addColumn("district")
                .addColumn("organisation")
                .build();
        final ObjectReader reader = csvMapper.readerFor(User.class).with(schema);
        return reader.<User>readValues(stream).readAll();
    }
}
