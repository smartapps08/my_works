package bd.org.basis.todotestapp;

import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etTask;

	private ContentResolver cr;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etTask = (EditText) findViewById(R.id.etTask);
		cr = getContentResolver();
	}

	public void createTask(View v) {
		String task = etTask.getText().toString();
		if (!task.equals("")) {
			ToDoItem item = new ToDoItem(task);
			ContentValues values = new ContentValues();
			values.put("task", item.getTask());
			values.put("creation_date", item.getCreated().getTime());
			Uri uri = cr.insert(
					Uri.parse("content://bd.org.basis.todoprovider/todoitems"),
					values);
			if (uri == null) {
				Toast.makeText(getApplicationContext(), "Data NOT inserted",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), uri.toString(),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void viewTasks(View v) {
		Cursor cursor = cr.query(
				Uri.parse("content://bd.org.basis.todoprovider/todoitems"),
				null, null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			cursor.moveToFirst();
			for (int i = 0; i < cursor.getCount(); i++) {
				String task = cursor.getString(cursor.getColumnIndex("task"));
				long time = cursor.getLong(cursor
						.getColumnIndex("creation_date"));
				Date created = new Date(time);
				ToDoItem item = new ToDoItem(task, created);
				Toast.makeText(getApplicationContext(), item.toString(),
						Toast.LENGTH_LONG).show();

				cursor.moveToNext();
			}

			cursor.close();
		}

	}
}
