/* Gracenote Android Music SDK Sample Application
 *
 * Copyright (C) 2010 Gracenote, Inc. All Rights Reserved.
 */
package com.customer.example;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.gracenote.mmid.MobileSDK.GNSearchResult;

/**
 * <p>
 * This class will work as a utility class to deal with database. It will be
 * responsible for database connection, insert a row into database, retrieve the
 * cursor for select and update operations.
 * 
 * 
 */
public final class DatabaseAdapter {
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public static final int MAX_COUNT = 1000;
	public static final String TAG = "Gracenote";

	
	public static final String MUSIC_HISTORY_TABLE = "search_history";
	public static final String MUSIC_HISTORY_RESPONSE_TABLE = "search_response";
	
	
	public static final String MUSIC_HISTORY_ID = "_id";
	public static final String MUSIC_HISTORY_SEARCH_ID = "search_id";
	public static final String MUSIC_HISTORY_ALBUM_TITLE = "album_title";
	public static final String MUSIC_HISTORY_ALBUM_TITLE_YOMI = "album_title_yomi";
	public static final String MUSIC_HISTORY_ARTIST = "artist";
	public static final String MUSIC_HISTORY_ARTIST_YOMI = "artist_yomi";
	public static final String MUSIC_HISTORY_ARTIST_BEST_YOMI = "artist_best_yumie";
	public static final String MUSIC_HISTORY_TRACK_TITLE = "track_title";
	public static final String MUSIC_HISTORY_TRACK_TITLE_YOMI = "track_title_yomi";
	public static final String MUSIC_HISTORY_COVERART_URL = "cover_art_url";
	public static final String MUSIC_HISTORY_COVERART_MIMETYPE = "cover_art_mimetype";
	public static final String MUSIC_HISTORY_COVERART_SIZE = "cover_art_size";
	public static final String MUSIC_HISTORY_ALBUMID = "album_id";
	public static final String MUSIC_HISTORY_ALBUM_TRACK_COUNT = "album_track_count";
	public static final String MUSIC_HISTORY_TRACK_NUMBER = "track_number";
	public static final String MUSIC_HISTORY_GENRE_ID = "genre_id";
	public static final String MUSIC_HISTORY_GENRE = "genre";
	public static final String MUSIC_HISTORY_ALBUM_LINK_DATA = "album_link_data";
	public static final String MUSIC_HISTORY_TRACK_LINK_DATA = "track_link_data";
	public static final String MUSIC_HISTORY_LATITUDE = "lat";
	public static final String MUSIC_HISTORY_LONGITUDE = "long";
	public static final String MUSIC_HISTORY_DATE = "date_time";
	public static final String MUSIC_HISTORY_COVERART_IMAGE = "coverart_image_data";
	public static final String MUSIC_HISTORY_FINGERPRINT = "fingerprint";
	public static final String COMMA = ",";
	
	public DatabaseAdapter(Context context) {
		this.context = context;
		DBHelper = new DatabaseHelper(this.context);
	}

