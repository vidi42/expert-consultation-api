package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.model.persistence.DocumentType;
import com.code4ro.legalconsultation.service.api.DocumentService;
import com.code4ro.legalconsultation.service.api.DocumentStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentStorageService documentStorageService;

    @GetMapping("")
    public ResponseEntity<List<DocumentMetadata>> getAllDocuments() {
        List<DocumentMetadata> documents = documentService.fetchAll();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getDocumentById(@PathVariable String id) {
        Optional<DocumentMetadata> optDocument = documentService.fetchOne(id);
        if(optDocument.isPresent())
            return ResponseEntity.ok(optDocument.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocument(@PathVariable String id) {
        documentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<UUID> createDocument(@RequestParam("title") String documentTitle,
            @RequestParam("number") BigInteger documentNumber,
            @RequestParam("initiator") String initiator,
            @RequestParam("type") DocumentType type,
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date creationDate,
            @DateTimeFormat(pattern = "dd/MM/yyyy") Date receiveDate,
            @RequestParam("file") MultipartFile documentFile) {

        DocumentView documentView = new DocumentView(documentTitle, documentNumber, initiator, type, creationDate, receiveDate);
        DocumentConsolidated consolidated = documentService.create(documentView, documentFile);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(consolidated.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UUID> modifyDocument(@PathVariable String id, @RequestParam("file") MultipartFile documentFile, @RequestBody DocumentView doc) {
        Optional<DocumentConsolidated> consolidated = documentService.update(id, doc, documentFile);
        if(consolidated.isPresent())
            return ResponseEntity.ok(consolidated.get().getId());
        else
            return ResponseEntity.notFound().build();
    }
}
