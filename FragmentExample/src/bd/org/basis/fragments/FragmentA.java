package bd.org.basis.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;



public class FragmentA extends Fragment {

	OnSwitchClickListener listener;
	Button btnSwitch;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		listener = (OnSwitchClickListener) activity;
		Log.e(getClass().getSimpleName(), "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(getClass().getSimpleName(), "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_a, null, false);
		btnSwitch=(Button)view.findViewById(R.id.btnSwitch);
		btnSwitch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onSwichClick(v);
			}
		});
		Log.e(getClass().getSimpleName(), "onCreateView");
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.e(getClass().getSimpleName(), "onActivityCreated");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.e(getClass().getSimpleName(), "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e(getClass().getSimpleName(), "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.e(getClass().getSimpleName(), "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.e(getClass().getSimpleName(), "onStop");
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.e(getClass().getSimpleName(), "onDestroyView");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.e(getClass().getSimpleName(), "onDestroy");
	}

	@Override
	public void onDetach() {
		super.onDetach();
		Log.e(getClass().getSimpleName(), "onDetach");
	}
}
