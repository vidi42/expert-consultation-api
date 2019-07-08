package com.code4ro.legalconsultation.common.controller;

import java.util.List;

public class I18nError {
    private final String i18nErrorKey;
    private final List<String> i18nErrorArguments;

    public I18nError(final String i18nErrorKey, List<String> i18nErrorArguments) {
        this.i18nErrorKey = i18nErrorKey;
        this.i18nErrorArguments = i18nErrorArguments;
    }

    public String getI18nErrorKey() {
        return i18nErrorKey;
    }

    public List<String> getI18nErrorArguments() {
        return i18nErrorArguments;
    }
}
