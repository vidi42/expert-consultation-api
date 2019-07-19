package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentBreakdown;
import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.service.api.DocumentService;
import com.code4ro.legalconsultation.service.api.DocumentStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {

    private DocumentConsolidatedService documentConsolidatedService;
    private DocumentBreakdownService documentBreakdownService;
    private DocumentMetadataService documentMetadataService;
    private DocumentStorageService documentStorageService;

    public DocumentServiceImpl(DocumentConsolidatedService documentConsolidatedService,
                               DocumentBreakdownService documentBreakdownService,
                               DocumentMetadataService documentMetadataService,
                               DocumentStorageService documentStorageService) {
        this.documentConsolidatedService = documentConsolidatedService;
        this.documentBreakdownService = documentBreakdownService;
        this.documentMetadataService = documentMetadataService;
        this.documentStorageService = documentStorageService;
    }

    @Override
    public List<DocumentMetadata> fetchAll() {
        return documentConsolidatedService.findAll()
                .stream()
                .map(DocumentConsolidated::getDocumentMetadata)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DocumentMetadata> fetchOne(final String id)
            throws ResourceNotFoundException{
        return Optional.ofNullable(documentConsolidatedService.findOne(id)
                .map(DocumentConsolidated::getDocumentMetadata)
                .orElse(null));
    }

    @Override
    public Optional<DocumentConsolidated> fetchOneConsolidated(final String id) {
        return documentConsolidatedService.findOne(id);
    }

    @Transactional
    @Override
    public DocumentConsolidated create(final DocumentView document, final MultipartFile file) {

        DocumentMetadata metadata = documentMetadataService.build(document);
        String filePath  = documentStorageService.storeFile(file);
        metadata.setFilePath(filePath);
        metadata = documentMetadataService.save(metadata);

        DocumentBreakdown breakdown = documentBreakdownService.create(Paths.get(filePath));

        return documentConsolidatedService.saveOne(new DocumentConsolidated(metadata, breakdown));
    }

    @Override
    public Optional<DocumentConsolidated> update(final String id, final DocumentView document, final MultipartFile file) {
        Optional<DocumentConsolidated> consolidated = documentConsolidatedService.findOne(id);

        if(consolidated.isPresent()){

            DocumentConsolidated documentConsolidated = consolidated.get();
            //update the metadata

            DocumentMetadata metadata = documentMetadataService.build(document);
            String filePath  = documentStorageService.storeFile(file);
            metadata.setFilePath(filePath);
            metadata.setId(documentConsolidated.getDocumentMetadata().getId());
            documentMetadataService.save(metadata);

            //update the breakdown
            DocumentBreakdown breakdown = documentBreakdownService.create(Paths.get(filePath));

            documentConsolidated.setDocumentBreakdown(breakdown);
            return Optional.of(documentConsolidatedService.saveOne(documentConsolidated));
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(final String id) {
        documentConsolidatedService.deleteById(id);
    }
}
