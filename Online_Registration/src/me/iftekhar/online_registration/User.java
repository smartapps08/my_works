package me.iftekhar.online_registration;

import java.io.Serializable;

public class User implements Serializable{
	private int id;
	private String name;
	private String adress;
	private String password;
	private String phone;
	private String email;
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", adress=" + adress
				+ ", password=" + password + ", phone=" + phone + ", email="
				+ email + "]";
	}
	public User(String name,String adress, String password,
			String phone,String email) {
		super();
		
		this.name = name;
		this.email = email;
		this.adress = adress;
		this.password = password;
		this.phone = phone;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
