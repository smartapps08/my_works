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
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapp.db.DatabaseHelper;
import com.smartapp.entities.Make;
import com.smartapp.entities.Model;
import com.smartapp.entities.SubModel;
import com.smartapp.entities.SubModelGroup;

public class MainActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	PagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	private static final String GET_ALL_VEHICLE_REQ = "http://hearstcars.api.mashery.com/v1/api/vehicles/latest/json?api_key=aegyqdyzstg92e6kh82vbf4t";
	public ArrayList<Model> models;
	public ArrayList<Make> makes;
	public ArrayList<SubModel> subModels;
	public ArrayList<SubModelGroup> subModelGroups;

	SharedPreferences pref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(),
				this);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});
		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.myabs_layout, null);
		TextView ABStitle = (TextView) v.findViewById(R.id.myabs_title);
		ABStitle.setText("Automotivated");
		actionBar.setCustomView(v);

		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			if (i == 0) {
				actionBar.addTab(actionBar.newTab().setTabListener(this));
				LayoutInflater inflater = LayoutInflater.from(this);
				View customView = inflater.inflate(R.layout.tab_title, null);
				TextView titleTV = (TextView) customView
						.findViewById(R.id.action_custom_title);
				titleTV.setText("My Cars");
				actionBar.getTabAt(i).setCustomView(customView);
			} else if (i == 1) {
				actionBar.addTab(actionBar.newTab().setTabListener(this));
				LayoutInflater inflater = LayoutInflater.from(this);
				View customView = inflater.inflate(R.layout.tab_title, null);
				TextView titleTV = (TextView) customView
						.findViewById(R.id.action_custom_title);

				titleTV.setText("Buy Cars");
				actionBar.getTabAt(i).setCustomView(customView);
			} else if (i == 2) {
				actionBar.addTab(actionBar.newTab().setTabListener(this));
				LayoutInflater inflater = LayoutInflater.from(this);
				View customView = inflater.inflate(R.layout.tab_title, null);
				TextView titleTV = (TextView) customView
						.findViewById(R.id.action_custom_title);
				titleTV.setText("Explore");
				actionBar.getTabAt(i).setCustomView(customView);
			}
			// actionBar.addTab(actionBar.newTab().setText(s).setTabListener(this));
		}
		pref = getSharedPreferences("data", Context.MODE_PRIVATE);
		boolean isDataAvailable = pref.getBoolean("data", false);
		if (!isDataAvailable) {
			if (isInternetAvailable()) {
				new GetAllVehicleTask().execute();
			} else {
				Toast.makeText(getApplicationContext(),
						"Internet not available", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case android.R.id.home:
			finish();
			break;
		case R.id.menu_add:
			startActivity(new Intent(this, AddCarActivity.class));
			break;

		default:
			return super.onOptionsItemSelected(item);
		}
		return false;
	}

	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab,
			android.support.v4.app.FragmentTransaction ft) {
	}

	private class GetAllVehicleTask extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(MainActivity.this, "",
					"Fetching vehicle data...");
		}

		@Override
		protected Void doInBackground(Void... params) {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(GET_ALL_VEHICLE_REQ);

			try {
				HttpResponse response = client.execute(getReq);
				if (response != null
						&& response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String jsonString = EntityUtils.toString(response
							.getEntity());
					JSONObject vehiclesdata = new JSONObject(jsonString);
					JSONObject vehicles = vehiclesdata
							.getJSONObject("vehicles");
					JSONArray makesArray = vehicles.getJSONArray("makes");
					DatabaseHelper dbHelper = new DatabaseHelper(
							MainActivity.this);
					makes = new ArrayList<Make>();
					for (int i = 0; i < makesArray.length(); i++) {
						JSONObject makeObject = makesArray.getJSONObject(i);
						String id = makeObject.getString("id");
						String name = makeObject.getString("name");
						Make make = new Make(id, name);
						Log.e("MAKE", make.toString());
						makes.add(make);
						dbHelper.insertMake(make);
					}

					JSONArray modelsArray = vehicles.getJSONArray("models");
					models = new ArrayList<Model>();
					for (int i = 0; i < modelsArray.length(); i++) {
						JSONObject modelObject = modelsArray.getJSONObject(i);
						String id = modelObject.getString("id");
						String name = modelObject.getString("name");
						String make = modelObject.getString("make");
						Model model = new Model(id, name, make);
						Log.e("MODEL", model.toString());
						models.add(model);
						dbHelper.insertModel(model);
					}

					JSONArray submodelsArray = vehicles
							.getJSONArray("submodels");
					subModels = new ArrayList<SubModel>();
					for (int i = 0; i < submodelsArray.length(); i++) {
						JSONObject submodelObject = submodelsArray
								.getJSONObject(i);
						String id = submodelObject.getString("id");
						String name = submodelObject.getString("name");
						String model = submodelObject.getString("model");
						String image = submodelObject.getString("image");
						String make = submodelObject.getString("make");
						String year = submodelObject.getString("year");
						String subModelGroup = submodelObject
								.getString("submodel_group");
						SubModel subModel = new SubModel(id, name, image,
								model, make, year, subModelGroup);
						Log.e("SUBMODEL", subModel.toString());
						subModels.add(subModel);
						dbHelper.insertSubmodel(subModel);
					}

					JSONArray subModelGroupArray = vehicles
							.getJSONArray("submodelGroups");
					subModelGroups = new ArrayList<SubModelGroup>();
					for (int i = 0; i < subModelGroupArray.length(); i++) {
						JSONObject submodelGroupObject = subModelGroupArray
								.getJSONObject(i);
						String id = submodelGroupObject.getString("id");
						String name = submodelGroupObject.getString("name");
						String model = submodelGroupObject.getString("model");
						SubModelGroup smg = new SubModelGroup(id, name, model);
						Log.e("SUBMODELGROUP", smg.toString());
						subModelGroups.add(smg);
						dbHelper.insertSubModelGroup(smg);
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
			pd.dismiss();
			SharedPreferences.Editor edit = pref.edit();
			edit.putBoolean("data", true);
			edit.commit();
		}

	}

	private boolean isInternetAvailable() {
		ConnectivityManager cManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cManager.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isAvailable() && netInfo.isConnected()) {
			return true;
		}
		return false;
	}
}
