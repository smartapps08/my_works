package com.agme.nearme;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PlacesListAdapter extends ArrayAdapter<Place> {

	private List<Place> placesList;
	private Activity activity;

	public PlacesListAdapter(Context context, ArrayList<Place> places) {
		super(context, R.id.txtName, places);
		this.placesList = places;
		this.activity = (Activity) context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			v = activity.getLayoutInflater().inflate(R.layout.places_list_row,
					null);
		}
		Place place = placesList.get(position);
		if (place != null) {
			ImageView imgIcon=(ImageView)v.findViewById(R.id.icon);
			TextView txtName = (TextView) v.findViewById(R.id.txtName);
			TextView txtVicinity = (TextView) v.findViewById(R.id.txtVicinity);
			TextView txtRating = (TextView) v.findViewById(R.id.txtRating);
			txtName.setText(place.getName());
			txtVicinity.setText(place.getVicinity());
			txtRating.setText("Rating: " + place.getRating());
		}
		return v;
	}
}