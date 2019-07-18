package com.code4ro.legalconsultation.common.exceptions;

public class InvalidDocumentException extends IllegalArgumentException {
    public InvalidDocumentException(){
        super("Invalid document type!");
    }
}
