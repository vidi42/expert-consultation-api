package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.dto.UserDto;
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
    public UserDto save(@RequestBody @Valid final UserDto userDto) {
        return userService.saveAndSendRegistrationMail(userDto);
    }

    @PutMapping
    public List<UserDto> saveAll(@Valid @RequestBody final List<UserDto> userDtos) {
        return userService.saveAndSendRegistrationMail(userDtos);
    }

    @GetMapping(value = "/{id}")
    public UserDto getOne(@PathVariable final String id) {
        return userService.getOne(id);
    }

    @GetMapping
    public Page<UserDto> findAll(final Pageable pageable) {
        return userService.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable final String id) {
        userService.deleteById(id);
    }

    @PostMapping(value = "/extract", consumes = "multipart/form-data")
    public List<UserDto> extractFromCsv(@RequestParam("csvFile") final MultipartFile file) {
        return userService.extract(file);
    }
}
