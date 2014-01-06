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
import android.widget.TextView;

import com.smartapps.db.DBHelper;
import com.smartapps.lazylist.Photo;
import com.smartapps.staggeredview.StaggeredAdapter;
import com.smartapps.staggeredview.StaggeredGridView;
import com.smartapps.util.Constants;
import com.smartapps.util.Installation;

public class SuggestionGridFragment extends Fragment {
	public static final String TAG = "grid";

	private ProgressDialog pd;

	StaggeredGridView gridView;
	StaggeredAdapter adapter;

	TextView txtRefresh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_grid, container, false);

		txtRefresh = (TextView) v.findViewById(R.id.txtRefresh);
		gridView = (StaggeredGridView) v.findViewById(R.id.staggeredGridView1);
		gridView.setDrawSelectorOnTop(true);
		gridView.setColumnCount(2);
		int margin = getResources().getDimensionPixelSize(
				R.dimen.activity_horizontal_margin);

		gridView.setItemMargin(margin); // set the GridView margin

		gridView.setPadding(margin, 0, margin, 0); // have the margin on the

		// if (HomeActivity.dbHelper == null) {
		// HomeActivity.dbHelper = new DBHelper(getActivity());
		//
		// }
		// HomeActivity.photoList = HomeActivity.dbHelper.getAllPhoto();
		//
		// if (HomeActivity.photoList == null
		// || HomeActivity.photoList.size() == 0) {
		// txtRefresh.setVisibility(View.VISIBLE);
		// gridView.setVisibility(View.GONE);
		// } else {
		// txtRefresh.setVisibility(View.GONE);
		// adapter = new StaggeredAdapter(getActivity(),
		// R.layout.row_staggered_demo, HomeActivity.photoList);
//		 gridView.setAdapter(adapter);
//		 gridView.setVisibility(View.VISIBLE);
		// }
		return v;
	}

}
