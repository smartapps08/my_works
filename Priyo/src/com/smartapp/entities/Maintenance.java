package com.smartapp.entities;

public class Maintenance {

	public String MaintenanceScheduleID;
	public String FrequencyDescription;
	public String IntervalMonth;
	public String IntervalMile;
	public String BaseLaborTime;
	public String LaborTimeInterval;
	public String ApplicationID;
	public String ContentCategoryID;
	public String LiteralName;
	public String SystemID;
	public String SystemName;
	public String Action;

	public Maintenance(String MaintenanceScheduleID,String FrequencyDescription,String IntervalMonth,String IntervalMile,String BaseLaborTime,String LaborTimeInterval,String ApplicationID,String ContentCategoryID,String LiteralName,String SystemID,String SystemName,String Action) {
		this.MaintenanceScheduleID = 	MaintenanceScheduleID; 
		this.FrequencyDescription = 	FrequencyDescription; 
		this.IntervalMonth = 	IntervalMonth; 
		this.IntervalMile = 	IntervalMile; 
		this.BaseLaborTime = 	BaseLaborTime; 
		this.LaborTimeInterval = 	LaborTimeInterval; 
		this.ApplicationID = 	ApplicationID; 
		this.ContentCategoryID = 	ContentCategoryID; 
		this.LiteralName = 	LiteralName; 
		this.SystemID = 	SystemID; 
		this.SystemName = 	SystemName; 
		this.Action = 	Action;
	}
	
}
