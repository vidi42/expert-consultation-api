package com.code4ro.legalconsultation.exceptions;

public class InvalidDocumentException extends IllegalArgumentException {
    public InvalidDocumentException(){
        super("Invalid document type!");
    }
}
