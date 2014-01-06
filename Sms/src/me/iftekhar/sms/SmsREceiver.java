package me.iftekhar.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import android.widget.Toast;

public class SmsREceiver extends BroadcastReceiver {
    private DBAdapter dbAdapter;
	String number="";
    String smsbody="";
    
	
	@Override
	
	
	
	
	public void onReceive(Context context, Intent intent) {
		

	
     Bundle bundle=intent.getExtras();
     
     
     dbAdapter=new DBAdapter(context);
    
     SmsMessage chunks[]=null;
     Object pdus[]=(Object[]) bundle.get("pdus");
     
     chunks=new SmsMessage[pdus.length];
     for (int i = 0; i < pdus.length; i++) {
		chunks[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
		smsbody+=chunks[i].getMessageBody();
		number=chunks[i].getOriginatingAddress();
	}
     long number_i=Long.parseLong(number);
    
     Sms sms=new Sms(number_i,smsbody);
     dbAdapter.open();
     long inserted = dbAdapter.insertInbox(sms);
    
     dbAdapter.close(); 
	}
}
	
	

