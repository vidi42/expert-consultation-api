package com.code4ro.legalconsultation.document.metadata.mapper;

import com.code4ro.legalconsultation.document.metadata.model.dto.DocumentMetadataDto;
import com.code4ro.legalconsultation.document.metadata.model.dto.DocumentViewDto;
import com.code4ro.legalconsultation.document.metadata.model.persistence.DocumentMetadata;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMetadataMapper {
    DocumentMetadata map(DocumentMetadataDto documentMetadataDto);

    DocumentMetadata map(DocumentViewDto documentViewDto);

    DocumentMetadataDto map(DocumentMetadata documentMetadata);
}
