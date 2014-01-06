package bd.org.basis.fragments;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends FragmentActivity implements OnSwitchClickListener {
	FragmentManager manager;
	FragmentA fragA;
	FragmentB fragB;
	FragmentC fragC;
	boolean flagBSet = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragA = new FragmentA();
		fragB = new FragmentB();
		fragC = new FragmentC();

		manager = getSupportFragmentManager();

		FragmentTransaction ft = manager.beginTransaction();
		ft.add(R.id.a_container, fragA, "frag A");
		ft.add(R.id.bc_container, fragB, "frag B");
		flagBSet = true;
		ft.commit();
	}

	@Override
	public void onSwichClick(View v) {
		Toast.makeText(getApplicationContext(),
				"Switch Clicked: from Activity", Toast.LENGTH_LONG).show();
		FragmentTransaction ft = manager.beginTransaction();
		if (flagBSet) {
			ft.remove(fragB);
			ft.add(R.id.bc_container, fragC, "frag C");
			flagBSet = false;
		} else {
			ft.remove(fragC);
			ft.add(R.id.bc_container, fragB, "frag B");
			flagBSet = true;
		}

		ft.commit();
	}

}
