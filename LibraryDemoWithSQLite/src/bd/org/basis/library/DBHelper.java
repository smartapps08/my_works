package bd.org.basis.library;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DBHelper extends SQLiteOpenHelper {
	public static final String DB_NAME = "library";
	public static final int VERSION = 1;

	public static final String TABLE_NAME = "books";
	public static final String ID_FIELD = "_id";
	public static final String TITLE_FIELD = "title";
	public static final String AUTHOR_FIELD = "author";
	public static final String ISBN_FIELD = "isbn";
	public static final String CATEGORY_FIELD = "category";
	public static final String PRICE_FIELD = "price";

	public static final String TABLE_SQL = "Create table " + TABLE_NAME + " ("
			+ ID_FIELD + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE_FIELD
			+ " TEXT, " + AUTHOR_FIELD + " TEXT, " + ISBN_FIELD + " TEXT, "
			+ CATEGORY_FIELD + " TEXT, " + PRICE_FIELD + " NUMBER)";

	public DBHelper(Context context) {
		super(context, DB_NAME, null, VERSION);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables
		db.execSQL(TABLE_SQL);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
