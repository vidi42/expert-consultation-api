package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.model.dto.BaseEntityDto;
import com.code4ro.legalconsultation.model.dto.UserDto;
import com.code4ro.legalconsultation.model.persistence.BaseEntity;
import com.code4ro.legalconsultation.model.persistence.User;
import com.code4ro.legalconsultation.service.MapperService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapperServiceImpl implements MapperService {
    private final ModelMapper modelMapper;

    public MapperServiceImpl() {
        this.modelMapper = new ModelMapper();

        addCustomMappings();
        addCustomTypeMaps();
    }

    private void addCustomMappings() {
        modelMapper.createTypeMap(BaseEntity.class, BaseEntityDto.class)
                .addMapping(BaseEntity::getId, BaseEntityDto::setId);
        modelMapper.createTypeMap(BaseEntityDto.class, BaseEntity.class)
                .addMapping(BaseEntityDto::getId, BaseEntity::setId);
    }

    private void addCustomTypeMaps() {
        modelMapper.createTypeMap(User.class, UserDto.class)
                .includeBase(BaseEntity.class, BaseEntityDto.class);
        modelMapper.createTypeMap(UserDto.class, User.class)
                .includeBase(BaseEntityDto.class, BaseEntity.class);
    }

    @Override
    public <T> T map(Object source, Class<T> targetType) {
        return source != null ? modelMapper.map(source, targetType) : null;
    }

    @Override
    public <T> List<T> mapList(List<?> sourceList, Class<? extends T> targetClass) {
        if (sourceList == null) {
            return Collections.emptyList();
        }

        return sourceList.stream()
                .map(listElement -> modelMapper.map(listElement, targetClass))
                .collect(Collectors.toList());
    }

    @Override
    public <T> Page<T> mapPage(Page<?> sourcePage, Class<? extends T> targetClass) {
        if (sourcePage == null) {
            return Page.empty();
        }

        return sourcePage.map(pageElement -> modelMapper.map(pageElement, targetClass));
    }
}
