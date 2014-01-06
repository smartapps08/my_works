package bd.org.basis.androidlib;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import bd.org.basis.entities.Book;
import bd.org.basis.entities.Library;

public class MainActivity extends Activity implements OnClickListener {
	private TextView txtResult;
	private EditText etTitle, etAuthor, etISBN, etCategory, etPrice, etKeyword;
	private Button btnAdd, btnSearch, btnList;

	private Library library;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		library = new Library();

		txtResult = (TextView) findViewById(R.id.txtResult);
		etTitle=(EditText)findViewById(R.id.etTitle);
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnAddBook:
			addNewBook();
			break;

		case R.id.btnSearch:
			String keyword = etKeyword.getText().toString().trim();
			ArrayList<Book> searchResult = library.search(keyword);
			txtResult.setText("");
			for (Book book : searchResult) {
				txtResult.append(book.toString() + "\n");
			}

			break;
		case R.id.btnListAllBooks:

			ArrayList<Book> result = library.getAllBooks();
			txtResult.setText("");
			for (Book book : result) {
				txtResult.append(book.toString() + "\n");
			}
			break;
		default:
			break;
		}

	}

	private void addNewBook() {
		String title = etTitle.getText().toString();
		String author = etAuthor.getText().toString();
		String isbn = etISBN.getText().toString();
		String category = etCategory.getText().toString();
		String price = etPrice.getText().toString();
		// validation
		if (title.equals("")) {
			// alert
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			alert.setTitle("Warning");
			alert.setMessage("Please input title...");
			alert.setIcon(R.drawable.ic_launcher);
			alert.show();

		} else {
			Book book = new Book(title, author, isbn, category,
					Double.parseDouble(price));
			this.library.addBook(book);
		}

	}

}
