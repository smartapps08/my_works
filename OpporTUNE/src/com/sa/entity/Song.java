package com.sa.entity;

public class Song {
	private String id;
	private String artist;
	private String title;
	private String data;
	private String displayName;
	private String composer;
	private String album;
	private int duration;

	public Song(String id, String artist, String title, String data,
			String displayName, String composer, String album, int duration) {
		this.id = id;
		this.artist = artist;
		this.title = title;
		this.data = data;
		this.displayName = displayName;
		this.composer = composer;
		this.album = album;
		this.duration = duration;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getComposer() {
		return composer;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "Song [id=" + id + ", artist=" + artist + ", title=" + title
				+ ", data=" + data + ", displayName=" + displayName
				+ ", composer=" + composer + ", album=" + album + ", duration="
				+ duration + "]";
	}

}
