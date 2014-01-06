package bd.org.basis.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ImageView imgPreview;
	private static final int CAPTURE_PHOTO = 1;
	private Uri originalUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		imgPreview = (ImageView) findViewById(R.id.imgPreview);
	}

	public void capturePhoto(View v) {
		File file = new File(Environment.getExternalStorageDirectory(),
				System.currentTimeMillis() + ".jpg");
		originalUri = Uri.fromFile(file);

		Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, originalUri);
		startActivityForResult(captureIntent, CAPTURE_PHOTO);
	}

	public void uploadPhoto(View v) {
		new UploadThread().start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_PHOTO) {
			if (resultCode == RESULT_OK) {
				if (originalUri != null) {
					BitmapFactory.Options ops = new BitmapFactory.Options();
					ops.inJustDecodeBounds = true;
					BitmapFactory.decodeFile(originalUri.getPath(), ops);

					int height = ops.outHeight;
					int width = ops.outWidth;

					int scalefactor = Math.min(width / 320, height / 240);

					ops.inJustDecodeBounds = false;
					ops.inSampleSize = scalefactor;
					ops.inPurgeable = true;

					Bitmap bmp = BitmapFactory.decodeFile(
							originalUri.getPath(), ops);
					imgPreview.setImageBitmap(bmp);
				}
			} else {

			}
		}
	}

	class UploadThread extends Thread {
		public void run() {
			// image convert to base64 encoded data
			if (originalUri != null) {
				// image file-> bitmap
				BitmapFactory.Options ops = new BitmapFactory.Options();
				ops.inPreferredConfig = Bitmap.Config.ARGB_8888;
				ops.inSampleSize = 8;

				Bitmap bmp = BitmapFactory.decodeFile(originalUri.getPath(),
						ops);

				// bitmap-> bytearrayoutputstream
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);

				// bytearrayoutputstream-> byte[]
				byte[] ba = baos.toByteArray();

				String encodedData = Base64.encodeBytes(ba);

				// post
				DefaultHttpClient client = new DefaultHttpClient();
				HttpPost postReq = new HttpPost(
						"http://10.0.2.2/online_library/upload.php");
				List params = new ArrayList();
				params.add(new BasicNameValuePair("image", encodedData));
				try {
					postReq.setEntity(new UrlEncodedFormEntity(params));
					HttpResponse resp = client.execute(postReq);
					if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						handler.sendEmptyMessage(1);
					} else {
						handler.sendEmptyMessage(0);
					}

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(0);
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(0);
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					handler.sendEmptyMessage(0);
					e.printStackTrace();
				}

			}

			// upload
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				Toast.makeText(getApplicationContext(), "Upload Failed",
						Toast.LENGTH_LONG).show();
				break;
			case 1:
				Toast.makeText(getApplicationContext(), "Upload Successful",
						Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
		}
	};

}
