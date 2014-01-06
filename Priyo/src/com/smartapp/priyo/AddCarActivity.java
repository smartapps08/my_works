package com.smartapp.priyo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapp.entities.Configuration;

public class AddCarActivity extends SherlockActivity {
	Configuration config;
	File configfile;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_car);
		// Show the Up button in the action bar.
		setupActionBar();

		Button vr = (Button) findViewById(R.id.bvinr);
		vr.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText vin = (EditText) findViewById(R.id.vin);
				String vinValue = vin.getText().toString();
/*
				configfile = new File(
						Environment.getExternalStorageDirectory(),
						"myconfig.txt");
				if (configfile.exists()) {
					configfile.delete();
				}*/
				new GetPerformanceTask().execute("ByVIN", vinValue);

			}
		});

		Button ymmer = (Button) findViewById(R.id.bymmer);
		ymmer.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText y = (EditText) findViewById(R.id.year);
				String yValue = y.getText().toString();

				EditText m = (EditText) findViewById(R.id.make);
				String mValue = m.getText().toString();

				EditText mod = (EditText) findViewById(R.id.model);
				String modValue = mod.getText().toString();

				EditText submod = (EditText) findViewById(R.id.submodel);
				String submodValue = submod.getText().toString();

				EditText e = (EditText) findViewById(R.id.engine);
				String eValue = e.getText().toString();
/*
				configfile = new File(
						Environment.getExternalStorageDirectory(),
						"myconfig.txt");
				if (configfile.exists()) {
					configfile.delete();
				}*/
				new GetPerformanceTask().execute("ByYMME", yValue, mValue,
						modValue, submodValue, eValue);

			}
		});

	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.add_car, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void configJSONRead(String respStr) throws JSONException {

		JSONObject respJson = new JSONObject(respStr);
		JSONObject bodyObject = respJson.getJSONObject("Body");
		JSONArray conArr = bodyObject.getJSONArray("Configurations");
		JSONObject con = conArr.getJSONObject(0);
		config = new Configuration(con.getString("VehicleConfigurationID"),
				con.getString("BaseVehicleID"), con.getString("VehicleID"),
				con.getString("EngineID"), con.getString("SpringFrontType"),
				con.getString("SpringRearType"), con.getString("BedType"),
				con.getString("BedLength"), con.getString("BedLengthMetric"),
				con.getString("BodyDoorCount"), con.getString("BodyType"),
				con.getString("BrakeSystem"), con.getString("BrakeFrontType"),
				con.getString("BrakeRearType"), con.getString("BrakeABS"),
				con.getString("WheelBase"), con.getString("WheelBaseMetric"),
				con.getString("DriveType"), con.getString("SteeringType"),
				con.getString("SteeringSystem"),
				con.getString("TransmissionType"),
				con.getString("TransmissionControlType"),
				con.getString("TransmissionManufacturerCode"),
				con.getString("TransmissionElectronicControl"),
				con.getString("TransmissionSpeed"));
	}

	public class GetPerformanceTask extends AsyncTask<String, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(AddCarActivity.this, "", "Fetching Data");
		}

		@Override
		protected Void doInBackground(String... param) {
			Log.e("IDS", param[0]);
			String url;

			if (param[0].equals("ByVIN")) {
				url = "http://autoAPI.hearst.com/Information/Vehicle/Search/ByVIN/"
						+ param[1] + "?api_key=aegyqdyzstg92e6kh82vbf4t";
				Log.e("VIN", url);
			} else {
				url = "http://autoAPI.hearst.com/Information/Vehicle/Search/ByYMME/"
						+ param[1]
						+ "/"
						+ param[2]
						+ "/"
						+ param[3]
						+ "/"
						+ param[4]
						+ "/"
						+ param[5]
						+ "?api_key=aegyqdyzstg92e6kh82vbf4t";
				Log.e("YMME", url);
			}
			url=url.replace(" ", "%20");

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(url);
			getReq.setHeader("Accept", "application/json"); // "Accept":
															// "application/json","X-Originating-Ip":
															// "35.0.112.42"
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String respStr = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", respStr);

					configJSONRead(respStr);

					// SD Card
					configfile = new File(
							Environment.getExternalStorageDirectory(),
							"myconfig.txt");
					byte[] data = respStr.getBytes();
					FileOutputStream fos;
					fos = new FileOutputStream(configfile);
					fos.write(data);
					fos.flush();
					fos.close();
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
			if (config != null && config.EngineID != null) {
				Toast.makeText(
						getApplicationContext(),
						"VehicleConfigurationID : "
								+ config.VehicleConfigurationID + "\n"
								+ "BaseVehicleID : " + config.BaseVehicleID
								+ "\n" + "VehicleID : " + config.VehicleID
								+ "\n" + "EngineID : " + config.EngineID + "\n"
								+ "SpringFrontType : " + config.SpringFrontType
								+ "\n" + "SpringRearType : "
								+ config.SpringRearType + "\n" + "BedType : "
								+ config.BedType + "\n" + "BedLength : "
								+ config.BedLength + "\n"
								+ "BedLengthMetric : " + config.BedLengthMetric
								+ "\n" + "BodyDoorCount : "
								+ config.BodyDoorCount + "\n" + "BodyType : "
								+ config.BodyType + "\n" + "BrakeSystem : "
								+ config.BrakeSystem + "\n"
								+ "BrakeFrontType : " + config.BrakeFrontType
								+ "\n" + "BrakeRearType : "
								+ config.BrakeRearType + "\n" + "BrakeABS : "
								+ config.BrakeABS + "\n" + "WheelBase : "
								+ config.WheelBase + "\n"
								+ "WheelBaseMetric : " + config.WheelBaseMetric
								+ "\n" + "DriveType : " + config.DriveType
								+ "\n" + "SteeringType : "
								+ config.SteeringType + "\n"
								+ "SteeringSystem : " + config.SteeringSystem
								+ "\n" + "TransmissionType : "
								+ config.TransmissionType + "\n"
								+ "TransmissionControlType : "
								+ config.TransmissionControlType + "\n"
								+ "TransmissionManufacturerCode : "
								+ config.TransmissionManufacturerCode + "\n"
								+ "TransmissionElectronicControl : "
								+ config.TransmissionElectronicControl + "\n"
								+ "TransmissionSpeed : "
								+ config.TransmissionSpeed, Toast.LENGTH_LONG)
						.show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Enter Valid Information", Toast.LENGTH_LONG).show();
			}

		}
	}

}
