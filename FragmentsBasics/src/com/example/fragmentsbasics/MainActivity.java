package com.example.fragmentsbasics;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends FragmentActivity implements
		OnSwitchFragmentClickListener {
	FragmentManager manager;
	FragmentA fragA;
	FragmentB fragB;
	FragmentC fragC;
	boolean isB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		manager = getSupportFragmentManager();
		fragA = new FragmentA();
		fragB = new FragmentB();
		fragC = new FragmentC();

		FragmentTransaction ft = manager.beginTransaction();
		// add, remove
		ft.add(R.id.a_container, fragA, "frag a");
		ft.add(R.id.bc_container, fragB, "frag b");
		isB = true;
		ft.commit();

	}

	@Override
	public void onSwitchFragmentClick(View v) {
		FragmentTransaction ft = manager.beginTransaction();
		if (isB) {
			ft.remove(fragB);
			ft.add(R.id.bc_container, fragC, "frag c");
			isB = false;
		} else {
			ft.remove(fragC);
			ft.add(R.id.bc_container, fragB, "frag b");
			isB = true;
		}
		ft.commit();
	}

}
