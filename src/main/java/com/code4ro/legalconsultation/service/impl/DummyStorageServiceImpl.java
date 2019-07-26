package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.service.api.DocumentStorageService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DummyStorageServiceImpl implements DocumentStorageService {

    private final static String DOCUMENT_PATH = "C:/legal/consultation/document/path";

    @Override
    public String storeFile(MultipartFile document) {
        return DOCUMENT_PATH;
    }

    @Override
    public Resource loadFileAsResource(String documentURI) {
        throw new UnsupportedOperationException();
    }
}
