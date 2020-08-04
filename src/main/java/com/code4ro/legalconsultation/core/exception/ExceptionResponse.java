package com.code4ro.legalconsultation.core.exception;

import com.code4ro.legalconsultation.i18n.model.I18nError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ExceptionResponse {
    private List<I18nError> i18nErrors;
    private Map<String, I18nError> i18nFieldErrors;
    private String additionalInfo;
}

