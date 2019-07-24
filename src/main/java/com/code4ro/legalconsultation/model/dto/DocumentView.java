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
public class DocumentView {
    private String title;
    private BigInteger documentNumber;
    private String initiator;
    private DocumentType type;
    private Date elaborationDate;
    private Date receivedDate;
    private String documentURI;
    private String documentUploadPath;
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
