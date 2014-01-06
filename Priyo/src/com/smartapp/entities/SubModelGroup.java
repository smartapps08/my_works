package com.smartapp.entities;

public class SubModelGroup {
	private String id;
	private String name;
	private String model;

	public SubModelGroup(String id, String name, String model) {
		this.id = id;
		this.name = name;
		this.model = model;
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

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Override
	public String toString() {
		return "SubModelGroup [id=" + id + ", name=" + name + ", model="
				+ model + "]";
	}

}
