package com.code4ro.legalconsultation.model.dto;

import com.code4ro.legalconsultation.model.persistence.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto extends BaseEntityDto {
    @Size(max = 40)
    private String firstName;
    @Size(max = 40)
    private String lastName;
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;
    @Size(max = 40)
    private String phoneNumber;
    @Size(max = 40)
    private String district;
    @Size(max = 100)
    private String organisation;
    @NotNull
    private UserRole role;
}
