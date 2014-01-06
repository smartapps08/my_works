package com.smartapp.entities;

public class Make {
	private String id;
	private String name;
	
	
	public Make(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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
	@Override
	public String toString() {
		return "Make [id=" + id + ", name=" + name + "]";
	}
	
}
