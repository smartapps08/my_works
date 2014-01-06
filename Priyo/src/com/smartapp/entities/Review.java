package com.smartapp.entities;

import java.util.ArrayList;

public class Review {
	private String make;
	private String model;
	private String year;
	private String kbbUrl;
	private String kbbTitle;
	private String kbbSentiment;
	private String kbbScore;
	private ArrayList<String> kbbConcepts;
	private String edmundsUrl;
	private String edmundsTitle;
	private String edmundsSentiment;
	private String edmundsScore;
	private ArrayList<String> edmundsConcepts;

	public Review(String make, String model, String year, String kbbUrl,
			String kbbTitle, String kbbSentiment, String kbbScore,
			ArrayList<String> kbbConcepts, String edmundsUrl,
			String edmundsTitle, String edmundsSentiment, String edmundsScore,
			ArrayList<String> edmundsConcepts) {
		this.make = make;
		this.model = model;
		this.year = year;
		this.kbbUrl = kbbUrl;
		this.kbbTitle = kbbTitle;
		this.kbbSentiment = kbbSentiment;
		this.kbbScore = kbbScore;
		this.kbbConcepts = kbbConcepts;
		this.edmundsUrl = edmundsUrl;
		this.edmundsTitle = edmundsTitle;
		this.edmundsSentiment = edmundsSentiment;
		this.edmundsScore = edmundsScore;
		this.edmundsConcepts = edmundsConcepts;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getKbbUrl() {
		return kbbUrl;
	}

	public void setKbbUrl(String kbbUrl) {
		this.kbbUrl = kbbUrl;
	}

	public String getKbbTitle() {
		return kbbTitle;
	}

	public void setKbbTitle(String kbbTitle) {
		this.kbbTitle = kbbTitle;
	}

	public String getKbbSentiment() {
		return kbbSentiment;
	}

	public void setKbbSentiment(String kbbSentiment) {
		this.kbbSentiment = kbbSentiment;
	}

	public String getKbbScore() {
		return kbbScore;
	}

	public void setKbbScore(String kbbScore) {
		this.kbbScore = kbbScore;
	}

	public ArrayList<String> getKbbConcepts() {
		return kbbConcepts;
	}

	public void setKbbConcepts(ArrayList<String> kbbConcepts) {
		this.kbbConcepts = kbbConcepts;
	}

	public String getEdmundsUrl() {
		return edmundsUrl;
	}

	public void setEdmundsUrl(String edmundsUrl) {
		this.edmundsUrl = edmundsUrl;
	}

	public String getEdmundsTitle() {
		return edmundsTitle;
	}

	public void setEdmundsTitle(String edmundsTitle) {
		this.edmundsTitle = edmundsTitle;
	}

	public String getEdmundsSentiment() {
		return edmundsSentiment;
	}

	public void setEdmundsSentiment(String edmundsSentiment) {
		this.edmundsSentiment = edmundsSentiment;
	}

	public String getEdmundsScore() {
		return edmundsScore;
	}

	public void setEdmundsScore(String edmundsScore) {
		this.edmundsScore = edmundsScore;
	}

	public ArrayList<String> getEdmundsConcepts() {
		return edmundsConcepts;
	}

	public void setEdmundsConcepts(ArrayList<String> edmundsConcepts) {
		this.edmundsConcepts = edmundsConcepts;
	}

	@Override
	public String toString() {
		return "Review [make=" + make + ", model=" + model + ", year=" + year
				+ ", kbbUrl=" + kbbUrl + ", kbbTitle=" + kbbTitle
				+ ", kbbSentiment=" + kbbSentiment + ", kbbScore=" + kbbScore
				+ ", kbbConcepts=" + kbbConcepts + ", edmundsUrl=" + edmundsUrl
				+ ", edmundsTitle=" + edmundsTitle + ", edmundsSentiment="
				+ edmundsSentiment + ", edmundsScore=" + edmundsScore
				+ ", edmundsConcepts=" + edmundsConcepts + "]";
	}

}
