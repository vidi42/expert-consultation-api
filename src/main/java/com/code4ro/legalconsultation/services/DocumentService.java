package com.code4ro.legalconsultation.services;

import com.code4ro.legalconsultation.models.DocumentView;

import java.math.BigInteger;
import java.util.List;

public interface DocumentService {
    List<DocumentView> fetchAll();
    DocumentView fetchOne(BigInteger id);
    void create(DocumentView document);
    void update(BigInteger id, DocumentView document);
    void deleteById(BigInteger id);
}
