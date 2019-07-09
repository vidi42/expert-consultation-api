package com.code4ro.legalconsultation.common.controller;

import org.springframework.http.HttpStatus;

import java.util.List;

public class LegalValidationException extends RuntimeException {
    private final String i18nKey;
    private final List<String> i8nArguments;
    private final HttpStatus httpStatus;

    public LegalValidationException(final String i18nKey, final List<String> i8nArguments, final HttpStatus httpStatus) {
        this.i18nKey = i18nKey;
        this.i8nArguments = i8nArguments;
        this.httpStatus = httpStatus;
    }

    public LegalValidationException(final String i18nKey, final HttpStatus httpStatus) {
        this.i18nKey = i18nKey;
        this.httpStatus = httpStatus;
        this.i8nArguments = null;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public List<String> getI8nArguments() {
        return i8nArguments;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
