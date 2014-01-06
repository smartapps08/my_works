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

public class DraftlistActivity extends Activity {
	private ArrayList<Sms>allDraft;
	  private DBAdapter dbAdapter;
	  private DraftAdapter adapter;
	  private ListView lvDraft;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_draftlist);
		
		
		dbAdapter=new DBAdapter(this);
		dbAdapter.open();
		allDraft=dbAdapter.getAlldraft();
		adapter=new DraftAdapter(this,allDraft);
		dbAdapter.close();
		lvDraft=(ListView)findViewById(R.id.lvDraft);
		
		
		
		lvDraft.setAdapter(adapter);

		lvDraft.setOnItemClickListener(new OnItemClickListener() {

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
