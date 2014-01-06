package com.smartapp.priyo;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartapp.entities.Performance;
import com.smartapp.entities.SubModel;

public class Tab2Fragment extends Fragment {

	private Button btnChoose1, btnChoose2;
	private LinearLayout ll1, ll2, ll3, ll4;
	private ImageView img1, img2;
	private TextView txtCar1, txtCar2, txtPer1, txtPer2;
	private Button btnCompare, btnSimulate, btnReview1, btnReview2;
	private Performance p1, p2;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_buy_cars, container,
				false);
		btnChoose1 = (Button) rootView.findViewById(R.id.btnChoose1);
		btnChoose2 = (Button) rootView.findViewById(R.id.btnChoose2);
		ll1 = (LinearLayout) rootView.findViewById(R.id.ll1);
		ll2 = (LinearLayout) rootView.findViewById(R.id.ll2);
		ll3 = (LinearLayout) rootView.findViewById(R.id.ll3);
		ll4 = (LinearLayout) rootView.findViewById(R.id.ll4);
		img1 = (ImageView) rootView.findViewById(R.id.imgCar1);
		img2 = (ImageView) rootView.findViewById(R.id.imgCar2);
		txtCar1 = (TextView) rootView.findViewById(R.id.txtCar1);
		txtCar2 = (TextView) rootView.findViewById(R.id.txtCar2);
		txtPer1 = (TextView) rootView.findViewById(R.id.txtPer1);
		txtPer2 = (TextView) rootView.findViewById(R.id.txtPer2);
		btnCompare = (Button) rootView.findViewById(R.id.btnCompare);
		btnSimulate = (Button) rootView.findViewById(R.id.btnSimulation);
		btnReview1 = (Button) rootView.findViewById(R.id.btnReview1);
		btnReview2 = (Button) rootView.findViewById(R.id.btnReview2);

		btnChoose1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getActivity(),
						SearchActivity.class), 0);

			}
		});

		btnCompare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (sm1 != null && sm2 != null) {
					new GetPerformanceTask().execute(sm1.getId(), sm2.getId());
				}
			}
		});

		btnSimulate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), SimulateActivity.class);
				i.putExtra("speed1", p1.getQuarterMileSpeed());
				i.putExtra("time1", p1.getQuarterMileTime());
				i.putExtra("speed2", p2.getQuarterMileSpeed());
				i.putExtra("time2", p2.getQuarterMileTime());
				// i.putExtra("speed1", 10.6);
				// i.putExtra("time1", 10.6);
				// i.putExtra("speed2", 12.5);
				// i.putExtra("time2", 12.9);
				startActivityForResult(i, 3);
			}
		});

		btnChoose2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(getActivity(),
						SearchActivity.class), 1);
			}
		});

		btnReview1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ReviewActivity.class);
				// i.putExtra("speed1", p1.getQuarterMileSpeed());
				// i.putExtra("time1", p1.getQuarterMileTime());
				// i.putExtra("speed2", p2.getQuarterMileSpeed());
				// i.putExtra("time2", p2.getQuarterMileSpeed());
				i.putExtra("model", sm1);
				startActivityForResult(i, 4);
			}
		});

		btnReview2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ReviewActivity.class);
				// i.putExtra("speed1", p1.getQuarterMileSpeed());
				// i.putExtra("time1", p1.getQuarterMileTime());
				// i.putExtra("speed2", p2.getQuarterMileSpeed());
				// i.putExtra("time2", p2.getQuarterMileSpeed());
				i.putExtra("model", sm2);
				startActivityForResult(i, 5);
			}
		});
		return rootView;
	}

	SubModel sm1, sm2;
	int index = 0;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				sm1 = (SubModel) data.getSerializableExtra("selected");
				txtCar1.setText("Vehicle 1: " + sm1.getName());
				index = 1;
				new DownloadImage().execute(sm1.getImage());
				ll1.setVisibility(View.VISIBLE);

			} else if (requestCode == 1) {
				sm2 = (SubModel) data.getSerializableExtra("selected");
				txtCar2.setText("Vehicle 2: " + sm2.getName());
				index = 2;
				new DownloadImage().execute(sm2.getImage());
				ll2.setVisibility(View.VISIBLE);

			}
			if (sm1 != null && sm2 != null) {
				btnCompare.setVisibility(View.VISIBLE);
			}
		}
	}

	public class DownloadImage extends AsyncTask<String, Integer, Bitmap> {

		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "", "Fetching image");
		}

		@Override
		protected Bitmap doInBackground(String... arg0) {
			try {

				Bitmap bmp = BitmapFactory.decodeStream((InputStream) new URL(
						arg0[0]).getContent());
				// double x = bmp.getWidth() / bmp.getHeight();
				// double w = 150 * x;
				// return bmp;
				return Bitmap.createScaledBitmap(bmp, 200, 150, true);
			} catch (MalformedURLException e) {
				e.printStackTrace();

			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * Called after the image has been downloaded -> this calls a function
		 * on the main thread again
		 */
		protected void onPostExecute(Bitmap image) {
			if (index == 1) {
				if (image != null) {
					img1.setImageBitmap(image);
				}
			} else if (index == 2) {
				if (image != null) {
					img2.setImageBitmap(image);
				}
			}
			pd.dismiss();
		}

	}

	public class GetPerformanceTask extends AsyncTask<String, Void, Void> {
		ProgressDialog pd;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pd = ProgressDialog.show(getActivity(), "",
					"Fetching Performance Data");
		}

		@Override
		protected Void doInBackground(String... param) {
			Log.e("IDS", param[0] + " " + param[1]);
			// String url1 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[0] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";
			// String url2 =
			// "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
			// + param[1] + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";
			String url1 = "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
					+ 3653 + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";
			String url2 = "http://hearstcars.api.mashery.com/v1/api/perfdata/by-submodel-id/"
					+ 3652 + "/json?api_key=aegyqdyzstg92e6kh82vbf4t";

			Log.e("url1", url1);
			Log.e("url2", url2);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet getReq = new HttpGet(url1);
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String respStr = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", respStr);
					JSONObject respJson = new JSONObject(respStr);
					JSONArray perfArray = respJson.getJSONArray("perfdata");
					JSONObject perfObj = perfArray.getJSONObject(0);
					String id = perfObj.getString("id");
					String quarterMileTime = perfObj
							.getString("quarter_mile_time");
					String quarterMileSpeed = perfObj
							.getString("quarter_mile_speed");

					p1 = new Performance(id,
							Double.parseDouble(quarterMileTime),
							Double.parseDouble(quarterMileSpeed));
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}

			client = new DefaultHttpClient();
			getReq = new HttpGet(url2);
			try {
				HttpResponse resp = client.execute(getReq);
				if (resp != null
						&& resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String respStr = EntityUtils.toString(resp.getEntity());
					Log.e("JSON", respStr);
					JSONObject respJson = new JSONObject(respStr);
					JSONArray perfArray = respJson.getJSONArray("perfdata");
					JSONObject perfObj = perfArray.getJSONObject(0);
					String id = perfObj.getString("id");
					String quarterMileTime = perfObj
							.getString("quarter_mile_time");
					String quarterMileSpeed = perfObj
							.getString("quarter_mile_speed");

					p2 = new Performance(id,
							Double.parseDouble(quarterMileTime),
							Double.parseDouble(quarterMileSpeed));
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
			if (p1 != null && p2 != null) {
				txtPer1.setText("Q Mile Time: " + p1.getQuarterMileTime()
						+ "\nQ Mile Speed: " + p1.getQuarterMileSpeed());
				txtPer2.setText("Q Mile Time: " + p2.getQuarterMileTime()
						+ "\nQ Mile Speed: " + p2.getQuarterMileSpeed());
				ll3.setVisibility(View.VISIBLE);
				ll4.setVisibility(View.VISIBLE);
				btnSimulate.setVisibility(View.VISIBLE);
			} else {
				Toast.makeText(getActivity(), "Performance Data Not Available",
						Toast.LENGTH_LONG).show();
			}
		}
	}
}
