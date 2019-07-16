package com.code4ro.legalconsultation.user.controller;

import com.code4ro.legalconsultation.user.model.User;
import com.code4ro.legalconsultation.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<User> saveAll(@Valid @RequestBody final List<User> users) {
        return userService.save(users);
    }

    @PostMapping(value = "/extract", consumes = "multipart/form-data")
    public List<User> extractFromCsv(@RequestParam("csvFile") final MultipartFile file) {
        return userService.extract(file);
    }
}
