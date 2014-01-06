package com.smartapps.newsappdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Tab1Fragment extends Fragment {
	
	private static final int SUCCESS = 1;
	private static final int FAILURE = 0;
	private ArrayList<NewsItem> NewsItems;
	private CustomAdapter adapter;
	private ListView newsListView;
	private ProgressBar progressBar;
	String imgUrl = "";
	ImageView newsImageView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
		
//		TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
//		dummyTextView.setText("test tab");
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		newsListView = (ListView) rootView.findViewById(R.id.listView);
		NewsItems = new ArrayList<NewsItem>();
		adapter = new CustomAdapter(getActivity(), R.layout.row, R.id.textView, NewsItems);
		
		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> l, View v, int pos, long arg3) {
				// TODO Auto-generated method stub
                Intent intent = new Intent();
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        		
//        		intent.putExtra("accountNo", readings.get(pos).getAccountNo());
//        		intent.putExtra("status", readings.get(pos).getSmsResponse());
        		Intent i=new Intent(getActivity(), NewsDetails.class);
        		i.putExtra("items", NewsItems);
        		i.putExtra("position", pos);
        		startActivity(i);
			}
		});
		
		new GetNewsThread().start();
		return rootView;
	}
	
	class CustomAdapter extends ArrayAdapter<NewsItem> {
		ArrayList<NewsItem> lists;
		Context context;

		public CustomAdapter(Context context, int resource,
				int textViewResourceId, ArrayList<NewsItem> objects) {
			super(context, resource, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
			this.lists = objects;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView;
			if (convertView == null) {
				LayoutInflater inflater = getActivity().getLayoutInflater();
				v = inflater.inflate(R.layout.row, parent, false);
			}

			newsImageView = (ImageView) v.findViewById(R.id.imageView);
			TextView newsTextView = (TextView) v.findViewById(R.id.textView);
			
			NewsItem news = (NewsItem) this.lists.get(position);

			// Create an object for subclass of AsyncTask
			setImageTask task = new setImageTask();
	        // Execute the task
	        task.execute(new String[] { news.getUrl() });
	        
//			URL url;
//			try {
//				url = new URL(news.getUrl());
//				Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//				newsImageView.setImageBitmap(bmp);
//			} catch (MalformedURLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
//			try {
//		        InputStream in = new java.net.URL(news.getUrl()).openStream();
//		        Bitmap bmp = BitmapFactory.decodeStream(in);
//		        newsImageView.setImageBitmap(bmp);
//		    } catch (Exception e) {
//		        Log.e("Error", e.getMessage());
//		        e.printStackTrace();
//		    }
			
//			BitmapFactory.Options bmOptions;
//		    bmOptions = new BitmapFactory.Options();
//		    bmOptions.inSampleSize = 1;
//		    Bitmap bm = LoadImage(news.getUrl(), bmOptions);
//		    newsImageView.setImageBitmap(bm);
			
			newsTextView.setText(news.getText());
			
			return v;
		}
		

	}
	
	class GetNewsThread extends Thread {

	public void run() {
		DefaultHttpClient client = new DefaultHttpClient();
        HttpGet getReq = new HttpGet("http://api.espn.com/v1/fantasy/football/news?apikey=g6eaqauu2rq7xxpqgjrgta4n");
        try {
            HttpResponse response = client.execute(getReq);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String jsonStr = EntityUtils.toString(response.getEntity());
                Log.d("RESPONSE", jsonStr);
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("headlines");
                for(int i = 0; i < 9; i++){
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if(obj != null){
                        String headline = obj.getString("headline");
                        Log.i("headline", headline+"");
//						mWidgetItems.add(new WidgetItem(headline));
//                        remoteViews.setTextViewText(R.id.textView, headline+"");
                        JSONArray imageArray = obj.getJSONArray("images");
							for(int j = 0; j < imageArray.length(); j++){
                        	JSONObject imgobj = imageArray.getJSONObject(j);
                        	if(imgobj != null){
                        		imgUrl = imgobj.getString("url");
//                        		URL url = new URL(imgUrl);
//                        		Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
////                        		remoteViews.setBitmap(R.id.ivHeadingImage, null, bmp);
//                        		remoteViews.setImageViewBitmap(R.id.ivHeadingImage, bmp);
//                        		NewsItems.add(new NewsItem(headline, imgUrl));
                        		Log.i("Image url", imgUrl+"");
                        	}
                        }
							NewsItems.add(new NewsItem(headline, imgUrl));
                        handler.sendEmptyMessage(SUCCESS); 
                    } else {
                    	handler.sendEmptyMessage(FAILURE);
                    }
                }
            } else {
                handler.sendEmptyMessage(FAILURE);
            }
        } catch (ClientProtocolException e) {
            handler.sendEmptyMessage(FAILURE);
            e.printStackTrace();
        } catch (IOException e) {
            handler.sendEmptyMessage(FAILURE);
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
	 }
	}
	
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
//			pd.dismiss();
			if (msg.what == SUCCESS) {
				newsListView.setAdapter(adapter);
				progressBar.setVisibility(View.INVISIBLE);
			} else {
//				Toast.makeText(getApplicationContext(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//				Intent homeIntent = new Intent(CSRActivity.this, HomeActivity.class);
//				startActivity(homeIntent);
			}
		}
	};
	
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
            newsImageView.setImageBitmap(result);
        }
 
        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;
 
            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
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