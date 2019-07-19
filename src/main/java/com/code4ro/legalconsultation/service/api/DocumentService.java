package com.code4ro.legalconsultation.service.api;

import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public interface DocumentService {
    List<DocumentMetadata> fetchAll();
    Optional<DocumentMetadata> fetchOne(String id);
    DocumentMetadata create(DocumentView document);
    DocumentMetadata update(String id, DocumentView document);
    void deleteById(String id);
}
