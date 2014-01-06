package me.iftekhar.sms;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SentAdapter extends ArrayAdapter<Sms> {
	private Activity context;
	private ArrayList<Sms> items;
	private LayoutInflater inflate;
	
	
	public SentAdapter(Context context, ArrayList<Sms> items) {
		super(context,R.layout.sent_arrange,items);
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
			v=inflate.inflate(R.layout.sent_arrange, null);
			
			
			
			TextView tvAddress_s= (TextView)v.findViewById(R.id.tvAddress_s); 
			TextView tvDate_s= (TextView)v.findViewById(R.id.tvDate_s);
			TextView tvText_s= (TextView)v.findViewById(R.id.tvText_s);
			Sms s=items.get(position);
			tvAddress_s.setText(s.getNumber()+"");
			tvDate_s.setText(s.getDate());
			tvText_s.setText(s.getText());
			
			
	
			
		}
		return v;
	}
	
		

}
