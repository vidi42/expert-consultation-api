package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.common.builders.DocumentMetadataBuilder;
import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentMetadataService {

    private final DocumentMetadataRepository documentMetadataRepository;

    @Autowired
    public DocumentMetadataService(DocumentMetadataRepository documentMetadataRepository) {
        this.documentMetadataRepository = documentMetadataRepository;
    }

    public List<DocumentMetadata> fetchAll() {
        return documentMetadataRepository.findAll();
    }

    public Optional<DocumentMetadata> fetchOne(final String id) {
        return documentMetadataRepository.findById(UUID.fromString(id));
    }

    public DocumentMetadata create(final DocumentView document) {
        return documentMetadataRepository.save(DocumentMetadataBuilder.buildFromDocumentView(document));
    }

    public DocumentMetadata update(final String id, final DocumentView document) {
        DocumentMetadata metadata = DocumentMetadataBuilder.buildFromDocumentView(document);
        metadata.setId(UUID.fromString(id));
        return documentMetadataRepository.save(metadata);
    }

    public void deleteById(final String id) {
        documentMetadataRepository.deleteById(UUID.fromString(id));
    }
}
