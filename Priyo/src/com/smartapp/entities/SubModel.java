package com.smartapp.entities;

import java.io.Serializable;

public class SubModel implements Serializable {
	private String id;
	private String name;
	private String image;
	private String model;
	private String make;
	private String year;
	private String subModelGroup;

	public SubModel(String id, String name, String image, String model,
			String make, String year, String subModelGroup) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.model = model;
		this.make = make;
		this.year = year;
		this.subModelGroup = subModelGroup;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getSubModelGroup() {
		return subModelGroup;
	}

	public void setSubModelGroup(String subModelGroup) {
		this.subModelGroup = subModelGroup;
	}

	@Override
	public String toString() {
		return "SubModel [id=" + id + ", name=" + name + ", image=" + image
				+ ", model=" + model + ", make=" + make + ", year=" + year
				+ ", subModelGroup=" + subModelGroup + "]";
	}

}
