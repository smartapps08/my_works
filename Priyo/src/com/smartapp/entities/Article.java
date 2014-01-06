package com.smartapp.entities;

public class Article {
	private String title;
	private String contentUrl;
	
	public Article(String title, String contentUrl) {
		this.title = title;
		this.contentUrl = contentUrl;
	}
	@Override
	public String toString() {
		return "Article [title=" + title + ", contentUrl=" + contentUrl + "]";
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
}
