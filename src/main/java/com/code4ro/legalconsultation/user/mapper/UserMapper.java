package com.code4ro.legalconsultation.user.mapper;

import com.code4ro.legalconsultation.user.model.persistence.User;
import com.code4ro.legalconsultation.user.model.dto.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User map(UserDto userDto);

    UserDto map(User User);

    List<UserDto> map(List<User> users);
}
