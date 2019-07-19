package com.code4ro.legalconsultation.model.persistence;

import javax.persistence.*;

@Entity
@Table(name = "consolidated_document")
public class DocumentConsolidated extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "metadata_id", referencedColumnName = "id")
    private DocumentMetadata documentMetadata;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "breakdown_id", referencedColumnName = "id")
    private DocumentBreakdown documentBreakdown;

    public DocumentConsolidated(DocumentMetadata documentMetadata, DocumentBreakdown documentBreakdown) {
        this.documentMetadata = documentMetadata;
        this.documentBreakdown = documentBreakdown;
    }

    public DocumentConsolidated() {
    }

    public DocumentMetadata getDocumentMetadata() {
        return documentMetadata;
    }

    public void setDocumentMetadata(DocumentMetadata documentMetadata) {
        this.documentMetadata = documentMetadata;
    }

    public DocumentBreakdown getDocumentBreakdown() {
        return documentBreakdown;
    }

    public void setDocumentBreakdown(DocumentBreakdown documentBreakdown) {
        this.documentBreakdown = documentBreakdown;
    }
}
