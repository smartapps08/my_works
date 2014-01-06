package com.smartapps.photographr;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartapps.lazylist.LazyAdapter;
import com.smartapps.lazylist.Photo;
import com.smartapps.lazylist.PullToRefreshListView;
import com.smartapps.lazylist.PullToRefreshListView.OnRefreshListener;

public class SuggestionListFragment extends Fragment {
	public static final String TAG = "list";
	// private Button clear;

	

	private ProgressDialog pd;

	// for data
	private ArrayList<Photo> photoList;
	private ArrayList<String> imageList;
	private LazyAdapter adapter;
	private PullToRefreshListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_list, container, false);
		list = (PullToRefreshListView) v.findViewById(R.id.listview);

		list.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
//				pd = ProgressDialog.show(getActivity(), "",
//						"Fetching Search Results...");
//				new RequestThread().start();

			}
		});
//		pd = ProgressDialog.show(getActivity(), "",
//				"Fetching Search Results...");
//		new RequestThread().start();

		return v;
	}

	
}
