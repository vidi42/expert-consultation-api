package com.code4ro.legalconsultation.document.export.service;

import com.code4ro.legalconsultation.document.consolidated.model.persistence.DocumentConsolidated;

public interface DocumentExporter {
    byte[] export(DocumentConsolidated document);
}
