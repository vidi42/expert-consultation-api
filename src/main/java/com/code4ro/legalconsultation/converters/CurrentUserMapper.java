package com.code4ro.legalconsultation.converters;

import com.code4ro.legalconsultation.common.security.CurrentUser;
import com.code4ro.legalconsultation.controller.CurrentUserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CurrentUserMapper {
    CurrentUserDto map(CurrentUser user);
}
