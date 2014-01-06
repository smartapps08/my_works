package com.smartapp.priyo;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapp.db.DatabaseHelper;
import com.smartapp.entities.Make;
import com.smartapp.entities.Model;
import com.smartapp.entities.SubModel;

public class ReviewActivity extends SherlockActivity {
	private SubModel selected;
	private TextView txtName, txtAnalysis1, txtAnalysis2;
	private WebView wv1, wv2;
	private Button btnAnalyze;
	private String url;
	private DatabaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_review);

		txtName = (TextView) findViewById(R.id.txtVehicleName);
		// txtAnalysis1 = (TextView) findViewById(R.id.txtAnalysis1);
		// txtAnalysis2 = (TextView) findViewById(R.id.txtAnalysis2);
		wv1 = (WebView) findViewById(R.id.wvReview1);
		// wv1 = (WebView) findViewById(R.id.wvReview2);
		// Show the Up button in the action bar.
		setupActionBar();
		selected = (SubModel) getIntent().getSerializableExtra("model");
		txtName.setText(selected.getName());
		dbHelper = new DatabaseHelper(this);
		Model m = dbHelper.getModelById(selected.getModel());
		Make mk = dbHelper.getMakeById(selected.getMake());
		if (m != null && mk != null) {
			Log.e("MAKE", mk.getName());
			Log.e("Model", m.getName());

			url = "http://nafsadh.com/carticle/view/" + mk.getName() + "/"
					+ m.getName() + "/" + selected.getYear();
			new GetReviewTask().execute(url);
		}
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.review, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			// NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class GetReviewTask extends AsyncTask<String, Void, String> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(ReviewActivity.this, "",
					"Fetching review...");
		}

		@Override
		protected String doInBackground(String... params) {
			String urlStr = params[0];
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(urlStr);
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String respStr = EntityUtils.toString(resp.getEntity());
					return respStr;

				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (result != null) {
				wv1.loadData(result, "text/html", "UTF-8");
				wv1.setWebViewClient(new ReviewWebViewClient());
			} else {
				Toast.makeText(getApplicationContext(), "Reviews Not Found...",
						Toast.LENGTH_LONG).show();
			}

		}
	}

	public class ReviewWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

}
