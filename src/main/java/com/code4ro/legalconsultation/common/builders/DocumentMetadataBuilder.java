package com.code4ro.legalconsultation.common.builders;

import com.code4ro.legalconsultation.model.dto.DocumentView;
import com.code4ro.legalconsultation.model.persistence.DocumentMetadata;
import com.code4ro.legalconsultation.model.persistence.DocumentType;

import java.math.BigInteger;
import java.util.Date;

public class DocumentMetadataBuilder implements GenericBuilder<DocumentMetadata> {

    private BigInteger documentNumber;
    private String documentTitle;
    private String documentInitializer;
    private DocumentType documentType;
    private Date dateOfDevelopment;
    private Date dateOfReceipt;

    private DocumentMetadataBuilder withDocumentNumber(final BigInteger documentNumber){
        this.documentNumber = documentNumber;
        return this;
    }

    private DocumentMetadataBuilder withDocumentTitle(final String documentTitle){
        this.documentTitle = documentTitle;
        return this;
    }

    private DocumentMetadataBuilder withDocumentInitializer(final String documentInitializer){
        this.documentInitializer = documentInitializer;
        return this;
    }

    private DocumentMetadataBuilder withDocumentType(final DocumentType documentType){
        this.documentType = documentType;
        return this;
    }

    private DocumentMetadataBuilder withDateOfDevelopment(final Date dateOfDevelopment){
        this.dateOfDevelopment = dateOfDevelopment;
        return this;
    }

    private DocumentMetadataBuilder withDateOfReceipt(final Date dateOfReceipt){
        this.dateOfReceipt = dateOfReceipt;
        return this;
    }

    public static DocumentMetadata buildFromDocumentView(final DocumentView documentView){
        DocumentMetadataBuilder builder = new DocumentMetadataBuilder();

        return builder.withDocumentNumber(new BigInteger(documentView.getDocumentNumber()))
                .withDocumentTitle(documentView.getTitle())
                .withDocumentInitializer(documentView.getInitiator())
                .withDocumentType(documentView.getType())
                .withDateOfDevelopment(documentView.getElaborationDate())
                .withDateOfReceipt(documentView.getReceivedDate())
                .build();
    }

    @Override
    public DocumentMetadata build() {
        DocumentMetadata metadata = new DocumentMetadata();
        metadata.setDocumentNumber(this.documentNumber);
        metadata.setDocumentTitle(this.documentTitle);
        metadata.setDocumentInitializer(this.documentInitializer);
        metadata.setDocumentType(this.documentType);
        metadata.setDateOfDevelopment(this.dateOfDevelopment);
        metadata.setDateOfReceipt(this.dateOfReceipt);
        return metadata;
    }
}
