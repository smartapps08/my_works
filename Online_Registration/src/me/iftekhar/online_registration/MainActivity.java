 package me.iftekhar.online_registration;


import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
private Button btnSignup;
private Button btnSubmit;

private EditText etEmail;
private EditText etPass;
private String mail;
private String pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnSignup=(Button) findViewById(R.id.btnSignup);
		btnSubmit=(Button) findViewById(R.id.btnSubmit);
		
		etEmail=(EditText) findViewById(R.id.etEmail);
		etPass=(EditText) findViewById(R.id.etPass);
		
		btnSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i=new Intent(MainActivity.this, Registration.class);
				
				startActivity(i);
			}
		});
		
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			
				
				GetReqThread t=new GetReqThread(handler);
				t.start();
				
			
					
				}
				
				
			
		});
		
		
		
		
	}
	
	
Handler handler=new Handler()
{
	public void handleMessage(android.os.Message msg) {
		
		Bundle b=msg.getData();
	@SuppressWarnings("unchecked")
	ArrayList<User> users=(ArrayList<User>)b.getSerializable("users");
	mail=etEmail.getText().toString();
    pass=etPass.getText().toString();
    int y=0;
	if(users!=null)
	{
		for(User user:users)
			
		{
			
				if(user.getEmail().equals(mail))
				{
					if(user.getPassword().equals(pass))
						
					{
						
						
						Toast.makeText(getApplicationContext(), "Email:"+user.getEmail()+"Password:"+user.getPassword(), Toast.LENGTH_LONG).show();
						Intent i=new Intent(MainActivity.this, ShowActivity.class);
						
						i.putExtra("address", user.getAdress());
						i.putExtra("mail", user.getEmail());
						i.putExtra("name", user.getAdress());
						startActivity(i);
						y=1;
						break;
					
					
					}
					
					else
					{
						
					}
					
				}
				else
				{
					
				
				}
			
			
		}
		
	}
	if(y<1)
	{
		//Toast.makeText(getApplicationContext(), "No email Such that", Toast.LENGTH_LONG).show();
	}
	
}
	
};
}
