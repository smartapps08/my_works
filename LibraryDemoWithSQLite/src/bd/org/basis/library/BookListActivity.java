package bd.org.basis.library;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class BookListActivity extends Activity {
	// data source
	private ArrayList<Book> allBooks;
	private DBAdapter dbAdapter;
	
	// adapter
	private BooksAdapter adapter;
	
	//view
	private ListView lvBooks;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book_list);
		
		dbAdapter=new DBAdapter(this);
		dbAdapter.open();
		allBooks=dbAdapter.getAllBooks();
		dbAdapter.close();
		
		lvBooks=(ListView)findViewById(R.id.lvBooks);
		adapter=new BooksAdapter(this, allBooks);
		lvBooks.setAdapter(adapter);
		
		lvBooks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listview, View v, int position,
					long id) {
				Toast.makeText(getApplicationContext(), "Selected Book: "+position, Toast.LENGTH_LONG).show();
			}
		});
	}

	
}
