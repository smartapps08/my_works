package com.smartapp.priyo;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.smartapp.db.DatabaseHelper;
import com.smartapp.entities.Make;
import com.smartapp.entities.Model;
import com.smartapp.entities.SubModel;
import com.smartapp.entities.SubModelGroup;

public class SearchActivity extends SherlockActivity {
	private Spinner spnMake, spnModel, spnSubModelGroup, spnSubModel;
	private Button btnSelect;
	private ArrayList<Make> makes;
	private ArrayList<Model> models;
	private ArrayList<SubModelGroup> smgs;
	private ArrayList<SubModel> subModels;
	private ArrayAdapter<String> adapterMake, adapterModel, adapterSmgs,
			adapterSM;
	private DatabaseHelper dbHelper;

	private ArrayList<String> makesA, modelsA, smgA, subModelsA;

	private boolean first = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		// Show the Up button in the action bar.
		setupActionBar();
		spnMake = (Spinner) findViewById(R.id.spnMake);
		spnModel = (Spinner) findViewById(R.id.spnModel);
		spnSubModel = (Spinner) findViewById(R.id.spnSubModel);
		spnSubModelGroup = (Spinner) findViewById(R.id.spnSubModelGroup);

		btnSelect = (Button) findViewById(R.id.btnSelect);
		dbHelper = new DatabaseHelper(this);

		makes = dbHelper.getAllMakes();
		models = dbHelper.getAllModels();
		smgs = dbHelper.getAllSubModelGroups();
		subModels = dbHelper.getAllSubModels();

		Log.e("SubModels", subModels.size() + "---------------");

		makesA = new ArrayList<String>();
		for (int i = 0; i < makes.size(); i++) {
			makesA.add(makes.get(i).getName());
		}

		modelsA = new ArrayList<String>();
		for (int i = 0; i < models.size(); i++) {
			modelsA.add(models.get(i).getName());
		}

		smgA = new ArrayList<String>();
		for (int i = 0; i < smgs.size(); i++) {
			smgA.add(smgs.get(i).getName());
		}

		subModelsA = new ArrayList<String>();
		for (int i = 0; i < subModels.size(); i++) {
			subModelsA.add(subModels.get(i).getName());
		}

		Log.e("SubModels", subModelsA.size() + "---------------");

		adapterMake = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, makesA);
		adapterModel = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, modelsA);
		adapterSmgs = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, smgA);
		adapterSM = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, subModelsA);

		spnMake.setAdapter(adapterMake);
		spnModel.setAdapter(adapterModel);
		spnSubModel.setAdapter(adapterSM);
		spnSubModelGroup.setAdapter(adapterSmgs);

		spnSubModelGroup
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View v,
							int position, long id) {
						if (first) {
							first = false;
						} else {
							String idMake = makes.get(
									spnMake.getSelectedItemPosition()).getId();
							String idModel = models.get(
									spnModel.getSelectedItemPosition()).getId();
							String idSmg = smgs.get(
									spnSubModelGroup.getSelectedItemPosition())
									.getId();
							String[] params = new String[3];
							params[0] = idMake;
							params[1] = idModel;
							params[2] = idSmg;
							subModels = dbHelper.search(params);
							subModelsA = new ArrayList<String>();
							for (int i = 0; i < subModels.size(); i++) {
								subModelsA.add(smgs.get(i).getName());
							}
							adapterSM = new ArrayAdapter<String>(
									SearchActivity.this,
									android.R.layout.simple_spinner_item,
									subModelsA);
							spnSubModel.setAdapter(adapterSM);
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}

				});
		btnSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SubModel subModel = subModels.get(spnSubModel
						.getSelectedItemPosition());
				Intent i = new Intent();
				i.putExtra("selected", subModel);
				setResult(RESULT_OK, i);
				finish();
			}
		});

	}

	private void setupActionBar() {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
