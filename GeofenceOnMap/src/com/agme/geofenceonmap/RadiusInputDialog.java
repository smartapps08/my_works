package com.agme.geofenceonmap;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class RadiusInputDialog extends DialogFragment implements
		OnEditorActionListener {
	private EditText etRadius;

	// To add a callback to the Activity when input is done
	public interface RadiusInputDialogListener {
		void onFinishInputRadius(int radius);
	}

	// empty constructor
	public RadiusInputDialog() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_input_radius, container);
		etRadius = (EditText) view.findViewById(R.id.etRadius);
		getDialog().setTitle("Input Radius");

		// requesting focus and showing soft keyboard automatically
		etRadius.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		etRadius.setOnEditorActionListener(this);
		return view;
	}

	@Override
	public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			// Return input text to activity
			RadiusInputDialogListener activity = (RadiusInputDialogListener) getActivity();
			activity.onFinishInputRadius(Integer.parseInt(etRadius.getText()
					.toString()));
			this.dismiss();
			return true;
		}
		return false;
	}
}
