package bd.org.basis.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private TextView txtResult;
	private EditText etTitle, etAuthor, etISBN, etCategory, etPrice, etKeyword;
	private Button btnAdd, btnSearch, btnList;

	private DBAdapter dbAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		dbAdapter = new DBAdapter(this);
		txtResult = (TextView) findViewById(R.id.txtResult);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etAuthor = (EditText) findViewById(R.id.etAuthor);
		etISBN = (EditText) findViewById(R.id.etIsbn);
		etCategory = (EditText) findViewById(R.id.etCategory);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etKeyword = (EditText) findViewById(R.id.etKeyword);

		btnAdd = (Button) findViewById(R.id.btnAddBook);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnList = (Button) findViewById(R.id.btnListAllBooks);
		btnAdd.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
		btnList.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// open database
		dbAdapter.open();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// close database
		dbAdapter.close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddBook:
			// insert a book
			String title = etTitle.getText().toString();
			String author = etAuthor.getText().toString();
			String isbn = etISBN.getText().toString();
			String category = etCategory.getText().toString();
			String price = etPrice.getText().toString();

			Book book = new Book(title, author, isbn, category,
					Double.parseDouble(price));
			long inserted = dbAdapter.insertBook(book);
			if (inserted < 0) {
				Toast.makeText(getApplicationContext(), "Insert failed",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Insert successful",
						Toast.LENGTH_LONG).show();
			}

			break;
		case R.id.btnSearch:

			break;
		case R.id.btnListAllBooks:
			Intent i=new Intent(this, BookListActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			break;
		default:
			break;
		}
	}

}
