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
public class Chapter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private UUID chapterUUID;
	
	@Column(name = "chapter_number",unique=true, nullable=false)
	private BigInteger chapterNumber;
	
	@Column(name = "chapter_title", nullable=false)
	private String chapterTitle;
	
	@OneToMany
	private List<Article> articles = new ArrayList<>();
	
	public UUID getChapterUUID() {
		return chapterUUID;
	}
	
	public void setChapterUUID(UUID chapterUUID) {
		this.chapterUUID = chapterUUID;
	}
	
	public BigInteger getChapterNumber() {
		return chapterNumber;
	}
	
	public void setChapterNumber(BigInteger chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	
	public String getChapterTitle() {
		return chapterTitle;
	}
	
	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
}
