package com.code4ro.legalconsultation.common.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Document not found");
    }
}
