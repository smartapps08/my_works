package com.example.fragmentsbasics;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class FragmentA extends Fragment implements OnClickListener {

	private Button btnSwitch, btnAlert;
	private OnSwitchFragmentClickListener listener;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		listener = (OnSwitchFragmentClickListener) activity;
		Toast.makeText(getActivity(), "onAttach", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Toast.makeText(getActivity(), "onCreate", Toast.LENGTH_LONG).show();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_a, null, false);
		Toast.makeText(getActivity(), "onCreateView", Toast.LENGTH_LONG).show();
		btnSwitch = (Button) v.findViewById(R.id.btnFragment);
		btnSwitch.setOnClickListener(this);

		btnAlert = (Button) v.findViewById(R.id.btnAlert);
		btnAlert.setOnClickListener(this);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Toast.makeText(getActivity(), "onActivityCreated", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Toast.makeText(getActivity(), "onStart", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Toast.makeText(getActivity(), "onResume", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnFragment:
			listener.onSwitchFragmentClick(v);
			break;
		case R.id.btnAlert:
			AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
			alert.setTitle("Dummy Alert");
			alert.setMessage("This is from Fragment A");
			alert.show();

			alert.setNegativeButton("Cancel",
					new android.content.DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface di, int id) {
							di.dismiss();

						}
					});

			break;
		default:
			break;
		}

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Toast.makeText(getActivity(), "onPause", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Toast.makeText(getActivity(), "onStop", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		Toast.makeText(getActivity(), "onDestroyView", Toast.LENGTH_LONG)
				.show();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Toast.makeText(getActivity(), "onDestroy", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		Toast.makeText(getActivity(), "onDetach", Toast.LENGTH_LONG).show();
	}
}
