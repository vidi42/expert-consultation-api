package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User save(@Valid @RequestBody final User user) {
        return userService.saveAndSendRegistrationMail(user);
    }

    @PutMapping
    public List<User> saveAll(@Valid @RequestBody final List<User> users) {
        return userService.saveAndSendRegistrationMail(users);
    }


    @GetMapping(value = "/{id}")
    public User getOne(@PathVariable final String id) {
        return userService.getOne(id);
    }

    @GetMapping
    public Page<User> findAll(final Pageable pageable) {
        return userService.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable final String id) {
        userService.deleteById(id);
    }

    @PostMapping(value = "/extract", consumes = "multipart/form-data")
    public List<User> extractFromCsv(@RequestParam("csvFile") final MultipartFile file) {
        return userService.extract(file);
    }
}
