package com.smartapp.priyo;

import java.io.IOException;
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

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.smartapp.entities.Article;

public class Tab3Fragment extends Fragment {
	private ListView lvArticles;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> articlesTitles;
	private ArrayList<Article> articles;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_explore, container,
				false);
		lvArticles = (ListView) rootView.findViewById(R.id.listView1);
		new GetArticlesTask().execute();
		return rootView;
	}

	class GetArticlesTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
//			pd = ProgressDialog.show(getActivity(), "", "Fetching articles");
		}

		@Override
		protected Void doInBackground(Void... params) {
			String url = "http://hearstcars.api.mashery.com/v1/api/content/index/0/10/desc/json?api_key=aegyqdyzstg92e6kh82vbf4t";

			Log.e("url2", url);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(url);
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String respStr = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", respStr);
					JSONObject respJson = new JSONObject(respStr);
					JSONObject contents = respJson.getJSONObject("content");
					JSONArray articlesArray = contents.getJSONArray("articles");
					articles = new ArrayList<Article>();
					articlesTitles = new ArrayList<String>();
					for (int i = 0; i < articlesArray.length(); i++) {
						JSONObject art = articlesArray.getJSONObject(i);
						String title = art.getString("fullTitle");
						String link = art.getString("content_url");
						Article a = new Article(title, link);
						articles.add(a);
						articlesTitles.add(title);
						Log.e("---------------", "No: " + i);
					}
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
//			pd.dismiss();
			adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, articlesTitles);

			if (adapter != null) {
				if (lvArticles != null) {
					lvArticles.setAdapter(adapter);
					lvArticles
							.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> parent,
										View v, int position, long id) {
									Intent browserIntent = new Intent(
											Intent.ACTION_VIEW, Uri
													.parse(articles.get(
															position)
															.getContentUrl()));
									startActivity(browserIntent);
								}
							});
				} else {
					Log.e("VIEW", "ListView is null");
				}
			} else {
				Log.e("ADAPTER", "Adapter is null");
			}
		}

	}

}
