package com.smartapp.saveimagewithtext;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText etComment;
	ImageView imageView;
	Button btnSave,btnShow;
	String comment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etComment = (EditText) findViewById(R.id.etComment);
		imageView = (ImageView) findViewById(R.id.imageView);
		btnSave = (Button) findViewById(R.id.btnSave);
		btnShow = (Button) findViewById(R.id.btnShow);
		
		btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				comment = etComment.getText().toString();
				saveImage(comment);
				
			}
		});
		
		btnShow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				File imgFile = new File(Environment.getExternalStorageDirectory()
						+ "/Saved_Images/myImage.jpg");
				Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
				imageView.setImageBitmap(bitmap);
			}
		});
		
		
	}

	void saveImage(String comment) {
	    File myDir=new File(Environment
				.getExternalStoragePublicDirectory("Saved_Images").toString());
	    myDir.mkdirs();
//	    Random generator = new Random();
//	    int n = 10000;
//	    n = generator.nextInt(n);
	    String fname = "myImage.jpg";
	    File file = new File (myDir, fname);
	    if (file.exists ()) file.delete ();
	    try {
	        FileOutputStream out = new FileOutputStream(file);

	        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.test);
	        Bitmap alteredBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
	        Canvas canvas = new Canvas(alteredBitmap);
	        Paint paint = new Paint();
	        canvas.drawBitmap(bm, 0, 0, paint);
	        paint.setColor(Color.WHITE); 
	        paint.setTextSize(50);
	        paint.setTextScaleX((float) 1.5);
	        canvas.drawText(comment, 10, 100, paint); 

	            alteredBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//	        originalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
	        out.flush();
	        out.close();
	        Toast.makeText(MainActivity.this, "Image saved in mnt/sdCard/Saved_Images/", Toast.LENGTH_LONG).show();
	        btnShow.setVisibility(View.VISIBLE);
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
