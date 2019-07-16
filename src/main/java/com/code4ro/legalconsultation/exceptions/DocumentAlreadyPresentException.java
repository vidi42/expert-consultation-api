package com.code4ro.legalconsultation.exceptions;

public class DocumentAlreadyPresentException extends RuntimeException{
    public DocumentAlreadyPresentException(){
        super("The document is already present!");
    }
}
