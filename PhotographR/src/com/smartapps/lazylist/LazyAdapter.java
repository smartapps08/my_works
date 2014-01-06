package com.smartapps.lazylist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartapps.photographr.R;

public class LazyAdapter extends BaseAdapter{
	private Activity activity;
    private String[] data;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    public ArrayList<Photo> photos;
    
    public LazyAdapter(Activity a, String[] d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
    
    public LazyAdapter(Activity a, ArrayList<Photo> photoList) {
        activity = a;
        photos=photoList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return photos.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.item, null);

        
        Photo p=photos.get(position);
        TextView text=(TextView)vi.findViewById(R.id.title);
        TextView url=(TextView)vi.findViewById(R.id.txturl);
        ImageView image=(ImageView)vi.findViewById(R.id.image);
        
        text.setText(p.getTitle());
        url.setText(p.getImgUrl());
        imageLoader.DisplayImage(p.getImgUrl(), image);
        return vi;
    }

}
