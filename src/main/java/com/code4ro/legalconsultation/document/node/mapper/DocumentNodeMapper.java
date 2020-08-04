package com.code4ro.legalconsultation.document.node.mapper;

import com.code4ro.legalconsultation.document.node.model.dto.DocumentNodeCreateDto;
import com.code4ro.legalconsultation.document.node.model.dto.DocumentNodeDto;
import com.code4ro.legalconsultation.document.node.model.dto.DocumentNodeSimpleDto;
import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {NumberOfCommentsMapper.class})
public interface DocumentNodeMapper {

    DocumentNodeDto map(DocumentNode documentNode);

    DocumentNode map(DocumentNodeDto documentNodeDto);

    DocumentNode map(DocumentNodeCreateDto documentNodeCreateDto);

    DocumentNodeSimpleDto mapToSimpleDto(DocumentNode documentNode);
}
