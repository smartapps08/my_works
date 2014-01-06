package bd.org.basis.onlinelibrary;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

class GetTask extends AsyncTask<Void, Integer, Void> {
	private static final String REQ_URL = "http://10.0.2.2/online_library/getallbooks.php";
	private String msg;
	private ProgressDialog pd;
	private ArrayList<Book> allBooks;

	private Context context;
	private Handler handler;
	public GetTask(Context context, Handler handler)
	{
		this.context=context;
		this.handler=handler;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		pd = ProgressDialog
				.show(context, "", "Fetching data");
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet getReq = new HttpGet(REQ_URL + "?keyword=" + "test");
		try {
			HttpResponse response = client.execute(getReq);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();
				String jsonStr = EntityUtils.toString(entity);
				Log.e(getClass().getSimpleName(), jsonStr);
				// parse
				JSONObject jsonObj = new JSONObject(jsonStr);
				String success = jsonObj.getString("success");
				if (success.equals("1")) {
					JSONArray bookArray = jsonObj.getJSONArray("books");
					allBooks = new ArrayList<Book>();
					for (int i = 0; i < bookArray.length(); i++) {
						JSONObject bookObj = bookArray.getJSONObject(i);
						int id = Integer.parseInt(bookObj.getString("id"));
						String title = bookObj.getString("title");
						String author = bookObj.getString("author");
						String isbn = bookObj.getString("isbn");
						String category = bookObj.getString("category");
						String priceStr = bookObj.getString("price");
						double price = Double.parseDouble(priceStr);
						Book b = new Book(title, author, isbn, category,
								price);
						allBooks.add(b);
					}

				} else {
					msg = jsonObj.getString("message");

				}

			} else {
			}
		} catch (ClientProtocolException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (IOException e) {
			msg = e.getMessage();
			e.printStackTrace();
		} catch (JSONException e) {
			msg = e.getMessage();
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		pd.dismiss();
		Message msg=Message.obtain();
		Bundle b=new Bundle();
		b.putSerializable("books", allBooks);
		msg.setData(b);
		handler.sendMessage(msg);
	}

}