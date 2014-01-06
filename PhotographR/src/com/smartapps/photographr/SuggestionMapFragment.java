package com.smartapps.photographr;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class SuggestionMapFragment extends SupportMapFragment {
	public static final String TAG = "map";
	private SupportMapFragment fragment;
	private GoogleMap map;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		map = super.getMap();
	}

	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View v = inflater.inflate(R.layout.fragment_map, container, false);
	// FragmentManager fm = getChildFragmentManager();
	// fragment = (SupportMapFragment) fm.findFragmentById(R.id.maps);
	// if (fragment == null) {
	// fragment = SupportMapFragment.newInstance();
	// fm.beginTransaction().replace(R.id.maps, fragment).commit();
	// }
	// map = fragment.getMap();
	//
	// return v;
	// }

	@Override
	public void onResume() {
		super.onResume();
		if (map == null) {
			map = super.getMap();
		}
	}

	public void refreshList() {
		Log.e("REFRESHING", "------------Refreshing the list------------");
	}
}
