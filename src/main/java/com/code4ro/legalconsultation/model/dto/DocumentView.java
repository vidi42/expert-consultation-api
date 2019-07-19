package com.code4ro.legalconsultation.model.dto;

import com.code4ro.legalconsultation.model.persistence.DocumentType;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

public class DocumentView {
    private String title;
    private BigInteger documentNumber;
    private String initiator;
    private DocumentType type;
    private Date elaborationDate;
    private Date receivedDate;
    private final String[] extensions = {"xls", "xlsx", "doc", "docx"};

    public DocumentView(String title, BigInteger documentNumber, String initiator, DocumentType type, Date elaborationDate, Date receivedDate) {
        this.title = title;
        this.documentNumber = documentNumber;
        this.initiator = initiator;
        this.type = type;
        this.elaborationDate = elaborationDate;
        this.receivedDate = receivedDate;
    }

    public DocumentView() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigInteger getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(BigInteger documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public Date getElaborationDate() {
        return elaborationDate;
    }

    public void setElaborationDate(Date elaborationDate) {
        this.elaborationDate = elaborationDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    private boolean isValidUploadPath(String path) {
        String extension = computeExtension(path);
        return Arrays.asList(extensions).contains(extension);
    }

    private String computeExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase();
    }

    public DocumentType getType() {
        return type;
    }

    public void setType(DocumentType type) {
        this.type = type;
    }
}