	public DatabaseAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		DBHelper.close();
	}

	public int deleterow(String _id) {
		int result = 0;
		try {
			result = db.delete(MUSIC_HISTORY_TABLE, MUSIC_HISTORY_ID +" = ?",
					new String[] { _id });
			result = db.delete(MUSIC_HISTORY_RESPONSE_TABLE, MUSIC_HISTORY_ID +" = ?", new String[] { _id });
		} catch (SQLiteDiskIOException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteFullException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteConstraintException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteDatabaseCorruptException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (Exception exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		Log.i("GraceNote", "Transaction completed.");
		return result;
	}

	public int deleteAll() {
		int result = 0;
		try {
			result = db.delete(MUSIC_HISTORY_TABLE, null, null);
			result = db.delete(MUSIC_HISTORY_RESPONSE_TABLE, null, null);
		} catch (SQLiteDiskIOException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteFullException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteConstraintException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteDatabaseCorruptException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteException exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (Exception exception) {
			Log.e(TAG, "Failed to delete record - " + exception.getMessage());
			Toast.makeText(context,
					"Failed to delete record - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		Log.i("GraceNote", "Transaction completed.");
		return result;
	}

	public Cursor getcursor() {
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("select a."+MUSIC_HISTORY_ID + COMMA +
							"a."+MUSIC_HISTORY_ALBUM_TITLE + COMMA +
							"a."+MUSIC_HISTORY_TRACK_TITLE + COMMA +
							"a."+MUSIC_HISTORY_ARTIST + COMMA +
							"a."+MUSIC_HISTORY_COVERART_IMAGE + COMMA +
							"b."+MUSIC_HISTORY_DATE +" from " +
							MUSIC_HISTORY_RESPONSE_TABLE + " a" + COMMA +
							MUSIC_HISTORY_TABLE+ " b where a."+MUSIC_HISTORY_SEARCH_ID+" = b."+MUSIC_HISTORY_ID+";",
							null);

			cursor.deactivate();
		} catch (SQLiteDiskIOException exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteFullException exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteConstraintException exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteDatabaseCorruptException exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (SQLiteException exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		} catch (Exception exception) {
			Log.e(TAG, "Failed to get cursor " + exception.getMessage());
			Toast.makeText(context,
					"Failed to retrive cursur - " + exception.getMessage(),
					Toast.LENGTH_SHORT).show();
		}
		return cursor;
	}

	public void handleMaxDBRow() {
		Cursor countCursor = db.rawQuery(
				"select count(*) from "+MUSIC_HISTORY_TABLE+";", null);
		int result_responce = 0;
		int result_history = 0;
		countCursor.moveToFirst();
		int count = countCursor.getInt(0);
		countCursor.close();

		Log.i("GN_DATABASE", "count is :" + count);

		if (count > MAX_COUNT) {

			Cursor minIdCursor = db.rawQuery(
					"select min(_id) from "+MUSIC_HISTORY_TABLE+";", null);
			minIdCursor.moveToFirst();
			String min_Id = minIdCursor.getString(0);
			minIdCursor.close();

			result_responce = db.delete(MUSIC_HISTORY_RESPONSE_TABLE, MUSIC_HISTORY_ID +" = ?",
					new String[] { min_Id });
			result_history = db.delete(MUSIC_HISTORY_TABLE, MUSIC_HISTORY_ID +" = ?",
					new String[] { min_Id });

			Log.i("GN_DATABASE_METADATA_DELETE", String.valueOf(result_responce));
			Log.i("GN_DATABASE_SEARCH_HISTORY_DELETE", String.valueOf(result_history));
		}
	}

	/**
	 * 
	 * @return Cursor - A pointer to a database which has location values for
	 *         records.
	 * 
	 */
	public Cursor getLocations() {
		Cursor cursor = null;
		try {
			cursor = db
					.rawQuery(
							" select a."+MUSIC_HISTORY_ID+ COMMA +
							"a."+MUSIC_HISTORY_ARTIST + COMMA +
							"a."+MUSIC_HISTORY_ALBUM_TITLE +COMMA +
							"b."+MUSIC_HISTORY_LATITUDE + COMMA +
							"b."+MUSIC_HISTORY_LONGITUDE + 
							" from "+ MUSIC_HISTORY_RESPONSE_TABLE +" a " +COMMA +
							MUSIC_HISTORY_TABLE + " b where a."+MUSIC_HISTORY_SEARCH_ID +"= b."+MUSIC_HISTORY_ID+";",
							null);
		} catch (Exception exception) {
			Log.e(TAG, "Failed to get location - " + exception.getMessage());
		}
		return cursor;
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public long insertChanges(GNSearchResult row, Location location) {
		ContentValues initialValues = null;
		// to insert into search_history
		initialValues = new ContentValues();
		TimeZone timeZone = TimeZone.getDefault();
		long gmtRawOffset = timeZone.getRawOffset();
		Date currentTime = new Date();
		Date convertedDate = new Date(currentTime.getTime() - gmtRawOffset);
		String time = dateFormat.format(convertedDate);
		if (row.getFingerprintData() != null)
			initialValues.put(MUSIC_HISTORY_FINGERPRINT, row.getFingerprintData());

		initialValues.put(MUSIC_HISTORY_DATE, time);
		try {
			if (location == null) {
				Log.i("GraceNote","Location could not retrive, inserting into database with blank location");
			} else {
				initialValues.put(MUSIC_HISTORY_LATITUDE, location.getLatitude());
				initialValues.put(MUSIC_HISTORY_LONGITUDE, location.getLongitude());
				Log.i("Lat", Double.toString(location.getLatitude()));
				Log.i("Long", Double.toString(location.getLongitude()));
			}
			long masterRowId = db.insert(MUSIC_HISTORY_TABLE, null, initialValues);

			Log.i("GraceNote", "Row inserted." + masterRowId);
			if (masterRowId == -1) {
				Toast.makeText(context, "Failed to insert into search history",
						Toast.LENGTH_SHORT);
				return 0;
			}
			for (int count = 0; count < row.getResponses().length; count++) {
				initialValues = new ContentValues();
				initialValues.put(MUSIC_HISTORY_SEARCH_ID, masterRowId);
				if (row.getResponses()[count].getAlbumTitle() != null)
					initialValues.put(MUSIC_HISTORY_ALBUM_TITLE, row.getResponses()[count]
							.getAlbumTitle());
				if (row.getResponses()[count].getAlbumTitleYomi() != null)
					initialValues.put(MUSIC_HISTORY_ALBUM_TITLE_YOMI,
							row.getResponses()[count].getAlbumTitleYomi());
				if (row.getResponses()[count].getArtist() != null)
					initialValues.put(MUSIC_HISTORY_ARTIST, row.getResponses()[count]
							.getArtist());
				if (row.getResponses()[count].getArtistYomi() != null)
					initialValues.put(MUSIC_HISTORY_ARTIST_YOMI, row.getResponses()[count]
							.getArtistYomi());
				if (row.getResponses()[count].getArtistBetsumei() != null)
					initialValues.put(MUSIC_HISTORY_ARTIST_BEST_YOMI,
							row.getResponses()[count].getArtistBetsumei());
				if (row.getResponses()[count].getTrackTitle() != null)
					initialValues.put(MUSIC_HISTORY_TRACK_TITLE, row.getResponses()[count]
							.getTrackTitle());
				if (row.getResponses()[count].getTrackTitleYomi() != null)
					initialValues.put(MUSIC_HISTORY_TRACK_TITLE_YOMI,
							row.getResponses()[count].getTrackTitleYomi());

				if (row.getResponses()[count].getCoverArt() != null) {
					initialValues.put(MUSIC_HISTORY_COVERART_IMAGE,
							row.getResponses()[count].getCoverArt().getData());
					if (row.getResponses()[count].getCoverArt().getMimeType() != null)
						initialValues.put(MUSIC_HISTORY_COVERART_MIMETYPE, row.getResponses()[count]
						        .getCoverArt().getMimeType());
					if (row.getResponses()[count].getCoverArt().getSize() != null)
						initialValues.put(MUSIC_HISTORY_COVERART_SIZE,
								row.getResponses()[count].getCoverArt().getSize());
				}

				
				if (row.getResponses()[count].getAlbumId() != null)
					initialValues.put(MUSIC_HISTORY_ALBUMID, row.getResponses()[count]
							.getAlbumId());
				initialValues.put(MUSIC_HISTORY_ALBUM_TRACK_COUNT,
						row.getResponses()[count].getAlbumTrackCount());
				initialValues.put(MUSIC_HISTORY_TRACK_NUMBER, row.getResponses()[count]
						.getTrackNumber());

				try {
					if (row.getResponses()[count].getAlbumLinkData() != null)
						initialValues.put(MUSIC_HISTORY_ALBUM_LINK_DATA, getBytes(row
								.getResponses()[count].getAlbumLinkData()));
					if (row.getResponses()[count].getTrackLinkData() != null)
						initialValues.put(MUSIC_HISTORY_TRACK_LINK_DATA, getBytes(row
								.getResponses()[count].getTrackLinkData()));
				} catch (IOException exception) {
					Log.e(TAG, "Failed to insert record - "
							+ exception.getMessage());
					Toast.makeText(context,
							"Failed to insert one of search result",
							Toast.LENGTH_SHORT).show();
				}
				long result = db.insert(MUSIC_HISTORY_RESPONSE_TABLE, null, initialValues);
				if (result == -1) {
					Log.e(TAG, "Failed to insert one of search result");
					Toast.makeText(context,
							"Failed to insert one of search result",
							Toast.LENGTH_SHORT).show();
				}
				Log.i("GraceNote", "Row inserted.");
			}
			handleMaxDBRow();

		} catch (SQLiteDiskIOException exception) {
			Log.e(TAG, "Failed to insert record - " + exception.getMessage());
			Toast.makeText(
					context,
					"Failed to insert search result - "
							+ exception.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (SQLiteFullException exception) {
			Log.e(TAG, "Failed to insert record - " + exception.getMessage());
			Toast.makeText(
					context,
					"Failed to insert search result - "
							+ exception.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (SQLiteConstraintException exception) {
			Log.e(TAG, "Failed to insert record - " + exception.getMessage());
			Toast.makeText(
					context,
					"Failed to insert search result - "
							+ exception.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (SQLiteDatabaseCorruptException exception) {
			Log.e(TAG, "Failed to insert record - " + exception.getMessage());
			Toast.makeText(
					context,
					"Failed to insert search result - "
							+ exception.getMessage(), Toast.LENGTH_SHORT)
					.show();
		} catch (SQLiteException exception) {
			Log.e(TAG, "Failed to insert record - " + exception.getMessage());
			Toast.makeText(
					context,
					"Failed to insert search result - "
							+ exception.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
		return 0;

	}

	/**
	 * <p>
	 * It will create a database and tables.
	 * 
	 * 
	 */
	public class DatabaseHelper extends SQLiteOpenHelper {

		final static String CREATE_TABLE_SEARCH_HISTORY = "CREATE TABLE " + MUSIC_HISTORY_TABLE + "("+
													MUSIC_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT "+ COMMA +
													MUSIC_HISTORY_FINGERPRINT+ " TEXT "+ COMMA +
													MUSIC_HISTORY_DATE + " TEXT " + COMMA +
													MUSIC_HISTORY_LATITUDE +" TEXT " + COMMA +
													MUSIC_HISTORY_LONGITUDE + " TEXT" +
													")";
		final static String CREATE_TABLE_RESPONSES = "CREATE TABLE " + MUSIC_HISTORY_RESPONSE_TABLE +"(" +
													MUSIC_HISTORY_ID + " INTEGER PRIMARY KEY " + COMMA +
													MUSIC_HISTORY_SEARCH_ID + " INTEGER " + COMMA +
													MUSIC_HISTORY_ALBUM_TITLE + " TEXT " + COMMA +
													MUSIC_HISTORY_ALBUM_TITLE_YOMI + " TEXT "+ COMMA +
													MUSIC_HISTORY_ARTIST + " TEXT "+ COMMA + 
													MUSIC_HISTORY_ARTIST_YOMI + " TEXT "+ COMMA +
													MUSIC_HISTORY_ARTIST_BEST_YOMI + " TEXT "+ COMMA + 
													MUSIC_HISTORY_TRACK_TITLE + " TEXT "+ COMMA +
													MUSIC_HISTORY_TRACK_TITLE_YOMI + " TEXT " + COMMA +
													MUSIC_HISTORY_COVERART_IMAGE + " BLOB " + COMMA +
													MUSIC_HISTORY_COVERART_MIMETYPE + " TEXT " + COMMA +
													MUSIC_HISTORY_COVERART_SIZE + " TEXT " + COMMA +
													MUSIC_HISTORY_ALBUMID + " TEXT " + COMMA +
													MUSIC_HISTORY_ALBUM_TRACK_COUNT + " TEXT " + COMMA +
													MUSIC_HISTORY_TRACK_NUMBER + " TEXT " + COMMA + 
													MUSIC_HISTORY_GENRE_ID +" TEXT " + COMMA +
													MUSIC_HISTORY_GENRE + " TEXT "+ COMMA +
													MUSIC_HISTORY_ALBUM_LINK_DATA + " BLOB "+ COMMA + 
													MUSIC_HISTORY_TRACK_LINK_DATA + " BLOB " +
													")";
	
		
		final static String CREATE_INDEX_DATE = "CREATE INDEX date_time_index on "+MUSIC_HISTORY_TABLE + "("+MUSIC_HISTORY_DATE+")";

		DatabaseHelper(Context context) {
			// database name - history_track
			// database version - 1
			super(context, "history_track", null, 1);
			Log.i("GraceNote", "Database helper constroctor");
		}

		@Override
		public void onCreate(SQLiteDatabase db)
				throws SQLiteConstraintException, SQLiteDiskIOException,
				SQLiteException {

			db.execSQL(CREATE_TABLE_SEARCH_HISTORY);
			db.execSQL(CREATE_TABLE_RESPONSES);
			db.execSQL(CREATE_INDEX_DATE);
			Log.i("GraceNote", "Tables created...");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
				throws SQLiteConstraintException, SQLiteDiskIOException,
				SQLiteException {
			Log.i("GraceNote", "table upgraded");
			db.execSQL("DROP TABLE IF EXISTS "+MUSIC_HISTORY_RESPONSE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS "+MUSIC_HISTORY_TABLE);
			onCreate(db);
		}
	}

	/**
	 * This method will generate bytes for
	 * 
	 * @param obj
	 * @return
	 * @throws java.io.IOException
	 */
	public byte[] getBytes(Object obj) throws java.io.IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(obj);
		oos.flush();
		oos.close();
		bos.close();
		byte[] data = bos.toByteArray();
		return data;
	}
}
