package com.code4ro.legalconsultation.common.controller;

import java.util.List;

public class ExceptionResponse {
    private List<I18nError> i18nErrors;
    private List<I18nFieldError> i18nFieldErrors;
    private String additionalInfo;

    public List<I18nError> getI18nErrors() {
        return i18nErrors;
    }

    public void setI18nErrors(List<I18nError> i18nErrors) {
        this.i18nErrors = i18nErrors;
    }

    public List<I18nFieldError> getI18nFieldErrors() {
        return i18nFieldErrors;
    }

    public void setI18nFieldErrors(List<I18nFieldError> i18nFieldErrors) {
        this.i18nFieldErrors = i18nFieldErrors;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

