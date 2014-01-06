package bd.org.basis.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class DownloaderService extends IntentService {

	DefaultHttpClient client;

	public DownloaderService() {
		super("DownloaderService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		client = new DefaultHttpClient();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		int result = Activity.RESULT_CANCELED;

		String uriStr = intent.getData().toString();
		HttpGet getReq = new HttpGet(uriStr);
		try {
			HttpResponse resp = client.execute(getReq);
			ResponseHandler<byte[]> respHandler = new ByteArrayResponseHandler();
			byte[] respBytes = respHandler.handleResponse(resp);

			File file = new File(Environment.getExternalStorageDirectory(),
					intent.getData().getLastPathSegment());
			if (file.exists()) {
				file.delete();
			}
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(respBytes);
			fos.close();
			result = Activity.RESULT_OK;

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Bundle b = intent.getExtras();
		if (b != null) {
			Messenger messenger = (Messenger) b.get("handler");
			Message msg = Message.obtain();
			msg.arg1 = result;
			try {
				messenger.send(msg);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

	}
}
