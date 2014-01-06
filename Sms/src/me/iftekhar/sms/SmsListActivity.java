package me.iftekhar.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

public class SmsListActivity extends Activity {
  private ArrayList<Sms>allinbox;
  private DBAdapter dbAdapter;
  private SmsAdapter adapter;
  
  private ListView lvSms;
  private SQLiteDatabase db;
 

 private int read;
 
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smslist);
		
		
		
		dbAdapter=new DBAdapter(this);
		dbAdapter.open();
		allinbox=dbAdapter.getAllinbox();
		adapter=new SmsAdapter(this,allinbox);
		
		dbAdapter.close();
		lvSms=(ListView)findViewById(R.id.lvSms);
		
		
		
		lvSms.setAdapter(adapter);

		lvSms.setOnItemClickListener(new OnItemClickListener() {

			
			
			
			@Override
			public void onItemClick(AdapterView<?> listview, View v, int position,
					long id) {
				
				AlertDialog.Builder alert = new AlertDialog.Builder(SmsListActivity.this);
				 final int pos = position;
				 alert.setTitle("Choose an Option!!");
				 alert.setMessage("Do You want delete or read? Otherwise Cancel");
				 alert.setPositiveButton("Read", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	String sms=adapter.getItem(pos).getText();
        				long n=adapter.getItem(pos).getNumber();
        				int serial=adapter.getItem(pos).getSerial_no();
        				
        			dbAdapter.open();
        			
        			dbAdapter.update_r(serial);
        			
        			
        				
        			
        			dbAdapter.close();	
        				
        				Intent i=new Intent(adapter.getcontext(),CreateActivity.class);
        				
        				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        				i.putExtra("data",sms);
        				
        				startActivity(i);
                            
                    }
            });
				 alert.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    	int serial=adapter.getItem(pos).getSerial_no();
                    	dbAdapter.open();
            			
            			dbAdapter.delete_r(serial);
            			dbAdapter.close();	
                    	finish();
                    	startActivity(getIntent());
                    }
            });
				 alert.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
	                    	
	                    }
	            });
            alert.show();
				
				
				
				
			}
		});
		
		
		
		
		
		
	}
	
		
	
	

}
