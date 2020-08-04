package com.code4ro.legalconsultation.security.mapper;

import com.code4ro.legalconsultation.authentication.model.persistence.ApplicationUser;
import com.code4ro.legalconsultation.security.model.dto.CurrentUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentUserMapper {
    CurrentUserDto map(ApplicationUser user);
}
