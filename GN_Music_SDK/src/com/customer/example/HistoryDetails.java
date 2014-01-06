/* Gracenote Android Music SDK Sample Application
 *
 * Copyright (C) 2010 Gracenote, Inc. All Rights Reserved.
 */
package com.customer.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * This class is used to render history detail into the screen.
 * 
 */
public final class HistoryDetails extends Activity implements OnClickListener {
	CheckBox deletecheckBox;
	DatabaseAdapter db = null;
	List<String> deleteId = new ArrayList<String>();
	public static final int DELETE_OPTION_ID = 0;
	public static final int DELETE_OPTION_ALL = 1;
	TextView trackText, artistText, albumText, dateText;
	ImageView coverArtImage;
	HistoryListAdapter adapter;
	ListView historylist;
	int i = 0;
	Cursor cursor;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hostory_layout);
		historylist = (ListView) findViewById(R.id.HistoryList);
		historylist.setVisibility(View.VISIBLE);
		db = new DatabaseAdapter(HistoryDetails.this);
		getHistory();
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, DELETE_OPTION_ID, 0, "Delete");
		menu.add(0, DELETE_OPTION_ALL,0, "Clear all");
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_OPTION_ID:
			//System.out.println("delete click");
			int count = deleteId.size();
			db.open();
			for (int i = 0; i < count; i++) {
				db.deleterow(deleteId.get(i));
			}
			db.close();
			getHistory();
			break;
		case DELETE_OPTION_ALL:
			db.open();
				db.deleteAll();
			db.close();
			getHistory();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * This class is used as a database Adapter to render a history.
	 */
	private class HistoryListAdapter extends CursorAdapter {
		private final LayoutInflater mInflater;

		public HistoryListAdapter(Context context, Cursor cursor) {
			super(context, cursor);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public long getItemId(int position) {
			return super.getItemId(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return super.getView(position, convertView, parent);
		}

		@Override
		public void bindView(View view, Context context, final Cursor cursor) {
			coverArtImage = (ImageView) view.findViewById(R.id.CoverArtImage);

			byte[] theByteArray = cursor.getBlob(4);
			if (theByteArray != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(theByteArray, 0,
						theByteArray.length);
				coverArtImage.setImageBitmap(bitmap);
			}

			trackText = (TextView) view.findViewById(R.id.TrackTextView);
			trackText.setText(cursor.getString(2));

			artistText = (TextView) view.findViewById(R.id.ArtistTextView);
			artistText.setText(cursor.getString(3));

			albumText = (TextView) view.findViewById(R.id.AlbumTextView);
			albumText.setText(cursor.getString(1));

			dateText = (TextView) view.findViewById(R.id.DateTimeTextView);
			dateText.setText(currentTimeZonedate(cursor.getString(5)));

			deletecheckBox.setTag(cursor.getString(0));
			deletecheckBox.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					CheckBox chk = (CheckBox) v;
					if (chk.isChecked()) {
						deleteId.add(chk.getTag().toString());
						//System.out.println("Chaked......" + chk.getTag());
					} else {
						deleteId.remove(chk.getTag().toString());
						//System.out.println("UnChaked......" + chk.getTag());
					}
				}
			});
		}

		@Override
		public View newView(Context context, final Cursor cursor,
				ViewGroup parent) {
			final View view = mInflater.inflate(R.layout.histrory_row, parent,
					false);
			view.setOnClickListener(HistoryDetails.this);
			view.setTag(cursor.getString(0));
			deletecheckBox = (CheckBox) view.findViewById(R.id.DeleteCheckbox);
			return view;
		}
	}
	
	public void getHistory() {
		//System.out.println("count i :"+i++);
		//System.out.println("object created");
		db.open();
		cursor = db.getcursor();
		//System.out.println("cursor count :" + cursor.getCount());
		historylist.setItemsCanFocus(false);
		historylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		adapter = new HistoryListAdapter(HistoryDetails.this, cursor);
		historylist.setAdapter(adapter);
	}
	public String currentTimeZonedate(String date) {
		String currentDate = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - HH:mm:ss");
		SimpleDateFormat actFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		SimpleDateFormat dateFormatTimeZome = new SimpleDateFormat("Z");
		dateFormat.setTimeZone(TimeZone.getDefault());
		TimeZone _timeZone = TimeZone.getDefault();
		long gmtRawOffset = _timeZone.getRawOffset();

		try {
			Date dateObj = actFormat.parse(date);
			currentDate = dateFormat.format(dateObj);
			String timezone = dateFormatTimeZome.format(dateObj);
			Date convertedDate = new Date(dateObj.getTime() + gmtRawOffset);
			currentDate = actFormat.format(convertedDate);
		} catch (Exception e) {
			Log.e("GN", "Exception :" + e.getMessage());
		}
		return currentDate;
	}
	public void onClick(View v) {
		v.getTag();
		//System.out.println("list clicked a......." + v.getTag());
		Intent i = new Intent(HistoryDetails.this, HistoryLocation.class);
		startActivity(i);
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.i("TAG", "on pause");
		if (cursor != null)
			cursor.close();
		if (db != null)
			db.close();
	}
	@Override
	protected void onRestart(){
		super.onRestart();
		getHistory();
	}
}