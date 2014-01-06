package bd.org.basis.library;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {
	private DBHelper dbHelper;
	private SQLiteDatabase db;
	private Context context;

	public DBAdapter(Context context) {
		this.context = context;
		dbHelper = new DBHelper(context);
	}

	public void open() {
		db = dbHelper.getWritableDatabase();
	}

	public void close() {
		db.close();
		if (db != null) {
			db = null;
		}
	}

	public long insertBook(Book book) {
		ContentValues values = new ContentValues();
		values.put(DBHelper.TITLE_FIELD, book.getTitle());
		values.put(DBHelper.AUTHOR_FIELD, book.getAuthorName());
		values.put(DBHelper.ISBN_FIELD, book.getIsbn());
		values.put(DBHelper.CATEGORY_FIELD, book.getCategory());
		values.put(DBHelper.PRICE_FIELD, book.getPrice());

		long inserted = db.insert(DBHelper.TABLE_NAME, null, values);

		return inserted;
	}

	public ArrayList<Book> getAllBooks() {
		ArrayList<Book> allBooks = new ArrayList<Book>();

		// projection
		String[] columns = { DBHelper.TITLE_FIELD, DBHelper.AUTHOR_FIELD,
				DBHelper.ISBN_FIELD, DBHelper.CATEGORY_FIELD,
				DBHelper.PRICE_FIELD };
		String selection = null;// DBHelper.ID_FIELD+"=? AND "+DBHelper.AUTHOR_FIELD+"=?";
		String[] selectionArgs = null;// {""+5, "xyz"};

		Cursor cursor = db.query(DBHelper.TABLE_NAME, columns, selection,
				selectionArgs, null, null, null);
		// columns=null for select *
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String title = cursor.getString(cursor
						.getColumnIndex(DBHelper.TITLE_FIELD));
				String author = cursor.getString(cursor
						.getColumnIndex(DBHelper.AUTHOR_FIELD));
				String isbn = cursor.getString(cursor
						.getColumnIndex(DBHelper.ISBN_FIELD));
				String category = cursor.getString(cursor
						.getColumnIndex(DBHelper.CATEGORY_FIELD));
				double price = cursor.getDouble(cursor
						.getColumnIndex(DBHelper.PRICE_FIELD));
				Book b=new Book(title, author, isbn, category, price);
				allBooks.add(b);

				cursor.moveToNext();
			}
		}
		cursor.close();

		return allBooks;
	}

	public ArrayList<Book> search(String keyword) {
		return null;
	}

}
