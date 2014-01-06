package com.banglacalendar.test;

public class BanglaDate {

	private int bangDay; // generated Bangla Date
	private int bangMonth; // generated Bangla Month
	private int bangYear; // generated Bangla Year

	public BanglaDate(int bangDay, int bangMonth, int bangYear) {
		this.bangDay = bangDay;
		this.bangMonth = bangMonth;
		this.bangYear = bangYear;
	}

	public int getBangDay() {
		return bangDay;
	}

	public void setBangDay(int bangDay) {
		this.bangDay = bangDay;
	}

	public int getBangMonth() {
		return bangMonth;
	}

	public void setBangMonth(int bangMonth) {
		this.bangMonth = bangMonth;
	}

	public int getBangYear() {
		return bangYear;
	}

	public void setBangYear(int bangYear) {
		this.bangYear = bangYear;
	}

	public String toString() {
		return bangDay + "-" + bangMonth + "-" + bangYear;
	}

}
