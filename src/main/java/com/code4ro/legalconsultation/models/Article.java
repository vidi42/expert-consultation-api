package com.code4ro.legalconsultation.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.util.UUID;

@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private UUID articleUUID;
	
	@Column(name = "article_number", unique=true, nullable=false)
	private BigInteger articleNumber;
	
	@Column(name = "article_title", nullable=false)
	private String articleTitle;
	
	@Column(name = "article_content", nullable=false)
	private String articleContent;
	
	public UUID getArticleUUID() {
		return articleUUID;
	}
	
	public void setArticleUUID(UUID articleUUID) {
		this.articleUUID = articleUUID;
	}
	
	public BigInteger getArticleNumber() {
		return articleNumber;
	}
	
	public void setArticleNumber(BigInteger articleNumber) {
		this.articleNumber = articleNumber;
	}
	
	public String getArticleTitle() {
		return articleTitle;
	}
	
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	
	public String getArticleContent() {
		return articleContent;
	}
	
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
}
