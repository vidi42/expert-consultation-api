package com.code4ro.legalconsultation.common.controller;

import java.util.List;

public class I18nFieldError extends I18nError {
    private final String fieldName;

    public I18nFieldError(final String fieldName, final String i18nErrorKey, List<String> i18nErrorArguments) {
        super(i18nErrorKey, i18nErrorArguments);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
