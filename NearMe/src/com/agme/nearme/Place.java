package com.agme.nearme;

public class Place {
	private String id;
	private String name;
	private String icon;
	private double rating;
	private String vicinity;
	private double latitude;
	private double longitude;

	public Place(String id, String name, String icon, double rating,
			String vicinity, double latitude, double longitude) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.rating = rating;
		this.vicinity = vicinity;
		this.latitude = latitude;
		this.longitude = longitude;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Place [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", rating=" + rating + ", vicinity=" + vicinity
				+ ", latitude=" + latitude + ", longitude=" + longitude + "]";
	}

}
