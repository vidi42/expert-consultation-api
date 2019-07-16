package com.code4ro.legalconsultation.models;

import com.code4ro.legalconsultation.exceptions.InvalidDocumentException;

import java.util.Arrays;
import java.util.Date;

public class DocumentView {
    private String title;
    private String documentNumber;
    private String initiator;
    private Date elaborationDate;
    private Date receivedDate;
    private String documentURI;
    private String documentUploadPath;
    private final String[] extensions = {"xls", "xlsx", "doc", "docx"};

    public String getDocumentUploadPath() {
        return documentUploadPath;
    }

    public void setDocumentUploadPath(String documentUploadPath) {
        if (isValidUploadPath(documentUploadPath))
            this.documentUploadPath = documentUploadPath;
        else throw new InvalidDocumentException();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
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

    public String getDocumentURI() {
        return documentURI;
    }

    public void setDocumentURI(String documentURI) {
        this.documentURI = documentURI;
    }

    private boolean isValidUploadPath(String path) {
        String extension = computeExtension(path);
        return Arrays.asList(extensions).contains(extension);
    }

    private String computeExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1).toLowerCase();
    }
}
