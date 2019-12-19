package com.code4ro.legalconsultation.model.dto.dtoValidators;

import com.code4ro.legalconsultation.service.impl.validators.DocumentFilePathValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentFilePathValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDocumentFilePath {
    String message() default "document.save.duplicatedFilePath";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
