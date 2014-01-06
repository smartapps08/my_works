package com.smartapp.priyo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartapp.entities.Configuration;
import com.smartapp.entities.Maintenance;

public class Tab1Fragment extends Fragment {
	Configuration config;
	File configfile, mfile;
	Maintenance maintain;
	String fullMS = null, prt = null;
	TextView maininfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.maintenance, container, false);
		TableLayout carinfo = (TableLayout) rootView.findViewById(R.id.carinfo);
		TableLayout nocarinfo = (TableLayout) rootView
				.findViewById(R.id.nocarinfo);

		try {

			configfile = new File(Environment.getExternalStorageDirectory(),
					"myconfig.txt");
			BufferedReader br = new BufferedReader(new FileReader(configfile));
			String line;
			StringBuilder text = new StringBuilder();
			while ((line = br.readLine()) != null) {
				text.append(line);
				text.append('\n');
			}

			if (text.length() == 0) {
				carinfo.setVisibility(View.GONE);
				nocarinfo.setVisibility(View.VISIBLE);
			} else {

				nocarinfo.setVisibility(View.GONE);
				carinfo.setVisibility(View.VISIBLE);
				TextView ncars = (TextView) rootView.findViewById(R.id.ncars);
				ncars.setText("You have 1 Car");
				configJSONRead(text.toString());

				TextView vsid = (TextView) rootView.findViewById(R.id.vsid);
				vsid.setText(config.VehicleConfigurationID);
			}

			br.close();
		} catch (FileNotFoundException npe) {
			carinfo.setVisibility(View.GONE);
			nocarinfo.setVisibility(View.VISIBLE);
		} catch (IOException e) {
			e.printStackTrace();
			carinfo.setVisibility(View.GONE);
			nocarinfo.setVisibility(View.VISIBLE);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NullPointerException npe) {
			carinfo.setVisibility(View.GONE);
			nocarinfo.setVisibility(View.VISIBLE);
		}

		Button bfinfo = (Button) rootView.findViewById(R.id.bfinfo);
		bfinfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(
						getActivity(),
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
			}
		});

		maininfo = (TextView) rootView.findViewById(R.id.maininfo);
		if (config != null) {
			new GetPerformanceTask().execute(config.VehicleConfigurationID);
		}

		Button bdp = (Button) rootView.findViewById(R.id.bdp);
		bdp.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new GetPartsDetail().execute("Parts",
						config.VehicleConfigurationID, maintain.ApplicationID,
						maintain.ContentCategoryID);
			}
		});

		return rootView;
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

	public void mainJSONRead(String minfo) throws JSONException {

		JSONObject respJson = new JSONObject(minfo);
		JSONObject bodyObject = respJson.getJSONObject("Body");
		JSONArray msA = bodyObject.getJSONArray("MaintenanceSchedules");

		JSONObject m = msA.getJSONObject(0);
		JSONObject mewt = m.getJSONObject("EWTInfo");
		maintain = new Maintenance(m.getString("MaintenanceScheduleID"),
				m.getString("FrequencyDescription"),
				m.getString("IntervalMonth"), m.getString("IntervalMile"),
				mewt.getString("BaseLaborTime"),
				mewt.getString("LaborTimeInterval"),
				m.getString("ApplicationID"), m.getString("ContentCategoryID"),
				m.getString("LiteralName"), m.getString("SystemID"),
				m.getString("SystemName"), m.getString("Action"));
	}

	public class GetPerformanceTask extends AsyncTask<String, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "", "Fetching Data");
		}

		@Override
		protected Void doInBackground(String... param) {
			Log.e("IDS", param[0]);
			// String url1 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[0] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";
			// String url2 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[1] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";

			String url;

			url = "http://autoAPI.hearst.com/Information/Content/Of/MaintenanceSchedules/WithVehicleSystem/ID/"
					+ param[0] + "?api_key=aegyqdyzstg92e6kh82vbf4t";
			Log.e("MaintenanceSchedules WithVehicleSystem", url);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(url);
			getReq.setHeader("Accept", "application/json"); // "Accept":
															// "application/json","X-Originating-Ip":
															// "35.0.112.42"
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					fullMS = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", fullMS);

					mainJSONRead(fullMS);

					// SD Card
					mfile = new File(Environment.getExternalStorageDirectory(),
							"mymaintenance.txt");
					byte[] data = fullMS.getBytes();
					FileOutputStream fos;
					fos = new FileOutputStream(mfile);
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

			maininfo.setText(maintain.Action + " " + maintain.SystemName
					+ "System " + maintain.FrequencyDescription + " "
					+ maintain.IntervalMonth + " month after running"
					+ maintain.IntervalMile + " miles which takes "
					+ maintain.BaseLaborTime + " " + maintain.LaborTimeInterval);

		}
	}

	public class GetPartsDetail extends AsyncTask<String, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "", "Fetching Data");
		}

		@Override
		protected Void doInBackground(String... param) {
			Log.e("IDS", param[0]);
			// String url1 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[0] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";
			// String url2 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[1] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";

			String url;

			url = "http://autoAPI.hearst.com/Information/Content/Of/Parts/WithVehicleSystem/ID/"
					+ param[1]
					+ "/RelatedTo/EstimatedWorkTime/"
					+ param[2]
					+ "/" + param[3] + "?api_key=aegyqdyzstg92e6kh82vbf4t";
			Log.e("Parts", url);

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(url);
			getReq.setHeader("Accept", "application/json"); // "Accept":
															// "application/json","X-Originating-Ip":
															// "35.0.112.42"
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					prt = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", prt);
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			pd.dismiss();
			try {

				JSONObject respJson = new JSONObject(prt);
				JSONObject bodyObject = respJson.getJSONObject("Body");
				JSONArray parts = bodyObject.getJSONArray("Parts");

				JSONObject part = parts.getJSONObject(0);
				JSONObject maker = part.getJSONObject("Manufacturer");
				JSONObject country = part.getJSONObject("Country");
				Toast.makeText(
						getActivity(),
						"Name : " + part.getString("LiteralName")
								+ "\nPart of : " + part.getString("SystemName")
								+ "\nOEM Number : "
								+ part.getString("OEMPartNumber")
								+ "\nSerial : "
								+ part.getString("SerialNumber")
								+ "\nPrice : $ " + part.getString("Price")
								+ "\nQuantity : " + part.getString("Quantity")
								+ "\nManufacturer : " + maker.getString("Name")
								+ "\nCountry : " + country.getString("Name"),
						Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(getActivity(),
						"Parts Details and Price are Unavailable",
						Toast.LENGTH_LONG).show();

			}
		}
	}

}
