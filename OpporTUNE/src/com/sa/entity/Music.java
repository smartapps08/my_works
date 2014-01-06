package com.sa.entity;

public class Music {
	private String id;
	private String artistName;
	private String artistId;
	private String title;
	private String trackId;
	private String foreignArtistId;

	public Music(String id, String artistName, String artistId, String title,
			String trackId, String foreignArtistId) {
		super();
		this.id = id;
		this.artistName = artistName;
		this.artistId = artistId;
		this.title = title;
		this.trackId = trackId;
		this.foreignArtistId = foreignArtistId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getForeignArtistId() {
		return foreignArtistId;
	}

	public void setForeignArtistId(String foreignArtistId) {
		this.foreignArtistId = foreignArtistId;
	}

	@Override
	public String toString() {
		return "Music [id=" + id + ", artistName=" + artistName + ", artistId="
				+ artistId + ", title=" + title + ", trackId=" + trackId
				+ ", foreignArtistId=" + foreignArtistId + "]";
	}

}
