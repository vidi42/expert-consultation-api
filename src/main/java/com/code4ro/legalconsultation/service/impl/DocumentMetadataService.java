package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.model.dto.DocumentViewDto;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.repository.DocumentMetadataRepository;
import com.code4ro.legalconsultation.service.api.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentMetadataService {

    private final DocumentMetadataRepository documentMetadataRepository;
    private final MapperService mapperService;

    @Autowired
    public DocumentMetadataService(DocumentMetadataRepository documentMetadataRepository,
                                   MapperService mapperService) {
        this.documentMetadataRepository = documentMetadataRepository;
        this.mapperService = mapperService;
    }

    public DocumentMetadata save(final DocumentMetadata documentMetadata){
        return documentMetadataRepository.save(documentMetadata);
    }

    public List<DocumentMetadata> fetchAll() {
        return documentMetadataRepository.findAll();
    }

    public Optional<DocumentMetadata> fetchOne(final String id) {
        return documentMetadataRepository.findById(UUID.fromString(id));
    }

    public DocumentMetadata build(final DocumentViewDto document){
        return mapperService.map(document, DocumentMetadata.class);
    }

    public DocumentMetadata create(final DocumentViewDto document) {
        return documentMetadataRepository.save(mapperService.map(document, DocumentMetadata.class));
    }

    public DocumentMetadata update(final String id, final DocumentViewDto document) {
        DocumentMetadata metadata = mapperService.map(document, DocumentMetadata.class);
        metadata.setId(UUID.fromString(id));
        return documentMetadataRepository.save(metadata);
    }

    public void deleteById(final String id) {
        documentMetadataRepository.deleteById(UUID.fromString(id));
    }
}
