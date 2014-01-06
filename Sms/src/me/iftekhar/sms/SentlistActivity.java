package me.iftekhar.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SentlistActivity extends Activity {
	private ArrayList<Sms>allSent;
	  private DBAdapter dbAdapter;
	  private SentAdapter adapter;
	  private ListView lvSent;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sentlist);
		
		
		dbAdapter=new DBAdapter(this);
		dbAdapter.open();
		allSent=dbAdapter.getAllsent();
		adapter=new SentAdapter(this,allSent);
		dbAdapter.close();
		lvSent=(ListView)findViewById(R.id.lvSent);
		
		
		
		lvSent.setAdapter(adapter);

		lvSent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listview, View v, int position,
					long id) {
				
				String sms=adapter.getItem(position).getText();
				Intent i=new Intent(adapter.getcontext(),CreateActivity.class);
				
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				i.putExtra("data",sms);
				startActivity(i);
				
			}
		});
		
	}

	

}
