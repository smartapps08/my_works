package me.iftekhar.sms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	
	private DBHelper dbhelper;
	private SQLiteDatabase db;
	private Context context;
	
	public static final String Serial_no="serial";
	public static final String Number="number";
	public static final String Sms_body="text";
	public static final String Date="date";
	public static final String Time="time";
	public static final String Read_f="read_flag";
	
	
	public static final String Ds="Delivered_Status";
	
	
	 
	public DBAdapter(Context context)
	{
		
		
		this.context = context;
		
		dbhelper=new DBHelper(context);
		
		
	}
	
	
	public void open() 
	
	{
		
		
		
	db=dbhelper.getWritableDatabase();
	
	
	 }
	
	
	public void close() 
	{
		
		
		db.close();
		if(db!=null)
		{
			db=null;
			
		}
	}
	
	
	
	//for Inbox////
public long insertInbox(Sms sms)
	{
		
	//long number, String text,String date, int read_flag,int serial_no
		
	ContentValues values = new ContentValues();
		
		values.put(DBHelper.Number, sms.getNumber());
		values.put(DBHelper.Sms_body, sms.getText());
		values.put(DBHelper.Read_f, 0);
		values.put(DBHelper.Date,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		//values.put(DBHelper.Serial_no,"");
		long insert_inbox=db.insert(DBHelper.T_Inbox, null, values);
	
		return insert_inbox;
	}
	
	public ArrayList<Sms>getAllinbox()
	{
		
		
		
		ArrayList<Sms> allSms = new ArrayList<Sms>();

		// projection
		String[] columns = { DBHelper.Number, DBHelper.Sms_body,
				DBHelper.Read_f, DBHelper.Date,DBHelper.Serial_no};
				 
		String selection = null;
		String[] selectionArgs = null;

		Cursor cursor = db.query(DBHelper.T_Inbox, columns, selection,
				selectionArgs, null, null, null);
		// columns=null for select *
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				
				
				long number = Long.parseLong(cursor.getString(cursor
						.getColumnIndex(DBHelper.Number)));
				String text = cursor.getString(cursor
						.getColumnIndex(DBHelper.Sms_body));
				int read_f = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(DBHelper.Read_f)));
				String date = cursor.getString(cursor
						.getColumnIndex(DBHelper.Date));
				int serial=Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(DBHelper.Serial_no)));
						
				
				Sms s=new Sms(number,text,date,read_f,serial);
				allSms.add(s);

				cursor.moveToNext();
			}
		}
		cursor.close();

		return allSms;
		
		
	}
	
	//for sent Items///
	public long insertSent(Sms sms)
	{
		
	
		
	ContentValues values = new ContentValues();
		
		values.put(DBHelper.Number, sms.getNumber());
		values.put(DBHelper.Sms_body, sms.getText());
		values.put(DBHelper.Ds,"Delivered" );
		values.put(DBHelper.Date,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		
		long insert_sent=db.insert(DBHelper.T_SentItems, null, values);
	
		return insert_sent;
	}
	
	
	
	public ArrayList<Sms>getAllsent()
	{
		
		
		ArrayList<Sms> allSms = new ArrayList<Sms>();

		// projection
		String[] columns = { DBHelper.Number, DBHelper.Sms_body,
				DBHelper.Ds, DBHelper.Date};
				 
		String selection = null;
		String[] selectionArgs = null;

		Cursor cursor = db.query(DBHelper.T_SentItems, columns, selection,
				selectionArgs, null, null, null);
	
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				Long number = Long.parseLong(cursor.getString(cursor
						.getColumnIndex(DBHelper.Number)));
				String text = cursor.getString(cursor
						.getColumnIndex(DBHelper.Sms_body));
				String ds = cursor.getString(cursor
						.getColumnIndex(DBHelper.Ds));
				String date = cursor.getString(cursor
						.getColumnIndex(DBHelper.Date));
				
				Sms s=new Sms(number,text,date,ds);
				allSms.add(s);

				cursor.moveToNext();
			}
		}
		cursor.close();

		return allSms;
		
		
	}
	
		
		
	
	
	
	
	//for drafts//
	public long insertDraft(Sms sms)
	{
		
	
		
	ContentValues values = new ContentValues();
		
		
		values.put(DBHelper.Sms_body, sms.getText());
		
		values.put(DBHelper.Date,new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		
		long insert_draft=db.insert(DBHelper.T_Drafts, null, values);
	
		return insert_draft;
	}
	
	public ArrayList<Sms>getAlldraft()
	{
		
		ArrayList<Sms> allSms = new ArrayList<Sms>();

		// projection
		String[] columns = {  DBHelper.Sms_body,
				 DBHelper.Date};
				 
		String selection = null;
		String[] selectionArgs = null;

		Cursor cursor = db.query(DBHelper.T_Drafts, columns, selection,
				selectionArgs, null, null, null);
		// columns=null for select *
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				
				String text = cursor.getString(cursor
						.getColumnIndex(DBHelper.Sms_body));
				
				String date = cursor.getString(cursor
						.getColumnIndex(DBHelper.Date));
				
				Sms s=new Sms(text,date);
				allSms.add(s);

				cursor.moveToNext();
			}
		}
		cursor.close();

		return allSms;
	
		
}
	
public void delete_r(int serial)
	
	{
		
		String sql="delete from " + DBHelper.T_Inbox+ " where " +DBHelper.Serial_no+ " = " +serial;
		
		db.execSQL(sql);
		
	}
	
	public void update_r(int serial)
	
	{
		
		String sql="update " + DBHelper.T_Inbox+ " set " +DBHelper.Read_f+ " =1 where " +DBHelper.Serial_no+ " = " +serial;
		
		db.execSQL(sql);
		
	}
	public int check()
	{
		String count = "SELECT count(*) FROM inbox where read_flag=0";
	    Cursor mcursor = db.rawQuery(count, null);
	    mcursor.moveToFirst();
	    int icount = mcursor.getInt(0);
	    return icount;
		
	}
}