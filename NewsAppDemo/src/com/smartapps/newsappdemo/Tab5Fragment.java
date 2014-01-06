package com.smartapps.newsappdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tab5Fragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
		TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
		dummyTextView.setText("test tab 5");
		return rootView;
	}
}
