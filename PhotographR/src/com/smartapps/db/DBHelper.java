package com.smartapps.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartapps.lazylist.Photo;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DBNAME = "photographr";
	public static final String ID = "_id";
	public static final String PHOTO_ID = "photo_id";
	public static final String OWNER = "owner";
	public static final String SECRET = "secret";
	public static final String SERVER = "server";
	public static final String FARM = "farm";
	public static final String TITLE = "title";
	public static final String ISPUBLIC = "ispublic";
	public static final String ISFRIEND = "isfriend";
	public static final String ISFAMILY = "isfamily";
	public static final String IMG_URL = "imgurl";
	public static final String LATITUDE = "lat";
	public static final String LONGITUDE = "lng";

	public static final String TABLE_NAME = "photos";
	public static final String TABLE_SQL = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ PHOTO_ID + " TEXT, " + OWNER + " TEXT, " + SECRET + " TEXT, "
			+ SERVER + " TEXT, " + FARM + " TEXT, " + TITLE + " TEXT, "
			+ ISPUBLIC + " TEXT, " + ISFRIEND + " TEXT, " + ISFAMILY
			+ " TEXT, " + IMG_URL + " TEXT, " + LATITUDE + " DOUBLE, "
			+ LONGITUDE + " DOUBLE" + ")";

	public DBHelper(Context context) {
		super(context, DBNAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	public ArrayList<Photo> getAllPhoto() {
		ArrayList<Photo> allPhoto = new ArrayList<Photo>();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String pId = cursor.getString(cursor.getColumnIndex(PHOTO_ID));
				String owner = cursor.getString(cursor.getColumnIndex(OWNER));
				String secret = cursor.getString(cursor.getColumnIndex(SECRET));
				String server = cursor.getString(cursor.getColumnIndex(SERVER));
				String farm = cursor.getString(cursor.getColumnIndex(FARM));
				String title = cursor.getString(cursor.getColumnIndex(TITLE));
				String isPublic = cursor.getString(cursor
						.getColumnIndex(ISPUBLIC));
				String isFriend = cursor.getString(cursor
						.getColumnIndex(ISFRIEND));
				String isFamily = cursor.getString(cursor
						.getColumnIndex(ISFAMILY));
				String imgUrl = cursor
						.getString(cursor.getColumnIndex(IMG_URL));
				double lat = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
				double lng = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
				Photo photo = new Photo(pId, owner, secret, server, farm,
						title, isPublic, isFriend, isFamily, imgUrl, lat, lng);
				allPhoto.add(photo);
				cursor.moveToNext();
			}
		}

		cursor.close();
		db.close();

		return allPhoto;
	}

	public ArrayList<String> getAllPhotoURL() {
		ArrayList<String> allPhoto = new ArrayList<String>();
		SQLiteDatabase db = getReadableDatabase();

		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {

				String imgUrl = cursor
						.getString(cursor.getColumnIndex(IMG_URL));

				allPhoto.add(imgUrl);
				cursor.moveToNext();
			}
		}

		cursor.close();
		db.close();

		return allPhoto;
	}

	public long insertPhoto(Photo p) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(PHOTO_ID, p.getId());
		values.put(OWNER, p.getOwner());
		values.put(SECRET, p.getSecret());
		values.put(SERVER, p.getServer());
		values.put(FARM, p.getFarm());
		values.put(TITLE, p.getTitle());
		values.put(ISPUBLIC, p.getIspublic());
		values.put(ISFRIEND, p.getIsfriend());
		values.put(ISFAMILY, p.getIsfamily());
		values.put(IMG_URL, p.getImgUrl());
		values.put(LATITUDE, p.getLat());
		values.put(LONGITUDE, p.getLng());
		long inserted = db.insert(TABLE_NAME, null, values);
		db.close();
		return inserted;
	}

	public int deleteAll() {
		SQLiteDatabase db = getWritableDatabase();
		int deleted = db.delete(TABLE_NAME, null, null);
		return deleted;
	}

}
