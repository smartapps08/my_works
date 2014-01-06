package com.smartapps.newsappdemo;

import java.io.Serializable;

public class NewsItem implements Serializable {

	public String text;
	public String url;

	public NewsItem(String text, String url) {
		super();
		this.text = text;
		this.url = url;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
