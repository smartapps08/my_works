package me.iftekhar.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DraftAdapter extends ArrayAdapter<Sms> {
	private Activity context;
	private ArrayList<Sms> items;
	private LayoutInflater inflate;
	
	
	public DraftAdapter(Context context, ArrayList<Sms> items) {
		super(context,R.layout.draft_arrange,items);
		this.context=(Activity)context;
		this.items=items;
		
	}
	
	
	public Context getcontext()
	
	{
		
		return context;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=convertView;
		if(v==null)
		{
			inflate=context.getLayoutInflater();
			v=inflate.inflate(R.layout.draft_arrange, null);
			
			
			
			 
			TextView tvDate_d= (TextView)v.findViewById(R.id.tvDate_d);
			TextView tvText_d= (TextView)v.findViewById(R.id.tvText_d);
			Sms s=items.get(position);
			
			tvDate_d.setText(s.getDate());
			tvText_d.setText(s.getText());
			
			
	
			
		}
		return v;
	}
	
		
}
