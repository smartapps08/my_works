package com.smartapps.vacation;

import java.util.Date;

import org.joda.time.DateTime;

public class Holiday {
	private String caption;
	private int type;
	private DateTime dateTime;
	private boolean lunar;

	public Holiday(String caption, int type, DateTime dateTime, boolean lunar) {
		this.caption = caption;
		this.type = type;
		this.dateTime = dateTime;
		this.lunar = lunar;
	}

	public Holiday(String caption, int type, Date date, boolean lunar) {
		this.caption = caption;
		this.type = type;
		DateTime dt = new DateTime(date.getTime());
		this.dateTime = dt;
		this.lunar = lunar;
	}

	public boolean isLunar() {
		return lunar;
	}

	public void setLunar(boolean lunar) {
		this.lunar = lunar;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public DateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}

}
