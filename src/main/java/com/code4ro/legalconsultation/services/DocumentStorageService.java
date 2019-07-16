package com.code4ro.legalconsultation.services;

import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentStorageService {
    static String resolveName(MultipartFile document){
        return StringUtils.cleanPath(document.getOriginalFilename());
    }
    String storeFile(MultipartFile document);
    Resource loadFileAsResource(String documentURI);
}
