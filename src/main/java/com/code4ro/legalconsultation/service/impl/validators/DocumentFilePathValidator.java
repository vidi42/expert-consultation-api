package com.code4ro.legalconsultation.service.impl.validators;

import com.code4ro.legalconsultation.model.dto.dtoValidators.UniqueDocumentFilePath;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class DocumentFilePathValidator implements ConstraintValidator<UniqueDocumentFilePath, String> {
    private DocumentMetadataRepository documentMetadataRepository;

    @Override
    public boolean isValid(final String filePath, final ConstraintValidatorContext context) {
        return !documentMetadataRepository.existsByFilePath(filePath);
    }
}
