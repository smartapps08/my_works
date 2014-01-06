package com.smartapps.util;

import android.app.Application;
import android.location.Location;

public class Constants extends Application {
	public static final String GCM_SENDER_ID = "944042750184";

	public static boolean notificationReceived;
	public static String notificationTitle = "", notificationType = "",
			notificationMsg = "", notificationUrl = "", registrationId = "";

	public static boolean isRegistered = false;

	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;

	// public static final String REG_REQUEST =
	// "http://23.21.47.98/photographr/register.php";
	// public static final String UNREG_REQUEST =
	// "http://23.21.47.98/photographr/unregister.php";
	// public static final String LOC_REQUEST =
	// "http://23.21.47.98/photographr/submit_location.php";
	// public static final String SEARCH_URL =
	// "http://23.21.47.98/photographr/fetch_suggestions.php?";

	public static final String REGISTRATION_SUCCESS_INTENT = "com.smartapps.photographr.REG_SUCCESS";
	public static final String REGISTRATION_FAILURE_INTENT = "com.smartapps.photographr.REG_FAILURE";
	public static final String UNREGISTRATION_SUCCESS_INTENT = "com.smartapps.photographr.UNREG_SUCCESS";
	public static final String UNREGISTRATION_FAILURE_INTENT = "com.smartapps.photographr.UNREG_FAILURE";

	// public static final String REGISTRATION_REQUEST =
	// "http://10.0.2.2/photographr/register.php";
	// public static final String UNREGISTRATION_REQUEST =
	// "http://10.0.2.2/photographr/unregister.php";
	// public static final String LOCATION_UPDATE_REQUEST =
	// "http://10.0.2.2/photographr/update_location.php";
	// public static final String SEARCH_URL =
	// "http://10.0.2.2/photographr/fetch_suggestions.php?";

	// public static final String REGISTRATION_REQUEST =
	// "http://192.168.174.1/photographr/register.php";
	// public static final String UNREGISTRATION_REQUEST =
	// "http://192.168.174.1/photographr/unregister.php";
	// public static final String LOCATION_UPDATE_REQUEST =
	// "http://192.168.174.1/photographr/update_location.php";
	// public static final String SEARCH_URL =
	// "http://192.168.174.1/photographr/fetch_suggestions.php?";
	// public static final String COMMENTS_URL =
	// "http://192.168.174.1/photographr/get_comments.php?";
	public static final String REGISTRATION_REQUEST = "http://ec2-54-213-113-233.us-west-2.compute.amazonaws.com/photographr/register.php";
	public static final String UNREGISTRATION_REQUEST = "http://ec2-54-213-113-233.us-west-2.compute.amazonaws.com/photographr/unregister.php";
	public static final String LOCATION_UPDATE_REQUEST = "http://ec2-54-213-113-233.us-west-2.compute.amazonaws.com/photographr/update_location.php";
	public static final String SEARCH_URL = "http://ec2-54-213-113-233.us-west-2.compute.amazonaws.com/photographr/fetch_suggestions.php?";
	public static final String COMMENTS_URL = "http://ec2-54-213-113-233.us-west-2.compute.amazonaws.com/photographr/get_comments.php?";

	public static final String PREF_FILE_NAME = "pref";
	public static final String PREF_REG_FIELD = "registered";
	public static final String PREF_SETTINGS_STARTUP_FIELD = "startup";
	public static final String PREF_SETTINGS_ACCURACY_FIELD = "accuracy";
	public static final String PREF_SETTINGS_SERVICE_FIELD = "service";
	public static final String PREF_SETTINGS_ALARM_FIELD = "alarm";
	// public static final String PREF_BACKGROUND_SERVICE = "background";
	// public static final String PREF_ALARM = "alarm";
	// public static final String PREF_INTERVAL = "interval";
	// public static final int ALARM_REQUEST_CODE = 198765;

}
