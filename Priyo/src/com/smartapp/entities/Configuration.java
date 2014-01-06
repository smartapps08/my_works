package com.smartapp.entities;

public class Configuration {

	public String VehicleConfigurationID;
	public String BaseVehicleID;
	public String VehicleID;
	public String EngineID;
	public String SpringFrontType; 
	public String SpringRearType; 
	public String BedType;
	public String BedLength; 
	public String BedLengthMetric; 
	public String BodyDoorCount; 
	public String BodyType;
	public String BrakeSystem; 
	public String BrakeFrontType; 
	public String BrakeRearType; 
	public String BrakeABS; 
	public String WheelBase; 
	public String WheelBaseMetric; 
	public String DriveType; 
	public String SteeringType; 
	public String SteeringSystem; 
	public String TransmissionType; 
	public String TransmissionControlType; 
	public String TransmissionManufacturerCode; 
	public String TransmissionElectronicControl; 
	public String TransmissionSpeed; 
	
	public Configuration(String VehicleConfigurationID,String BaseVehicleID,String VehicleID,String EngineID,String SpringFrontType,String SpringRearType,String BedType,String BedLength,String BedLengthMetric,String BodyDoorCount,String BodyType,String BrakeSystem,String BrakeFrontType,String BrakeRearType,String BrakeABS,String WheelBase,String WheelBaseMetric,String DriveType,String SteeringType,String SteeringSystem,String TransmissionType,String TransmissionControlType,String TransmissionManufacturerCode,String TransmissionElectronicControl,String TransmissionSpeed) {
		this.VehicleConfigurationID = VehicleConfigurationID; 
		this.BaseVehicleID = BaseVehicleID; 
		this.VehicleID = VehicleID; 
		this.EngineID = EngineID; 
		this.SpringFrontType = SpringFrontType;  
		this.SpringRearType = SpringRearType;  
		this.BedType = BedType; 
		this.BedLength = BedLength;  
		this.BedLengthMetric = BedLengthMetric;  
		this.BodyDoorCount = BodyDoorCount;  
		this.BodyType = BodyType; 
		this.BrakeSystem = BrakeSystem;  
		this.BrakeFrontType = BrakeFrontType;  
		this.BrakeRearType = BrakeRearType;  
		this.BrakeABS = BrakeABS;  
		this.WheelBase = WheelBase;  
		this.WheelBaseMetric = WheelBaseMetric;  
		this.DriveType = DriveType;  
		this.SteeringType = SteeringType;  
		this.SteeringSystem = SteeringSystem;  
		this.TransmissionType = TransmissionType;  
		this.TransmissionControlType = TransmissionControlType;  
		this.TransmissionManufacturerCode = TransmissionManufacturerCode;  
		this.TransmissionElectronicControl = TransmissionElectronicControl;  
		this.TransmissionSpeed = TransmissionSpeed;  
	}
}
