package com.agme.maptypes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;

public class CustomMapFragment extends Fragment {

	private MapView mapView;
	private GoogleMap map;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// inflat and return the layout
		View v = inflater.inflate(R.layout.map_fragment_layout, container,
				false);
		mapView = (MapView) v.findViewById(R.id.mapView);
		mapView.onCreate(savedInstanceState);
		map = mapView.getMap();
		if (map == null) {
			Toast.makeText(getActivity(), "Map is not available",
					Toast.LENGTH_LONG).show();
		}
		return v;
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		mapView.onLowMemory();
	}
}
