package com.code4ro.legalconsultation.core.model.dto.validator;

import com.code4ro.legalconsultation.core.validators.DocumentNumberValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentNumberValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDocumentNumberConstraint {
    String message() default "document.save.duplicatedNumber";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
