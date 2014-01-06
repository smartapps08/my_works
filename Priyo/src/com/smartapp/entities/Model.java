package com.smartapp.entities;

public class Model {
	private String id;
	private String name;
	private String make;
	
	
	public Model(String id, String name, String make) {
		this.id = id;
		this.name = name;
		this.make = make;
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
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	@Override
	public String toString() {
		return "Model [id=" + id + ", name=" + name + ", make=" + make + "]";
	}
	
}
