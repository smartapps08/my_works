package com.smartapps.watchdog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {
	private static final String TAG = "CameraDemo";
	Camera camera;
	SharedPreferences pref;
	String emailaddress, password;
	CameraPreview preview;
	Button buttonClick;
	public static String filenames = "WatchDog";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cameraxml);

		pref = getSharedPreferences(filenames, 0);

		emailaddress = pref.getString("keyemail", "");

		Log.d("Email", emailaddress);
		preview = new CameraPreview(this);
		((FrameLayout) findViewById(R.id.preview)).addView(preview);

		handler.sendEmptyMessageDelayed(0, 3000);
		Log.d(TAG, "onCreate'd");
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			preview.camera.takePicture(shutterCallback, rawCallback,
					jpegCallback);

		};
	};

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw");
		}
	};

	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {

				StoreByteImage(data, 50, "ImageName");

			} catch (Exception e) {
				Log.d(TAG, "Exception--------------------1");
				e.printStackTrace();
			}
			Log.d(TAG, "onPictureTaken - jpeg");
			camera.release();
			finish();
		}
	};

	public boolean StoreByteImage(byte[] imageData, int quality, String expName) {

		File sdImageMainDirectory = Environment.getExternalStorageDirectory();
		// new File("/sdcard"); //
		FileOutputStream fileOutputStream = null;
		String nameFile;
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 5;
			Bitmap myImage = BitmapFactory.decodeByteArray(imageData, 0,
					imageData.length, options);
			fileOutputStream = new FileOutputStream(
					sdImageMainDirectory.toString() + "/jam.jpg");
			BufferedOutputStream bos = new BufferedOutputStream(
					fileOutputStream);

			myImage.compress(CompressFormat.JPEG, quality, bos);

			bos.flush();
			bos.close();

			// image attachment code with email sent

			SendMail();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "Exception--------------------1");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.d(TAG, "Exception--------------------1");
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	public void SendMail() throws MessagingException {

		String host = "smtp.gmail.com";
		String Password = "phonewatchdog";
		String from = "phonewatchdog@gmail.com";
		String toAddress = emailaddress;
		String filename = Environment.getExternalStorageDirectory()
				+ "/jam.jpg";
		// Get system properties
		Properties props = System.getProperties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtps.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getInstance(props, null);

		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from));

		message.setRecipients(Message.RecipientType.TO, toAddress);

		message.setSubject("JavaMail Attachment");

		BodyPart messageBodyPart = new MimeBodyPart();

		messageBodyPart.setText("Here's the file");

		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		DataSource source = new FileDataSource(filename);

		messageBodyPart.setDataHandler(new DataHandler(source));

		messageBodyPart.setFileName(filename);

		multipart.addBodyPart(messageBodyPart);

		message.setContent(multipart);

		try {
			Transport tr = session.getTransport("smtps");
			tr.connect(host, from, Password);
			tr.sendMessage(message, message.getAllRecipients());
			System.out.println("Mail Sent Successfully");
			tr.close();

		} catch (SendFailedException sfe) {

			System.out.println(sfe);
		}
	}

	;

}
