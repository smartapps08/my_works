package bd.org.basis.todolistcp;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class ToDoContentProvider extends ContentProvider {
	public static final Uri CONTENT_URI = Uri
			.parse("content://bd.org.basis.todoprovider/todoitems");

	public static final int ALLROWS = 1;
	public static final int SINGLE_ROW = 2;

	public static final String DATABASE_NAME = "todoDatabase.db";
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_TABLE = "todoItemTable";
	public static final String KEY_ID = "_id";
	public static final String KEY_TASK = "task";
	public static final String KEY_CREATION_DATE = "creation_date";

	private static final UriMatcher uriMatcher;

	private MySQLiteOpenHelper dbHelper;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("bd.org.basis.todoprovider", "todoitems", ALLROWS);
		uriMatcher.addURI("bd.org.basis.todoprovider", "todoitems/#",
				SINGLE_ROW);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new MySQLiteOpenHelper(getContext());
		return true;
	}

	@Override
	public String getType(Uri uri) {
		if (uriMatcher.match(uri) == ALLROWS) {
			return "vnd.android.cursor.dir/vnd.bd.org.basis.todo";
		} else if (uriMatcher.match(uri) == SINGLE_ROW) {
			return "vnd.android.cursor.item/vnd.bd.org.basis.todo";
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = db.insert(DATABASE_TABLE, null, values);
		if (id >= 0) {
			Uri insertUri = ContentUris.withAppendedId(CONTENT_URI, id);
			return insertUri;
		} else {
			return null;
		}

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = null;

		if (uriMatcher.match(uri) == ALLROWS) {
			cursor = db.query(DATABASE_TABLE, projection, selection,
					selectionArgs, null, null, sortOrder);
		} else if (uriMatcher.match(uri) == SINGLE_ROW) {
			String rowId = uri.getLastPathSegment();
			cursor = db.query(DATABASE_TABLE, projection, KEY_ID + "=?",
					new String[] { rowId }, null, null, sortOrder);
		}

		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		return 0;
	}

	private static class MySQLiteOpenHelper extends SQLiteOpenHelper {

		public MySQLiteOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		// SQL statement to create a new database.
		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" + KEY_ID
				+ " integer primary key autoincrement, " + KEY_TASK
				+ " text not null, " + KEY_CREATION_DATE + " long);";

		// Called when no database exists in disk and the helper class needs
		// to create a new one.
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Log the version upgrade.
			Log.w("TaskDBAdapter", "Upgrading from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data");

			// The simplest case is to drop the old table and create a new one.
			db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(db);
		}

	}

}
