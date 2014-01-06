package com.smartapps.photographr;

import java.io.IOException;
import java.io.InputStream;
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
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapps.lazylist.Photo;
import com.smartapps.util.Constants;
import com.smartapps.util.ImageHelper;

public class PhotoDetailsActivity extends SherlockActivity {
	private Bitmap bm;
	private ImageView imageView;
	private ListView listview;
	private TextView txtTitle;
	private ArrayList<String> comments;
	private SpannableArrayAdapter adapter;
	Photo selectedPhoto;

	public static final int FROM_MAP = 0;
	public static final int FROM_GRID = 1;
	private int from;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_details);
		// Show the Up button in the action bar.
		from = getIntent().getIntExtra("from", 1);

		setupActionBar();
		imageView = (ImageView) findViewById(R.id.imageView);
		txtTitle = (TextView) findViewById(R.id.txtTitle);
		listview = (ListView) findViewById(R.id.listComments);
		selectedPhoto = HomeActivity.photoList.get(getIntent().getIntExtra(
				"selection", 0));
		txtTitle.setText(selectedPhoto.getTitle());
		new FetchPhotoWithCommentsTask().execute(selectedPhoto);

		// Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(
		// R.drawable.test1)).getBitmap();
		// ImageHelper ih = new ImageHelper();
		// bm = ih.getRoundedCornerBitmap(bitmap, 30);
		// imageView.setImageBitmap(bm);
	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.photo_details, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (from == FROM_GRID) {
				NavUtils.navigateUpFromSameTask(this);
			} else if (from == FROM_MAP) {
				Intent i = new Intent(this, PhotoMapActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
			}
			return true;
		case R.id.action_map:
			Intent i = new Intent(this, SinglePhotoMapActivity.class);
			i.putExtra("selection", getIntent().getIntExtra("selection", 0));
			startActivity(i);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	class FetchPhotoWithCommentsTask extends AsyncTask<Photo, Void, Void> {
		boolean error = false;
		Bitmap photo1;
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(PhotoDetailsActivity.this, "",
					"Fetching comments...");
		}

		@Override
		protected Void doInBackground(Photo... params) {
			Photo p = params[0];
			String photoReq = Constants.COMMENTS_URL + "photoId=" + p.getId();
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(photoReq);
			try {
				HttpResponse response = client.execute(getReq);
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						String jsonStr = EntityUtils.toString(entity);
						Log.e("COMMENTS",
								jsonStr + " -----------" + p.getImgUrl());
						JSONArray commentsArray = new JSONArray(jsonStr);
						comments = new ArrayList<String>();
						for (int i = 0; i < commentsArray.length(); i++) {
							JSONObject commentsObj = commentsArray
									.getJSONObject(i);

							String comment = commentsObj.getString("comment");
							Spannable s = Spannable.Factory.getInstance()
									.newSpannable(comment);
							comments.add(comment);
						}

						photo1 = downloadBitmap(p.getImgUrl());

					} else {
						error = true;
					}
				}
			} catch (ClientProtocolException e) {
				error = true;
				e.printStackTrace();
			} catch (IOException e) {
				error = true;
				e.printStackTrace();
			} catch (JSONException e) {
				error = true;
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (!error) {
				imageView.setImageBitmap(ImageHelper.getRoundedCornerBitmap(
						photo1, 20));
				adapter = new SpannableArrayAdapter(PhotoDetailsActivity.this,
						R.layout.comments_row, comments);
				listview.setAdapter(adapter);
			}
		}

		private Bitmap downloadBitmap(String url) {
			// initilize the default HTTP client object
			final DefaultHttpClient client = new DefaultHttpClient();

			// forming a HttoGet request
			final HttpGet getRequest = new HttpGet(url);
			try {

				HttpResponse response = client.execute(getRequest);

				// check 200 OK for success
				final int statusCode = response.getStatusLine().getStatusCode();

				if (statusCode != HttpStatus.SC_OK) {
					Log.w("ImageDownloader", "Error " + statusCode
							+ " while retrieving bitmap from " + url);
					return null;

				}

				final HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream inputStream = null;
					try {
						// getting contents from the stream
						inputStream = entity.getContent();

						// decoding stream data back into image Bitmap that
						// android understands
						final Bitmap bitmap = BitmapFactory
								.decodeStream(inputStream);

						return bitmap;
					} finally {
						if (inputStream != null) {
							inputStream.close();
						}
						entity.consumeContent();
					}
				}
			} catch (Exception e) {
				// You Could provide a more explicit error message for
				// IOException
				getRequest.abort();
				Log.e("ImageDownloader", "Something went wrong while"
						+ " retrieving bitmap from " + url + e.toString());
			}

			return null;
		}
	}

	class SpannableArrayAdapter extends ArrayAdapter<String> {
		private ArrayList<String> items;

		public SpannableArrayAdapter(Context context, int textViewResourceId,
				ArrayList<String> objects) {
			super(context, textViewResourceId, objects);
			this.items = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				v = getLayoutInflater().inflate(R.layout.comments_row, parent,
						false);
				TextView txtComment = (TextView) v
						.findViewById(R.id.txtComments);
				String s = items.get(position);
				txtComment.setText(Html.fromHtml(s));
			}

			return v;
		}

	}

}
