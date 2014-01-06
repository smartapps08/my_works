package bd.org.basis.downloader;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	private EditText etFileUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		etFileUrl = (EditText) findViewById(R.id.etFileUrl);
	}

	public void startDownload(View v) {
		String path = etFileUrl.getText().toString();
		Log.e(getClass().getSimpleName(), "Path: " + path);
		Intent intent = new Intent(this, DownloaderService.class);
		Uri uri = Uri.parse(path);
		intent.setData(uri);
		intent.putExtra("handler", new Messenger(handler));
		startService(intent);

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1) {
			case Activity.RESULT_OK:
				Toast.makeText(getApplicationContext(), "Download Completed",
						Toast.LENGTH_LONG).show();
				break;
			case Activity.RESULT_CANCELED:
				Toast.makeText(getApplicationContext(), "Download Failed",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	};
}
