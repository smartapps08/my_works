package com.smartapps.newsappdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsDetailsFragment extends Fragment {

	private static ArrayList<NewsItem> newsItems;
	private int position;
	ImageView imageView;

	public static Fragment newInstance()// (ArrayList<NewsItem> newsItem, int
										// pos)
	{
		// NewsDetailsFragment fragment = new NewsDetailsFragment();
		// newsItems = newsItem;
		// position = pos;
		// return fragment;
		return new NewsDetailsFragment();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// if ((savedInstanceState != null) &&
		// savedInstanceState.containsKey(KEY_CONTENT)) {
		// mContent = savedInstanceState.getString(KEY_CONTENT);
		// }
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.newsItems = (ArrayList<NewsItem>) getArguments().getSerializable(
				"items");
		this.position=getArguments().getInt("position");
		View rootView = inflater.inflate(R.layout.news_details, container,
				false);
		TextView tvHeading = (TextView) rootView.findViewById(R.id.tvHeading);
		TextView tvdescription = (TextView) rootView
				.findViewById(R.id.textView);
		imageView = (ImageView) rootView.findViewById(R.id.imageView);

		tvHeading.setText(newsItems.get(position).text + "");
		tvdescription.setText("Position = " + position);

		// Create an object for subclass of AsyncTask
		setImageTask task = new setImageTask();
		// Execute the task
		task.execute(new String[] { newsItems.get(position).url });

		return rootView;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// outState.putString(KEY_CONTENT, mContent);
	}

	private class setImageTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			Bitmap map = null;
			for (String url : urls) {
				map = downloadImage(url);
			}
			return map;
		}

		// Sets the Bitmap returned by doInBackground
		@Override
		protected void onPostExecute(Bitmap result) {
			imageView.setImageBitmap(result);
		}

		// Creates Bitmap from InputStream and returns it
		private Bitmap downloadImage(String url) {
			Bitmap bitmap = null;
			InputStream stream = null;
			BitmapFactory.Options bmOptions = new BitmapFactory.Options();
			bmOptions.inSampleSize = 1;

			try {
				stream = getHttpConnection(url);
				bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
				stream.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return bitmap;
		}

		// Makes HttpURLConnection and returns InputStream
		private InputStream getHttpConnection(String urlString)
				throws IOException {
			InputStream stream = null;
			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();

			try {
				HttpURLConnection httpConnection = (HttpURLConnection) connection;
				httpConnection.setRequestMethod("GET");
				httpConnection.connect();

				if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
					stream = httpConnection.getInputStream();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return stream;
		}
	}

}
