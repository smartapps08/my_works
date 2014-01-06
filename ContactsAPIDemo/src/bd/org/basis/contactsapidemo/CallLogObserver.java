package bd.org.basis.contactsapidemo;

import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class CallLogObserver extends ContentObserver {

	Handler handler;
	public CallLogObserver(Handler handler) {
		super(handler);
		this.handler=handler;
		// TODO Auto-generated constructor stub
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
		if(uri==null)
		{
			
		}
		else
		{
			Log.e("CHANGE", "URI: "+uri.getPath());
			// tasks on change
		}
		handler.sendEmptyMessage(0);
	}

}
