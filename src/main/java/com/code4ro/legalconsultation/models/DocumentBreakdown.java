package com.code4ro.legalconsultation.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class DocumentBreakdown {
	
	@Id
	@Column(name = "id")
	private UUID documentUUID;
	
	@Column(name = "document_number", unique=true, nullable=false)
	private BigInteger documentNumber;
	
	@Column(name = "document_title", nullable=false)
	private String documentTitle;
	
	@OneToMany
	private List<Chapter> chapters = new ArrayList<>();
	
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
	
	public List<Chapter> getChapters() {
		return chapters;
	}
	
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
}
