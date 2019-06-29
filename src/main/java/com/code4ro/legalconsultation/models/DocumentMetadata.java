package com.code4ro.legalconsultation.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigInteger;
import java.sql.Date;
import java.util.UUID;

@Entity
public class DocumentMetadata {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private UUID documentUUID;
	
	@Column(name = "document_number", unique=true, nullable=false)
	private BigInteger documentNumber;
	
	@Column(name = "document_title", nullable=false)
	private String documentTitle;
	
	@Column(name = "document_initializer", nullable=false)
	private String documentInitializer;
	
	@Column(name = "document_type")
	@Enumerated(EnumType.STRING)
	private String documentType;
	
	@Column(name = "date_of_development", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dateOfDevelopment;
	
	@Column(name ="date_of_receipt", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date dateOfReceipt;
	
	public UUID getDocumentUUID() {
		return documentUUID;
	}
	
	public void setDocumentUUID(UUID documentUUID) {
		this.documentUUID = documentUUID;
	}
	
	public BigInteger getDocumentNumber() {
		return documentNumber;
	}
	
	public void setDocumentNumber(BigInteger documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public String getDocumentTitle() {
		return documentTitle;
	}
	
	public void setDocumentTitle(String documentTitle) {
		this.documentTitle = documentTitle;
	}
	
	public String getDocumentInitializer() {
		return documentInitializer;
	}
	
	public void setDocumentInitializer(String documentInitializer) {
		this.documentInitializer = documentInitializer;
	}
	
	public String getDocumentType() {
		return documentType;
	}
	
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	
	public Date getDateOfDevelopment() {
		return dateOfDevelopment;
	}
	
	public void setDateOfDevelopment(Date dateOfDevelopment) {
		this.dateOfDevelopment = dateOfDevelopment;
	}
	
	public Date getDateOfReceipt() {
		return dateOfReceipt;
	}
	
	public void setDateOfReceipt(Date dateOfReceipt) {
		this.dateOfReceipt = dateOfReceipt;
	}
}
