package me.iftekhar.sms;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private Button btnCreateMessage,btnInbox,btnSentItems,btnDrafts;
	
private ImageView imgOut;
private DBAdapter dbAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		btnCreateMessage=(Button)findViewById(R.id.btnCreateMessage);
		btnDrafts=(Button) findViewById(R.id.btnDrafts);
		btnInbox=(Button) findViewById(R.id.btnInbox);
		btnSentItems=(Button)findViewById(R.id.btnSentItems);
		imgOut=(ImageView) findViewById(R.id.imgOut);
		
		dbAdapter=new DBAdapter(this);
		
		
		btnCreateMessage.setOnClickListener(this);
		btnDrafts.setOnClickListener(this);
		btnInbox.setOnClickListener(this);
		btnSentItems.setOnClickListener(this);
		
	     
	

	}
	
	 @Override
	protected void onResume() {
		
		super.onResume();
		
		
			dbAdapter.open();
		if(dbAdapter.check()>0)
		{
			imgOut.setVisibility(ImageView.VISIBLE);
		}
		else
		{
			imgOut.setVisibility(ImageView.GONE);
		}
	}
	 
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		dbAdapter.close();
		}
	
	
	@Override
	public void onClick(View v) {
		
	switch(v.getId())
		{
		
		case R.id.btnCreateMessage:
			
			Intent intent_c=new Intent(this, CreateActivity.class);
			intent_c.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_c);	
		     
	  
	   
	   
			break;
			
		case R.id.btnInbox:
			
	Intent intent=new Intent(this, SmsListActivity.class);
	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(intent);		
			
			
	        break;
		case R.id.btnDrafts:
			
			Intent intent_d=new Intent(this, DraftlistActivity.class);
			intent_d.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_d);		
			 
			
			
			break;
			
			
		case R.id.btnSentItems:
			Intent intent_s=new Intent(this, SentlistActivity.class);
			intent_s.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_s);	
			break;
			default:
				break;
		
		}
		
	}
	
	
	}

 
