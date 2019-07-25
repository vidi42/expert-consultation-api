package com.code4ro.legalconsultation.model.dto;

import com.code4ro.legalconsultation.common.exceptions.InvalidDocumentException;
import com.code4ro.legalconsultation.model.persistence.DocumentType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
public class DocumentViewDto {
    private String title;
    private BigInteger documentNumber;
    private String documentInitializer;
    private DocumentType documentType;
    private Date dateOfDevelopment;
    private Date dateOfReceipt;
    private String documentURI;
    private String documentUploadPath;
    private final String[] extensions = {"xls", "xlsx", "doc", "docx"};


    public DocumentViewDto(String title, BigInteger documentNumber, String documentInitializer, DocumentType documentType, Date dateOfDevelopment, Date dateOfReceipt) {
        this.title = title;
        this.documentNumber = documentNumber;
        this.documentInitializer = documentInitializer;
        this.documentType = documentType;
        this.dateOfDevelopment = dateOfDevelopment;
        this.dateOfReceipt = dateOfReceipt;
    }

    public DocumentViewDto() {
    }

    public void setDocumentUploadPath(String documentUploadPath) {
        if (isValidUploadPath(documentUploadPath))
            this.documentUploadPath = documentUploadPath;
        else throw new InvalidDocumentException();
    }

    private boolean isValidUploadPath(String path) {
        String extension = computeExtension(path);
        return Arrays.asList(extensions).contains(extension);
    }

    private String computeExtension(String path) {
        return path.substring(path.lastIndexOf('.') + 1).toLowerCase();
    }
}
