package com.code4ro.legalconsultation.model.dto;

import com.code4ro.legalconsultation.common.exceptions.InvalidDocumentException;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;

@Getter
@Setter
public class DocumentView {
    private String title;
    private String documentNumber;
    private String initiator;
    private Date elaborationDate;
    private Date receivedDate;
    private String documentURI;
    private String documentUploadPath;
    private final String[] extensions = {"xls", "xlsx", "doc", "docx"};

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
