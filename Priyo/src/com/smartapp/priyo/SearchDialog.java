package com.smartapp.priyo;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.smartapp.db.DatabaseHelper;
import com.smartapp.entities.Make;
import com.smartapp.entities.Model;
import com.smartapp.entities.SubModel;
import com.smartapp.entities.SubModelGroup;

public class SearchDialog extends DialogFragment {

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

	private View v;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		v = getActivity().getLayoutInflater().inflate(
				R.layout.search_custom_dialog, null);
		spnMake = (Spinner) v.findViewById(R.id.spnMake);
		spnModel = (Spinner) v.findViewById(R.id.spnModel);
		spnSubModelGroup = (Spinner) v.findViewById(R.id.spnSubModelGroup);
		spnSubModel = (Spinner) v.findViewById(R.id.spnSubModel);
		btnSelect = (Button) v.findViewById(R.id.btnSelect);
		dbHelper = new DatabaseHelper(getActivity());
		makes = dbHelper.getAllMakes();
		models = dbHelper.getAllModels();
		smgs = dbHelper.getAllSubModelGroups();
		subModels = dbHelper.getAllSubModels();

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
			subModelsA.add(smgs.get(i).getName());
		}

		adapterMake = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, makesA);
		adapterModel = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, modelsA);
		adapterSmgs = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, smgA);
		adapterSM = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, subModelsA);

		spnMake.setAdapter(adapterMake);
		spnModel.setAdapter(adapterModel);
		spnSubModelGroup.setAdapter(adapterSmgs);
		spnSubModel.setAdapter(adapterSM);
		btnSelect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				SubModel subModel = subModels.get(spnSubModel
						.getSelectedItemPosition());

				dismiss();
			}
		});

		spnSubModelGroup.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				String idMake = makes.get(spnMake.getSelectedItemPosition())
						.getId();
				String idModel = models.get(spnModel.getSelectedItemPosition())
						.getId();
				String idSmg = smgs.get(
						spnSubModelGroup.getSelectedItemPosition()).getId();
				String[] params = new String[3];
				params[0] = idMake;
				params[1] = idModel;
				params[2] = idSmg;
				subModels = dbHelper.search(params);
				subModelsA = new ArrayList<String>();
				for (int i = 0; i < subModels.size(); i++) {
					subModelsA.add(smgs.get(i).getName());
				}
				adapterSM = new ArrayAdapter<String>(getActivity(),
						android.R.layout.simple_spinner_item, subModelsA);
				spnSubModel.setAdapter(adapterSM);
			}

		});
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		return (builder.setTitle("Search a Vehicle").setView(v).create());
	}

}
