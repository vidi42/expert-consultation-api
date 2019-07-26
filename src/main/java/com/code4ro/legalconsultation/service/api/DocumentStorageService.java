package com.code4ro.legalconsultation.service.api;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface DocumentStorageService {

    static String resolveName(final MultipartFile document){
        return StringUtils.cleanPath(document.getOriginalFilename());
    }

    String storeFile(final MultipartFile document);

    Resource loadFileAsResource(String documentURI);
}
