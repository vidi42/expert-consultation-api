package com.code4ro.legalconsultation.core.exception;

import com.code4ro.legalconsultation.i18n.model.I18nError;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Builder
public class LegalValidationException extends RuntimeException {
    private final String i18nKey;
    private final List<String> i8nArguments;
    private final HttpStatus httpStatus;
    private final Map<String, I18nError> i18nFieldErrors;
}
