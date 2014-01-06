package com.smartapp.priyo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

public class HomeActivity extends SherlockActivity {
	ActionBar actionBar;
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		actionBar = getSupportActionBar();
		actionBar.setDisplayShowHomeEnabled(true);

		// coverFlow.setSelection(8, true);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

		LayoutInflater inflator = LayoutInflater.from(this);
		View v = inflator.inflate(R.layout.myabs_layout_home, null);
		TextView ABStitle = (TextView) v.findViewById(R.id.myabs_title);
		ABStitle.setText("Automotivated");

		actionBar.setCustomView(v);
		prefs = getSharedPreferences("auto", Context.MODE_PRIVATE);

		Button si = (Button) findViewById(R.id.bsignin);
		si.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				EditText username_text = (EditText) findViewById(R.id.uid);
				EditText pass_text = (EditText) findViewById(R.id.passw);
				try {
					String uid = username_text.getText().toString();
					String pass = pass_text.getText().toString();

					if (!uid.equals("") && !pass.equals("")) {
						String u = prefs.getString("uid", "");
						String p = prefs.getString("pass", "");
						if (p.equals(pass) && u.equals(uid)) {
							Intent am = new Intent(HomeActivity.this,
									MainActivity.class);

							startActivity(am);
						} else {
							AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
									HomeActivity.this);

							// set title
							alertDialogBuilder.setTitle("Your Title");

							// set dialog message
							alertDialogBuilder
									.setMessage("UserID & Password Mismatch!")
									.setCancelable(false)
									.setPositiveButton(
											"Try again",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													// if this button is
													// clicked,
													// close
													// current activity
													EditText username_text = (EditText) findViewById(R.id.uid);
													EditText pass_text = (EditText) findViewById(R.id.passw);
													username_text.setText("");
													pass_text.setText("");
												}
											})
									.setNegativeButton(
											"Exit",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													HomeActivity.this.finish();
												}
											});

							// create alert dialog
							AlertDialog alertDialog = alertDialogBuilder
									.create();

							// show it
							alertDialog.show();
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	public void signup(View v) {
		startActivity(new Intent(this, SignupActivity.class));
	}

}
