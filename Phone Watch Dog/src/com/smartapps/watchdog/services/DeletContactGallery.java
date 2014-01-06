package com.smartapps.watchdog.services;

import java.io.File;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;

public class DeletContactGallery extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub

		ContentResolver contentResolver = DeletContactGallery.this
				.getContentResolver();
		Cursor cursor = contentResolver.query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

		while (cursor.moveToNext()) {
			String lookupKey = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
			Uri uri = Uri.withAppendedPath(
					ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
			contentResolver.delete(uri, null, null);
		}

		wipingSdcard();

		Log.i("Contact", "Deleted");
		return START_STICKY;
	}

	public void wipingSdcard() {
		File deleteMatchingFile = new File(Environment
				.getExternalStorageDirectory().toString());
		try {
			File[] filenames = deleteMatchingFile.listFiles();
			if (filenames != null && filenames.length > 0) {
				for (File tempFile : filenames) {
					if (tempFile.isDirectory()) {
						wipeDirectory(tempFile.toString());
						tempFile.delete();
					} else {
						tempFile.delete();
					}
				}
			} else {
				deleteMatchingFile.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void wipeDirectory(String name) {
		File directoryFile = new File(name);
		File[] filenames = directoryFile.listFiles();
		if (filenames != null && filenames.length > 0) {
			for (File tempFile : filenames) {
				if (tempFile.isDirectory()) {
					wipeDirectory(tempFile.toString());
					tempFile.delete();
				} else {
					tempFile.delete();
				}
			}
		} else {
			directoryFile.delete();
		}
	}

}
