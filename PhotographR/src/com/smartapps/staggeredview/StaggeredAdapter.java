package com.smartapps.staggeredview;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.smartapps.lazylist.Photo;
import com.smartapps.photographr.R;

public class StaggeredAdapter extends ArrayAdapter<Photo> {

	private ImageLoader mLoader;
	private ArrayList<Photo> items;

	public StaggeredAdapter(Context context, int textViewResourceId,
			ArrayList<Photo> items) {
		super(context, textViewResourceId, items);
		mLoader = new ImageLoader(context);
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;

		if (convertView == null) {
			LayoutInflater layoutInflator = LayoutInflater.from(getContext());
			convertView = layoutInflator.inflate(R.layout.row_staggered_demo,
					null);
			holder = new ViewHolder();
			holder.imageView = (ScaleImageView) convertView
					.findViewById(R.id.imageView1);
			holder.txtTitle = (TextView) convertView
					.findViewById(R.id.textView1);

			convertView.setTag(holder);
		}

		holder = (ViewHolder) convertView.getTag();

		mLoader.DisplayImage(items.get(position).getImgUrl(), holder.imageView);
		holder.txtTitle.setText(items.get(position).getTitle());
		return convertView;
	}

	static class ViewHolder {
		ScaleImageView imageView;
		TextView txtTitle;
	}
}
