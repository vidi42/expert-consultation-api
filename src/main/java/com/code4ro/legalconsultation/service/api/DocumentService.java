package com.code4ro.legalconsultation.service.api;

import com.code4ro.legalconsultation.common.exceptions.ResourceNotFoundException;
import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public interface DocumentService {
    //return all metadata for documents
    List<DocumentMetadata> fetchAll();

    //return document metadata for a single document
    Optional<DocumentMetadata> fetchOne(final String id);

    //return the entire consolidated form of the document,
    //that contains the metadata and the breakdown into units
    Optional<DocumentConsolidated> fetchOneConsolidated(final String id);

    //create a single document, including metadata and breakdown
    DocumentConsolidated create(final DocumentView document, final MultipartFile file);

    //update a single document
    Optional<DocumentConsolidated> update(final String id, final DocumentView document, final MultipartFile multipartFile);

    //delete a document based on id
    void deleteById(final String id);
}
