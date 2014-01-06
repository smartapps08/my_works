package me.iftekhar.online_registration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;

public class Registration extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks,GooglePlayServicesClient.OnConnectionFailedListener,LocationListener{
private EditText etName;
private EditText etEmail;
private EditText etAddress;
private EditText etPhone;
private EditText etPass;
private EditText etCon_pass;
private Button btnAdd;
private Location currentLocation;
private LocationClient locationClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		etName=(EditText) findViewById(R.id.etName_r);
		etEmail=(EditText) findViewById(R.id.etEmail_r);
		
		etAddress=(EditText)findViewById(R.id.etAddress_r);
		etPhone=(EditText)findViewById(R.id.etPhone_r);
		etPass=(EditText)findViewById(R.id.etPass_r);
		etCon_pass=(EditText)findViewById(R.id.etCon_passr);
		btnAdd=(Button) findViewById(R.id.btnAdd_r);
		locationClient=new LocationClient(this, this, this);
		if(isServiceConnected())
		{
			currentLocation=locationClient.getLastLocation();
			new GetAddressTask().execute(currentLocation);
		}
		
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name=etName.getText().toString();
				String email=etEmail.getText().toString();
				
				String address=etAddress.getText().toString();
				String phone=etPhone.getText().toString();
				String password=etPass.getText().toString();
				String con_pass=etCon_pass.getText().toString();
				
				
				
				User user=new User(name,address,password,phone,email);
				
			
				if(con_pass.equals(password))
				
					{new PostThread(user).start();
					}
				
				else{
					Toast.makeText(getApplicationContext(), "password not mtched", Toast.LENGTH_LONG).show();
				}
			}
		});
		
	}
	
	@Override
		protected void onStart() {
			
			super.onStart();
			locationClient.connect();
		}
	@Override
		protected void onStop() {
			
			super.onStop();
			locationClient.disconnect();
		}
	
	
	
	
	class PostThread extends Thread
	{
		private User user;
		public PostThread(User u)
		{
			this.user=u;
		}
		
		public void run()
		{
			//send post request//
			String messge="";
			
			DefaultHttpClient client=new DefaultHttpClient();
			HttpPost post_req=new HttpPost("http://10.0.2.2/online_registration/addusers.php");
			try {
				
				ArrayList<BasicNameValuePair> param=new ArrayList<BasicNameValuePair>();
				param.add(new BasicNameValuePair("name", this.user.getName()));
				param.add(new BasicNameValuePair("email", this.user.getEmail()));
				
				param.add(new BasicNameValuePair("password", this.user.getPassword()));
				param.add(new BasicNameValuePair("address", this.user.getAdress()));
				param.add(new BasicNameValuePair("phone", this.user.getPhone()));
				post_req.setEntity(new UrlEncodedFormEntity(param));
				HttpResponse response=client.execute(post_req);
				
				String success="";
				
				if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
				{
					HttpEntity entity=response.getEntity();
					String jsonStr=EntityUtils.toString(entity);
					try{
					JSONObject jsonObject=new JSONObject(jsonStr);
					success=jsonObject.getString("success");
					
					}
					catch (JSONException e) {
					Log.e("JSON Parser", "Error parsing data [" + e.getMessage()+"]"+ jsonStr);
						
						e.printStackTrace();
					}
					if(success.equals("2"))
					{
						
						handler.sendEmptyMessage(2);
						
					}
	               else if(success.equals("1")){
	            	  
	            	   handler.sendEmptyMessage(1);
	            	   
					} 
					
					else{
						handler.sendEmptyMessage(0);
					} 
					}
				else
				{
					handler.sendEmptyMessage(0);
				}
			} catch (ClientProtocolException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			} 
			
			
			
				}
	}

	
	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			
			
			
			
			
			
			if(msg.what==2)
			{
				
				Toast.makeText(getApplicationContext(), "Email is not avilable", Toast.LENGTH_LONG).show();
			}
			
			else if(msg.what==1)
			{
				Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_LONG).show();
			}
			
		else
		{
			Toast.makeText(getApplicationContext(), "Inserted failed", Toast.LENGTH_LONG).show();
		}
	
		
			
		}
	};

	@Override
	public void onConnected(Bundle b) {
		Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_LONG).show();
		
	}


	@Override
	public void onDisconnected() {
		Toast.makeText(getApplicationContext(), "DisConnected", Toast.LENGTH_LONG).show();
		
	}


	@Override
	public void onConnectionFailed(ConnectionResult connectionResult ) {
		//Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
		if(connectionResult.hasResolution())
		{
			
		}
		else
		{
		}
		
		
	}


	@Override
	public void onLocationChanged(Location location) {
		
		
	}
	private boolean isServiceConnected()
	{
		int resultcode=GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(ConnectionResult.SUCCESS==resultcode)
		{
			return true;
			
		}
		
		else
		{
			Dialog dialog=GooglePlayServicesUtil.getErrorDialog(resultcode, this, 0);
			//dialog.show();
			 return false;
		}
	}
	
	public class GetAddressTask extends AsyncTask<Location, Void, String>	
	{
		@Override
		protected String doInBackground(Location... locations) {
			Geocoder geocoder=new Geocoder(Registration.this);
			Location location=locations[0];
			List<Address> addresses;
			String addrStr="";
			try {
				addresses = (List<Address>)geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
				Address addr=addresses.get(0);
				 addrStr=addr.getAddressLine(0)+","+addr.getAdminArea()+","+addr.getCountryName();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return addrStr;
		}
		@Override
		protected void onPostExecute(String result) {
		
			etAddress.setText(result);
		}
	}

}