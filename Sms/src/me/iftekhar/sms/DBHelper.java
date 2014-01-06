package me.iftekhar.sms;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DB_name="Messaging";
	public static final int Version=1;
	
	
	public static final String T_Inbox="Inbox";
	public static final String Serial_no="serial";
	public static final String Number="number";
	public static final String Sms_body="text";
	public static final String Date="date";
	
	public static final String Read_f="read_flag";
	
	public static final String T_SentItems="Sent_Items";
	public static final String Ds="Delivered_Status";
	
	public static final String T_Drafts="Drafts";
	//create inbox table//
	public static final String Table_inbox =
			"Create table " + T_Inbox + " ( " + Number + " INTEGER, " + Sms_body + " TEXT, " 
	+ Date + " String, " + Read_f + " Integer, " +Serial_no+ " INTEGER PRIMARY KEY AUTOINCREMENT) ";

	
	// create Sent items table//
	public static final String Table_sentitems =
			"Create table " + T_SentItems + " ( " + Number + " INTEGER, " + Sms_body + " TEXT, " 
	+ Date + " String,  " +Serial_no+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +Ds+ " INTEGER) ";
	
	//create Drafts table//
	public static final String Table_drafts=
	"Create table " + T_Drafts + " ( " + Sms_body + " TEXT, " + Date + " String, "  +Serial_no+ " INTEGER PRIMARY KEY AUTOINCREMENT) ";
	
	public DBHelper(Context context) {
		super(context,DB_name, null, Version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create Table
	
       db.execSQL(Table_inbox);
       db.execSQL(Table_sentitems);
       db.execSQL(Table_drafts);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		

	}

}
