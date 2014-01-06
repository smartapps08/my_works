package bd.org.basis.onlinelibrary;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btnShow;
	private EditText etTitle, etAuthor, etISBN, etCategory, etPrice, etKeyword;
	private Button btnAdd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnShow = (Button) findViewById(R.id.btnListAllBooks);
		etTitle = (EditText) findViewById(R.id.etTitle);
		etAuthor = (EditText) findViewById(R.id.etAuthor);
		etISBN = (EditText) findViewById(R.id.etIsbn);
		etCategory = (EditText) findViewById(R.id.etCategory);
		etPrice = (EditText) findViewById(R.id.etPrice);
		etKeyword = (EditText) findViewById(R.id.etKeyword);

		btnAdd = (Button) findViewById(R.id.btnAddBook);

		btnShow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, BookListActivity.class);
				startActivity(i);

			}
		});

		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// post
				String title = etTitle.getText().toString();
				String author = etAuthor.getText().toString();
				String isbn = etISBN.getText().toString();
				String category = etCategory.getText().toString();
				String price = etPrice.getText().toString();

				Book book = new Book(title, author, isbn, category, Double
						.parseDouble(price));
				new PostThread(book).start();
			}
		});
	}

	class PostThread extends Thread {
		private Book book;

		public PostThread(Book b) {
			this.book = b;
		}

		public void run() {
			// send post request
			DefaultHttpClient client = new DefaultHttpClient();
			HttpPost postReq = new HttpPost(
					"http://10.0.2.2/online_library/addbook.php");

			try {
				// set parameters

				ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
				params.add(new BasicNameValuePair("title", this.book.getTitle()));
				params.add(new BasicNameValuePair("author", this.book
						.getAuthorName()));
				params.add(new BasicNameValuePair("isbn", this.book.getIsbn()));
				params.add(new BasicNameValuePair("category", this.book
						.getCategory()));
				params.add(new BasicNameValuePair("price", this.book.getPrice()
						+ ""));
				postReq.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse response = client.execute(postReq);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();
					String jsonStr = EntityUtils.toString(entity);
					JSONObject jsonObj = new JSONObject(jsonStr);
					String success = jsonObj.getString("success");
					if (success.equals("1")) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(0);
					}

				} else {
					handler.sendEmptyMessage(0);
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(0);
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				handler.sendEmptyMessage(0);
				e.printStackTrace();
			} catch (JSONException e) {
				handler.sendEmptyMessage(0);
				e.printStackTrace();
			}

			handler.sendEmptyMessage(0);
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				Toast.makeText(getApplicationContext(), "Insert successful",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Insert failed",
						Toast.LENGTH_LONG).show();
			}
		}
	};

}
