package com.code4ro.legalconsultation.document.node.service;

import com.code4ro.legalconsultation.document.node.model.dto.DocumentNodeSimpleDto;
import com.code4ro.legalconsultation.document.node.model.persistence.DocumentNode;

import java.util.UUID;

public interface DocumentNodeService {
    DocumentNode findById(UUID id);

    DocumentNode findRootNodeForId(UUID id);

    DocumentNode parse(String pdfContent);

    DocumentNode create(final DocumentNode documentNode);

    DocumentNode update(DocumentNodeSimpleDto documentNode);

    void deleteById(UUID id);
}
