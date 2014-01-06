package com.smartapps.lazylist;

public class Photo {
	private String id, owner, secret, server, farm, title, ispublic, isfriend,
			isfamily, imgUrl;
	private double lat, lng;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getIsfriend() {
		return isfriend;
	}

	public void setIsfriend(String isfriend) {
		this.isfriend = isfriend;
	}

	public String getIsfamily() {
		return isfamily;
	}

	public void setIsfamily(String isfamily) {
		this.isfamily = isfamily;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public Photo(String id, String owner, String secret, String server,
			String farm, String title, String ispublic, String isfriend,
			String isfamily, String imgUrl, double lat, double lng) {
		super();
		this.id = id;
		this.owner = owner;
		this.secret = secret;
		this.server = server;
		this.farm = farm;
		this.title = title;
		this.ispublic = ispublic;
		this.isfriend = isfriend;
		this.isfamily = isfamily;
		this.imgUrl = imgUrl;
		this.lat = lat;
		this.lng = lng;
	}

}
