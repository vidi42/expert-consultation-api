package com.code4ro.legalconsultation.controller;

import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.service.api.DocumentService;
import com.code4ro.legalconsultation.service.api.DocumentStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/document")
public class DocumentController {

    //TODO Create implementation for the service first
    //@Autowired
    private DocumentService documentService;

    //TODO Create implementation for the service first
    //@Autowired
    private DocumentStorageService documentStorageService;

    @GetMapping("")
    public ResponseEntity getAllDocuments() {
        List<DocumentView> documents = documentService.fetchAll();
        return new ResponseEntity<>(documents, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getDocumentById(@PathVariable BigInteger id) {
        DocumentView doc;
        doc = documentService.fetchOne(id);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteDocument(@PathVariable BigInteger id) {
        documentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createDocument(@RequestParam("file") MultipartFile documentFile, @RequestBody DocumentView doc) {
        doc.setDocumentUploadPath(DocumentStorageService.resolveName(documentFile));
        doc.setDocumentURI(documentStorageService.storeFile(documentFile));
        documentService.create(doc);
        return new ResponseEntity<>(doc, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity modifyDocument(@PathVariable BigInteger id, @RequestBody DocumentView doc) {
        documentService.update(id, doc);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateDocumentFile(@PathVariable BigInteger id, @RequestParam("file") MultipartFile documentFile) {
        DocumentView doc;
        doc = documentService.fetchOne(id);
        doc.setDocumentUploadPath(DocumentStorageService.resolveName(documentFile));
        doc.setDocumentURI(documentStorageService.storeFile(documentFile));
        documentService.update(id, doc);
        return new ResponseEntity<>(doc, HttpStatus.OK);
    }
}
