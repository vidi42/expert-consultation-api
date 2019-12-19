package com.code4ro.legalconsultation.service.impl.validators;

import com.code4ro.legalconsultation.model.dto.dtoValidators.UniqueDocumentNumberConstraint;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;

@AllArgsConstructor
public class DocumentNumberValidation implements ConstraintValidator<UniqueDocumentNumberConstraint, BigInteger> {
    private DocumentMetadataRepository documentMetadataRepository;

    @Override
    public boolean isValid(final BigInteger documentNumber, final ConstraintValidatorContext context) {
        return !documentMetadataRepository.existsByDocumentNumber(documentNumber);
    }
}
