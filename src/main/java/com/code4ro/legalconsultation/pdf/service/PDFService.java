package com.code4ro.legalconsultation.pdf.service;

import com.code4ro.legalconsultation.document.consolidated.model.persistence.DocumentConsolidated;
import com.code4ro.legalconsultation.pdf.model.persistence.PdfHandle;
import org.springframework.web.multipart.MultipartFile;

public interface PDFService {
    String readAsString(byte[] file);

    PdfHandle createPdf(DocumentConsolidated documentConsolidatedId, String state, MultipartFile file);
}
