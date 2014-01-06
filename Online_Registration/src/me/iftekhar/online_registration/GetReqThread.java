package me.iftekhar.online_registration;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class GetReqThread extends Thread

{
	
	public static final int Success=1;
	public static final int Failure=0;
	public static ArrayList<User> check_user;
	private static final String Req_Url="http://10.0.2.2/online_registration/getallusers.php";
	private Handler handler;
	public GetReqThread(Handler handler)
	{
		this.handler=handler;
	}
	
	public void run()
	{
		//send a get request//
		DefaultHttpClient client=new DefaultHttpClient();
		HttpGet getReq=new HttpGet(Req_Url);
		try {
			HttpResponse response=client.execute(getReq);
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
			{
				HttpEntity entity=response.getEntity();
				String jsonStr=EntityUtils.toString(entity);
				//parse
				JSONObject jsonObj=new JSONObject(jsonStr);
			Log.e(getClass().getSimpleName(),jsonStr);
				String success=jsonObj.getString("success");
				
				if(success.equals("1"))
				{
					JSONArray userArray=jsonObj.getJSONArray("users");
					System.out.println(userArray.length());
					check_user=new ArrayList<User>();
					for (int i = 0; i < userArray.length(); i++) {
						JSONObject userObject=userArray.getJSONObject(i);
						int id=Integer.parseInt(userObject.getString("id"));
						String name=userObject.getString("name");
						String password=userObject.getString("password");
						String email=userObject.getString("email");
						String address=userObject.getString("address");
						String phone=userObject.getString("phone");
						Log.d(getClass().getSimpleName(),email);
						User user=new User(name, address, password, phone, email);
						check_user.add(user);
						
					}
					
					
					Message msg=Message.obtain();
					Bundle b=new Bundle();
					b.putSerializable("users", check_user);
					
					msg.setData(b);
					handler .sendMessage(msg);
				}
				else
				{
					String message=jsonObj.getString("message");
					
				}
				
			}
			else
			{
				
			}
		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
		
		//notify Ui thread
		Message msg=Message.obtain();
		Bundle b=new Bundle();
		b.putSerializable("users", check_user);
		msg.setData(b);
		handler .sendMessage(msg); 
		
	}
}