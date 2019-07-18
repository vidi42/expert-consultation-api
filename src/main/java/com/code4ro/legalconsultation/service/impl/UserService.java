package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.repository.UserRepository;
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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CsvMapper csvMapper = new CsvMapper();

    @Autowired
    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(final User user) {
        return userRepository.save(user);
    }

    public List<User> saveAll(final List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getOne(final String id) {
        return userRepository.findById(UUID.fromString(id)).orElseThrow(EntityNotFoundException::new);
    }

    public Page<User> findAll(final Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteById(final String id) {
        userRepository.deleteById(UUID.fromString(id));
    }

    public List<User> extract(final MultipartFile csvFile) throws LegalValidationException {
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
                }
            });
            users.stream()
                    .filter(user -> !Objects.nonNull(user.getId()))
                    .forEach(userRepository::save);
            return users;
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
