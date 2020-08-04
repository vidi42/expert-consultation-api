package com.code4ro.legalconsultation.authentication.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "login.UsernameOrEmail.not.empty")
    private String usernameOrEmail;

    @NotBlank(message = "login.Password.not.empty")
    private String password;
}
