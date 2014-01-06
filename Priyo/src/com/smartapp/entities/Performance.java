package com.smartapp.entities;

public class Performance {
	private String id;
	private double quarterMileTime;
	private double quarterMileSpeed;

	public Performance(String id, double quarterMileTime,
			double quarterMileSpeed) {
		this.id = id;
		this.quarterMileTime = quarterMileTime;
		this.quarterMileSpeed = quarterMileSpeed;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getQuarterMileTime() {
		return quarterMileTime;
	}

	public void setQuarterMileTime(double quarterMileTime) {
		this.quarterMileTime = quarterMileTime;
	}

	public double getQuarterMileSpeed() {
		return quarterMileSpeed;
	}

	public void setQuarterMileSpeed(double quarterMileSpeed) {
		this.quarterMileSpeed = quarterMileSpeed;
	}

}
