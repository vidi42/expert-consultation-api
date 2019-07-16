package com.code4ro.legalconsultation.common.controller;

import java.util.List;
import java.util.Map;

public class ExceptionResponse {
    private List<I18nError> i18nErrors;
    private Map<String, I18nError> i18nFieldErrors;
    private String additionalInfo;

    public List<I18nError> getI18nErrors() {
        return i18nErrors;
    }

    public void setI18nErrors(List<I18nError> i18nErrors) {
        this.i18nErrors = i18nErrors;
    }

    public Map<String, I18nError> getI18nFieldErrors() {
        return i18nFieldErrors;
    }

    public void setI18nFieldErrors(Map<String, I18nError> i18nFieldErrors) {
        this.i18nFieldErrors = i18nFieldErrors;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

