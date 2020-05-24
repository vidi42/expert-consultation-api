package com.code4ro.legalconsultation.service.impl.pdf;

import com.code4ro.legalconsultation.common.exceptions.LegalValidationException;
import com.code4ro.legalconsultation.service.api.PDFService;
import com.code4ro.legalconsultation.service.impl.pdf.reader.BasicOARPdfReader;
import com.code4ro.legalconsultation.service.impl.pdf.reader.PDFReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class PDFServiceImpl implements PDFService {

    private final BasicOARPdfReader basicOARPdfReader;

    @Autowired
    public PDFServiceImpl(final BasicOARPdfReader basicOARPdfReader) {
        this.basicOARPdfReader = basicOARPdfReader;
    }

    @Override
    public String readAsString(final byte[] file) {

        try {
            final PDDocument doc = PDDocument.load(file);
            // TODO: add a more general way for getting the right parser based on document template once we have more document types
            final PDFReader pdfReader = basicOARPdfReader;
            return pdfReader.getContent(doc);
        } catch (IOException e) {
            log.warn("Exception while parsing PDF file", e);
            throw new LegalValidationException("document.parse.pdf.failed", HttpStatus.BAD_REQUEST);
        }
    }
}
