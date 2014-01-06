package bd.org.basis.menus;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView txtHello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtHello = (TextView) findViewById(R.id.txtHello);
		registerForContextMenu(txtHello);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.main, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_game:
			Toast.makeText(getApplicationContext(), "New Game",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.action_save_game:
			Toast.makeText(getApplicationContext(), "Save Game",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "Settings",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_game:
			Toast.makeText(getApplicationContext(), "New Game",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.action_save_game:
			Toast.makeText(getApplicationContext(), "Save Game",
					Toast.LENGTH_LONG).show();
			break;
		case R.id.action_settings:
			Toast.makeText(getApplicationContext(), "Settings",
					Toast.LENGTH_LONG).show();
			break;
		default:
			break;
		}

		return true;
	}

}
