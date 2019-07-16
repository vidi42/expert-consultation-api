package com.code4ro.legalconsultation.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Document not found");
    }
}
