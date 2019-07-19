package com.code4ro.legalconsultation.service.impl;

import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.repository.DocumentConsolidatedRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentConsolidatedService {

    private static final Logger logger = LoggerFactory.getLogger(DocumentConsolidatedService.class);

    private final DocumentConsolidatedRepository documentConsolidatedRepository;

    @Autowired
    public DocumentConsolidatedService(final DocumentConsolidatedRepository repository){
        this.documentConsolidatedRepository = repository;
    }

    public Optional<DocumentConsolidated> findOne(final String uuid){
        return documentConsolidatedRepository.findById(UUID.fromString(uuid));
    }

    public List<DocumentConsolidated> findAll(){
        return documentConsolidatedRepository.findAll();
    }

    public DocumentConsolidated saveOne(final DocumentConsolidated documentConsolidated){
        return documentConsolidatedRepository.save(documentConsolidated);
    }

    public List<DocumentConsolidated> saveAll(List<DocumentConsolidated> documentConsolidatedList){
        return documentConsolidatedRepository.saveAll(documentConsolidatedList);
    }

    public void deleteById(final String uuid){
        documentConsolidatedRepository.deleteById(UUID.fromString(uuid));
    }
}
