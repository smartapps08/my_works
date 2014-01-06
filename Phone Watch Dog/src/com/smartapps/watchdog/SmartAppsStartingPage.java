package com.smartapps.watchdog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SmartAppsStartingPage extends Activity implements OnClickListener{

	Button Email,Contact;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Initialization();
		Email.setOnClickListener(this);
		//Contact.setOnClickListener(this);
	}

	private void Initialization() {
		// TODO Auto-generated method stub
		Email=(Button)findViewById(R.id.button1);
//		Contact=(Button)findViewById(R.id.button2);
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.button1:
			
			Intent i=new Intent("com.smartapps.watchdog.PHONEWATCHDOGACTIVITY");
			startActivity(i);
			break;
			
		}
		
	}
	
	

}
