package com.agme.nearme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {

	private static final String[] CATEGORIES = { "Restaurants", "Bars",
			"Coffee & Tea", "Gas & Service Stations", "Drugstores", "Shopping" };
	private static final int[] CATEGORY_ICONS = { R.drawable.ic_restaurant,
			R.drawable.ic_bar, R.drawable.ic_coffee, R.drawable.ic_gas,
			R.drawable.ic_drugstore, R.drawable.ic_shopping };

	private ListView lvCategories;
	private CategoryListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvCategories = (ListView) findViewById(R.id.lvCategories);
		adapter = new CategoryListAdapter();
		lvCategories.setAdapter(adapter);

		lvCategories.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Toast.makeText(getApplicationContext(), "Selected",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(MainActivity.this,
						PlacesListActivity.class);
				intent.putExtra("category", CATEGORIES[position]);
				intent.putExtra("categoryIndex", position);
				startActivity(intent);
			}
		});

	}

	class CategoryListAdapter extends ArrayAdapter<String> {

		public CategoryListAdapter() {
			super(MainActivity.this, R.layout.category_list_row,
					R.id.txtCategoryName, CATEGORIES);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View row = super.getView(position, convertView, parent);
			ImageView categoryIcon = (ImageView) row
					.findViewById(R.id.imgCategory);
			categoryIcon.setImageResource(CATEGORY_ICONS[position]);

			TextView categoryName = (TextView) row
					.findViewById(R.id.txtCategoryName);
			categoryName.setText(CATEGORIES[position]);
			return row;
		}
	}
}
