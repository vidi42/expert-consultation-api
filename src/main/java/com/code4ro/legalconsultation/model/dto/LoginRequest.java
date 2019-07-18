package com.code4ro.legalconsultation.model.dto;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "login.UsernameOrEmail.not.empty")
    private String usernameOrEmail;

    @NotBlank(message = "login.Password.not.empty")
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
