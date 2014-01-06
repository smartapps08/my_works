package bd.org.basis.abtabs;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.ActionBar.TabListener;
import com.actionbarsherlock.app.SherlockFragmentActivity;



public class MainActivity extends SherlockFragmentActivity {
	ActionBar actionBar;
	FragmentA fragA;
	FragmentB fragB;
	FragmentC fragC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		fragA = new FragmentA();
		fragB = new FragmentB();
		fragC = new FragmentC();

		ActionBar.Tab aTab = actionBar.newTab();
		aTab.setText("Tab A");
		aTab.setIcon(R.drawable.ic_launcher);

		ActionBar.Tab bTab = actionBar.newTab();
		bTab.setText("Tab B");
		bTab.setIcon(R.drawable.ic_launcher);

		ActionBar.Tab cTab = actionBar.newTab();
		cTab.setText("Tab C");
		cTab.setIcon(R.drawable.ic_launcher);

		aTab.setTabListener(new MyTabListener());
		bTab.setTabListener(new MyTabListener());
		cTab.setTabListener(new MyTabListener());

		actionBar.addTab(aTab);
		actionBar.addTab(bTab);
		actionBar.addTab(cTab);
		actionBar.selectTab(aTab);
	}

	class MyTabListener implements TabListener {

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			Toast.makeText(getApplicationContext(), "onTabReselected",
					Toast.LENGTH_LONG).show();

		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			Toast.makeText(getApplicationContext(), "onTabSelected",
					Toast.LENGTH_LONG).show();
			if (tab.getPosition() == 0) {
				ft.replace(android.R.id.content, fragA);
			} else if (tab.getPosition() == 1) {
				ft.replace(android.R.id.content, fragB);
			} else if (tab.getPosition() == 2) {
				ft.replace(android.R.id.content, fragC);
			}

		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			Toast.makeText(getApplicationContext(), "onTabUnselected",
					Toast.LENGTH_LONG).show();

		}

	}

}
