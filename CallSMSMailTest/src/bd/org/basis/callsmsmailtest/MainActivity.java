package bd.org.basis.callsmsmailtest;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void call(View v) {
		String number = "01731541394";
		// uri format tel:911
		String uriStr = "tel:" + number;
		Uri callUri = Uri.parse(uriStr);
		// Intent callIntent = new Intent(Intent.ACTION_DIAL, callUri);
		Intent callIntent = new Intent(Intent.ACTION_CALL, callUri);
		startActivity(callIntent);
	}

	public void sendSMS(View v) {
		String number = "01731541394";
		String uriStr = "sms:" + number;
		Uri smsUri = Uri.parse(uriStr);
		Intent smsIntent = new Intent(Intent.ACTION_VIEW, smsUri);
		smsIntent.putExtra("sms_body", "This is an SMS");
		startActivity(smsIntent);
	}

	public void sendEmail(View v) {
		String email = "smartapps08@gmail.com";
		String subject = "From Application";
		String body = "This is email body";

		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { email });
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		emailIntent.putExtra(Intent.EXTRA_TEXT, body);
		startActivity(Intent.createChooser(emailIntent, ""));

		
	}
}
