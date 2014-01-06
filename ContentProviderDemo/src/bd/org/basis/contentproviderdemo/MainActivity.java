package bd.org.basis.contentproviderdemo;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Browser;
import android.provider.CallLog;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	CallLogObserver observer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		observer = new CallLogObserver(handler);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getContentResolver().registerContentObserver(CallLog.Calls.CONTENT_URI,
				true, observer);
	}

	@Override
	protected void onPause() {
		super.onPause();
		getContentResolver().unregisterContentObserver(observer);
	}

	public void showCallLog(View v) {
		ContentResolver cr = getContentResolver();
		Cursor calllogCursor = cr.query(CallLog.Calls.CONTENT_URI, null, null,
				null, null);
		if (calllogCursor != null && calllogCursor.getCount() > 0) {
			while (calllogCursor.moveToNext()) {
				String number = calllogCursor.getString(calllogCursor
						.getColumnIndex(CallLog.Calls.NUMBER));
				String type = calllogCursor.getString(calllogCursor
						.getColumnIndex(CallLog.Calls.TYPE));
				long duration = calllogCursor.getLong(calllogCursor
						.getColumnIndex(CallLog.Calls.DURATION));
				Toast.makeText(getApplicationContext(),
						number + "----" + duration + "---" + type,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	public void showHistory(View v) {
		ContentResolver cr = getContentResolver();
		Cursor browserCursor = cr.query(Browser.BOOKMARKS_URI, null, null,
				null, null);
		if (browserCursor != null && browserCursor.getCount() > 0) {
			while (browserCursor.moveToNext()) {
				String url = browserCursor.getString(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.URL));
				String title = browserCursor.getString(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.TITLE));
				long time = browserCursor.getLong(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.DATE));
				Toast.makeText(getApplicationContext(),
						url + "----" + title + "---" + time, Toast.LENGTH_LONG)
						.show();
			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(getApplicationContext(), "Data changed",
					Toast.LENGTH_LONG).show();
		}
	};
}
