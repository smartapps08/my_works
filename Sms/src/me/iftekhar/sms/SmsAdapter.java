package me.iftekhar.sms;

import java.util.ArrayList;

import me.iftekhar.sms.R.drawable;


import android.app.Activity;
import android.content.Context;
import android.media.AudioRecord.OnRecordPositionUpdateListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class SmsAdapter extends ArrayAdapter<Sms> {

	
	private Activity context;
	private ArrayList<Sms> items;
	private LayoutInflater inflate;
	
	
	public SmsAdapter(Context context, ArrayList<Sms> items) {
		super(context,R.layout.sms_arrange,items);
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
			v=inflate.inflate(R.layout.sms_arrange, null);
			
			
			
			TextView tvNumber= (TextView)v.findViewById(R.id.tvNumber); 
			TextView tvDate= (TextView)v.findViewById(R.id.tvDate);
			TextView tvText= (TextView)v.findViewById(R.id.tvText);
			ImageView imgView=(ImageView)v.findViewById(R.id.imgRead);
			Sms s=items.get(position);
			tvNumber.setText(s.getNumber()+"");
			tvDate.setText(s.getDate());
			tvText.setText(s.getText());
		
			if(s.getRead_flag()>0)
			{
				
			imgView.setImageResource(drawable.read_message_icon);
			}
			
			
	
			
		}
		
		return v;
	}
	
	
		
	}

	


