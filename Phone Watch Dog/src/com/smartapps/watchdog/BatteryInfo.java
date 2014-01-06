package com.smartapps.watchdog;

public class BatteryInfo {
	public static int health;
	public static int icon_small ;
	public static int level;
	public static int plugged ;
	public static boolean present;
	public static int scale; 
	public static int status;
	public static String technology;
	public static int temperature;
	public static int voltage; 
	
	public static String getMessage()
	{
		String message="Health: " + health + "\n" + "Icon Small:"
				+ icon_small + "\n" + "Level: " + level + "\n"
				+ "Plugged: " + plugged + "\n" + "Present: " + present
				+ "\n" + "Scale: " + scale + "\n" + "Status: " + status
				+ "\n" + "Technology: " + technology + "\n"
				+ "Temperature: " + temperature + "\n" + "Voltage: "
				+ voltage + "\n";
		
		return message;
	}

}
