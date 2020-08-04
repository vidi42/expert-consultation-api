package com.code4ro.legalconsultation.document.reader.service;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public interface PDFReader {
    String getContent(PDDocument document) throws IOException;
}
