package com.smartapps.banglatransliteration;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartapps.avro.CustomLoader;
import com.smartapps.avro.PhoneticParser;
import com.smartapps.helpers.BijoyFontUtil;

public class MainActivity extends Activity {
	private EditText etTest;
	private TextView txtOutput;
	private Typeface tf;
	private BijoyFontUtil tfUtil;
	private PhoneticParser avro;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageView = (ImageView) findViewById(R.id.imageView);
		etTest = (EditText) findViewById(R.id.etTest);
		txtOutput = (TextView) findViewById(R.id.txtOutput);
		tf = Typeface.createFromAsset(getAssets(), "font/suttony.ttf");
		tfUtil = new BijoyFontUtil();
		etTest.setTypeface(tf);
		txtOutput.setTypeface(tf);
		try {
			avro = PhoneticParser.getInstance();
			avro.setLoader(new CustomLoader(this));
			// avro.setLoader(new PhoneticXmlLoader(this));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something very unholy has just happened :|");
		}

		etTest.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String strLabel = avro.parse(etTest.getText().toString());
				String strLabelEncoded = tfUtil
						.convertUnicode2BijoyString(strLabel);
				txtOutput.setText(strLabelEncoded);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable e) {

			}
		});
	}

	public void check(View v) {
		String strLabel = avro.parse(etTest.getText().toString());
		Log.e("Converted", strLabel);
		String strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		etTest.setText(strLabelEncoded);
		txtOutput.setText(strLabel);
	}

	public void generate(View v) {
		saveImage(etTest.getText().toString());
		File imgFile = new File(Environment.getExternalStorageDirectory()
				+ "/Saved_Images/myImage.jpg");
		Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		imageView.setImageBitmap(bitmap);
		
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
	    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    shareIntent.setType("image/*");

	    // For a file in shared storage.  For data in private storage, use a ContentProvider.
	   
	    Uri uri = Uri.fromFile( new File(Environment.getExternalStorageDirectory() + "/Saved_Images/myImage.jpg"));
	    shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
	    startActivity(shareIntent);
	}

	void saveImage(String comment) {
		File myDir = new File(Environment.getExternalStoragePublicDirectory(
				"Saved_Images").toString());
		myDir.mkdirs();
		// Random generator = new Random();
		// int n = 10000;
		// n = generator.nextInt(n);
		String fname = "myImage.jpg";
		File file = new File(myDir, fname);
		if (file.exists())
			file.delete();
		try {
			FileOutputStream out = new FileOutputStream(file);

			Bitmap bm = BitmapFactory.decodeResource(getResources(),
					R.drawable.test);
			Bitmap alteredBitmap = Bitmap.createBitmap(bm.getWidth(),
					bm.getHeight(), bm.getConfig());
			Canvas canvas = new Canvas(alteredBitmap);
			Paint paint = new Paint();
			canvas.drawBitmap(bm, 0, 0, paint);
			paint.setColor(Color.BLACK);
			paint.setTypeface(tf);
			paint.setTextSize(50);
			paint.setTextScaleX((float) 4.0);
			canvas.drawText(comment, 10, 100, paint);
			//
			paint.setColor(Color.WHITE);
			paint.setStyle(Style.STROKE);
			paint.setTypeface(tf);
			paint.setTextSize(50);
			paint.setStrokeWidth(1);
			paint.setTextScaleX((float) 4.0);
			canvas.drawText(comment, 10, 100, paint);

			alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			// originalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
