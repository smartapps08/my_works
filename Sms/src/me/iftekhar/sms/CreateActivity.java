package me.iftekhar.sms;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateActivity extends Activity implements OnClickListener{
private EditText etAddress;
private EditText etSms;
private Button btnSend;
private Button  btnSave;
private DBAdapter dbAdapter;
private String number;
private String sms;
private TextView textView1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
		
	
		
		String  sms_sig_I=getIntent().getStringExtra("data");
		textView1=(TextView)findViewById(R.id.textView1);
		etAddress=(EditText) findViewById(R.id.etAddress);
		etSms=(EditText) findViewById(R.id.etSms);
		btnSend=(Button) findViewById(R.id.btnSend);
		btnSave=(Button) findViewById(R.id.btnSave);
		 dbAdapter=new DBAdapter(this);
		etAddress.setOnClickListener(this);
		etSms.setOnClickListener(this);
		btnSend.setOnClickListener(this);
		btnSave.setOnClickListener(this);
		
		
	if(sms_sig_I!=null)
		{	
		etSms.setText(sms_sig_I);
		}
		else
		{
			etSms.setText("");
		}
		
		
	}

	@Override
	public void onClick(View v) {
		
		
		number=etAddress.getText().toString().trim();
		sms=etSms.getText().toString().trim();
		switch(v.getId())
		
		{
		case R.id.btnSend:
			SmsManager manager = SmsManager.getDefault();
			manager.sendTextMessage(number, null, etSms.getText().toString().trim(), null, null);
			Toast.makeText(getApplicationContext(), "Successfully Send",Toast.LENGTH_LONG).show();
			long number_s=Long.parseLong(number);
			Sms sent=new Sms(number_s,sms);
			
			dbAdapter.open();
		     long inserted = dbAdapter.insertSent(sent);
		     dbAdapter.close(); 
			break;
			
		case R.id.btnSave:
			dbAdapter.open();
			Sms draft=new Sms(sms,"");
			long i=dbAdapter.insertDraft(draft);
			dbAdapter.close();
			Toast.makeText(getApplicationContext(), "Successfully Saved",Toast.LENGTH_LONG).show();
			default:
				break;
			
                 		
		}
		
		
	}

	
}
