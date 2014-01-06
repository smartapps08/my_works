package com.smartapp.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smartapp.entities.Make;
import com.smartapp.entities.Model;
import com.smartapp.entities.SubModel;
import com.smartapp.entities.SubModelGroup;

public class DatabaseHelper extends SQLiteOpenHelper {
	public static final String SUBMODEL_SQL = "create table submodels (_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT, image TEXT, model TEXT, make TEXT, year TEXT, submodel_group TEXT)";
	public static final String MODEL_SQL = "create table models (_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT, make TEXT)";
	public static final String MAKE_SQL = "create table makes (_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT)";
	public static final String SUBMODELGROUP_SQL = "create table submodelgroups (_id INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT, name TEXT, model TEXT)";

	public DatabaseHelper(Context context) {
		super(context, "hearst", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SUBMODEL_SQL);
		db.execSQL(MODEL_SQL);
		db.execSQL(MAKE_SQL);
		db.execSQL(SUBMODELGROUP_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}

	public long insertSubmodel(SubModel submodel) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", submodel.getId());
		values.put("name", submodel.getName());
		values.put("make", submodel.getMake());
		values.put("model", submodel.getModel());
		values.put("image", submodel.getImage());
		values.put("year", submodel.getYear());
		values.put("submodel_group", submodel.getSubModelGroup());
		long inserted = db.insert("submodels", null, values);
		db.close();
		return inserted;
	}

	public SubModel getSubModelById(String id) {
		SQLiteDatabase db = getReadableDatabase();
		SubModel sm = null;
		Cursor cursor = db.query("submodels", null, "id=?",
				new String[] { id }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String ids = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String make = cursor.getString(cursor.getColumnIndex("make"));
			String model = cursor.getString(cursor.getColumnIndex("model"));
			String image = cursor.getString(cursor.getColumnIndex("image"));
			String year = cursor.getString(cursor.getColumnIndex("year"));
			String smg = cursor.getString(cursor
					.getColumnIndex("submodel_group"));
			sm = new SubModel(ids, name, image, model, make, year, smg);
		} else {

		}
		cursor.close();
		db.close();
		return sm;

	}

	public ArrayList<SubModel> getAllSubModels() {
		ArrayList<SubModel> submodels = new ArrayList<SubModel>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("submodels", null, null, null, null, null,
				null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String ids = cursor.getString(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String make = cursor.getString(cursor.getColumnIndex("make"));
				String model = cursor.getString(cursor.getColumnIndex("model"));
				String image = cursor.getString(cursor.getColumnIndex("image"));
				String year = cursor.getString(cursor.getColumnIndex("year"));
				String smg = cursor.getString(cursor
						.getColumnIndex("submodel_group"));
				SubModel sm = new SubModel(ids, name, image, model, make, year,
						smg);
				submodels.add(sm);
				cursor.moveToNext();
			}
		} else {
		}

		cursor.close();
		db.close();
		return submodels;
	}

	public ArrayList<SubModel> search(String[] parameters) {
		ArrayList<SubModel> submodels = new ArrayList<SubModel>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("submodels", null, "make=? and model=? and submodel_group=?", parameters, null, null,
				null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String ids = cursor.getString(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String make = cursor.getString(cursor.getColumnIndex("make"));
				String model = cursor.getString(cursor.getColumnIndex("model"));
				String image = cursor.getString(cursor.getColumnIndex("image"));
				String year = cursor.getString(cursor.getColumnIndex("year"));
				String smg = cursor.getString(cursor
						.getColumnIndex("submodel_group"));
				SubModel sm = new SubModel(ids, name, image, model, make, year,
						smg);
				submodels.add(sm);
				cursor.moveToNext();
			}
		} else {
		}

		cursor.close();
		db.close();
		return submodels;
	}

	public long insertModel(Model model) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", model.getId());
		values.put("name", model.getName());
		values.put("make", model.getMake());

		long inserted = db.insert("models", null, values);
		db.close();
		return inserted;
	}

	public Model getModelById(String id) {
		SQLiteDatabase db = getReadableDatabase();
		Model m = null;
		Cursor cursor = db.query("models", null, "id=?", new String[] { id },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String ids = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String make = cursor.getString(cursor.getColumnIndex("make"));
			m = new Model(ids, name, make);
		} else {

		}
		cursor.close();
		db.close();
		return m;

	}

	public ArrayList<Model> getAllModels() {
		ArrayList<Model> models = new ArrayList<Model>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("models", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String ids = cursor.getString(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String make = cursor.getString(cursor.getColumnIndex("make"));
				Model sm = new Model(ids, name, make);
				models.add(sm);
				cursor.moveToNext();
			}
		} else {
		}

		cursor.close();
		db.close();
		return models;
	}

	public long insertMake(Make make) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", make.getId());
		values.put("name", make.getName());
		long inserted = db.insert("makes", null, values);
		db.close();
		return inserted;
	}

	public Make getMakeById(String id) {
		SQLiteDatabase db = getReadableDatabase();
		Make m = null;
		Cursor cursor = db.query("makes", null, "id=?", new String[] { id },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String ids = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			m = new Make(ids, name);
		} else {

		}
		cursor.close();
		db.close();
		return m;

	}

	public ArrayList<Make> getAllMakes() {
		ArrayList<Make> makes = new ArrayList<Make>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("models", null, null, null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String ids = cursor.getString(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				Make sm = new Make(ids, name);
				makes.add(sm);
				cursor.moveToNext();
			}
		} else {
		}

		cursor.close();
		db.close();
		return makes;
	}

	public long insertSubModelGroup(SubModelGroup smg) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", smg.getId());
		values.put("name", smg.getName());
		values.put("model", smg.getModel());
		long inserted = db.insert("submodelgroups", null, values);
		db.close();
		return inserted;
	}

	public SubModelGroup getSubModelGroupById(String id) {
		SQLiteDatabase db = getReadableDatabase();
		SubModelGroup sm = null;
		Cursor cursor = db.query("submodelgroups", null, "id=?",
				new String[] { id }, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			String ids = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String model = cursor.getString(cursor.getColumnIndex("model"));
			sm = new SubModelGroup(ids, name, model);
		} else {

		}
		cursor.close();
		db.close();
		return sm;

	}

	public ArrayList<SubModelGroup> getAllSubModelGroups() {
		ArrayList<SubModelGroup> smgs = new ArrayList<SubModelGroup>();
		SQLiteDatabase db = getReadableDatabase();
		Cursor cursor = db.query("submodelgroups", null, null, null, null,
				null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String ids = cursor.getString(cursor.getColumnIndex("id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String model = cursor.getString(cursor.getColumnIndex("model"));
				SubModelGroup sm = new SubModelGroup(ids, name, model);
				smgs.add(sm);
				cursor.moveToNext();
			}
		} else {
		}

		cursor.close();
		db.close();
		return smgs;
	}

}
