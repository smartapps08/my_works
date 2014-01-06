package bd.org.basis.contentproviderdemo;

import android.annotation.SuppressLint;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;

@SuppressLint("NewApi")
public class CallLogObserver extends ContentObserver {
	Handler handler;

	public CallLogObserver(Handler handler) {
		super(handler);
		this.handler = handler;

	}

	@Override
	public void onChange(boolean selfChange) {
		handleChange(null);
	}

	@Override
	public void onChange(boolean selfChange, Uri uri) {
		handleChange(uri);
	}

	private void handleChange(Uri uri) {
		if (uri == null) {

		} else {

		}

		handler.sendEmptyMessage(0);
	}

}
