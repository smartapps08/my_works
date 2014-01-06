package me.iftekhar.online_registration;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowActivity extends Activity {
private TextView tvaddress,tvname,tvmail;
private ImageView imageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		
		tvaddress=(TextView) findViewById(R.id.tvaddress);
		tvmail=(TextView) findViewById(R.id.tvmail);
		tvname=(TextView) findViewById(R.id.tvname);
		imageView=(ImageView) findViewById(R.id.imageView);
		
		String address=getIntent().getStringExtra("address");
		String mail=getIntent().getStringExtra("mail");
		String name=getIntent().getStringExtra("name");
		
		tvaddress.setText(address);
		tvmail.setText(mail);
		tvname.setText(name);
		
	}

	

}
