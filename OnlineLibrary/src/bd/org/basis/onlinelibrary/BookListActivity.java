package bd.org.basis.onlinelibrary;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

public class BookListActivity extends Activity {

	// data source
	private ArrayList<Book> allBooks;
	private BooksAdapter adapter;
	private ListView lvBooks;
	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);
		lvBooks = (ListView) findViewById(R.id.lvBooks);

		// start a NON-UI thread to send request

		if (isConnected()) {
			//
			GetTask task = new GetTask(this, handler);
			task.execute();
		} else {
			Toast.makeText(getApplicationContext(), "Internet Not available",
					Toast.LENGTH_LONG).show();
		}

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// you're back to UI thread
			
			Bundle b = msg.getData();
			ArrayList<Book> books = (ArrayList<Book>) b
					.getSerializable("books");
			if (books != null) {
				adapter = new BooksAdapter(BookListActivity.this, books);
				lvBooks.setAdapter(adapter);
			} else {
				Toast.makeText(getApplicationContext(), "Failed",
						Toast.LENGTH_LONG).show();
			}

		}
	};

	private boolean isConnected() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cManager.getActiveNetworkInfo();

		if (netInfo != null) {
			if (netInfo.isAvailable() && netInfo.isConnected()) {
				return true;
			} else {
				return false;
			}
		}
		return false;

	}

}
