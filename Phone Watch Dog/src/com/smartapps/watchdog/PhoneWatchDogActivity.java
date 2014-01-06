package com.smartapps.watchdog;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smartapps.watchdog.helper.ContactListAdapter;

public class PhoneWatchDogActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	AutoCompleteTextView actv1, actv2, actv3, actv4, actv5;
	EditText emailaddress, password;
	String getnumber1, getnumber2, getnumber3, getnumber4, getnumber5,
			getserial, getemail, getpassword, sSimSerial;
	Cursor cursor;
	TextView mailsave, scontact1, scontact2, scontact3, scontact4, scontact5;
	TelephonyManager telephonyManager;
	public static String filenames = "WatchDog";
	Button save;
	SharedPreferences pref;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact);
		initializer();
		String emailaddress = pref.getString("keyemail", "");
		String contact1 = pref.getString("key1", "");
		String contact2 = pref.getString("key2", "");
		String contact3 = pref.getString("key3", "");
		String contact4 = pref.getString("key4", "");
		String contact5 = pref.getString("key5", "");
		mailsave.setText(emailaddress);
		scontact1.setText(contact1);
		scontact2.setText(contact2);
		scontact3.setText(contact3);
		scontact4.setText(contact4);
		scontact5.setText(contact5);
		ContactListAdapter adapter = new ContactListAdapter(this, cursor);
		ContentResolver content = getContentResolver();
		Cursor cursor = content.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
				adapter.CONTACT_PROJECTION, null, null, null);

		actv1.setAdapter(adapter);
		actv2.setAdapter(adapter);
		actv3.setAdapter(adapter);
		actv4.setAdapter(adapter);
		actv5.setAdapter(adapter);

		actv1.setThreshold(0);
		actv2.setThreshold(0);
		actv3.setThreshold(0);
		actv4.setThreshold(0);
		actv5.setThreshold(0);
		save.setOnClickListener(this);
	}

	private void initializer() {
		actv1 = (AutoCompleteTextView) findViewById(R.id.autoone);
		actv2 = (AutoCompleteTextView) findViewById(R.id.autotwo);
		actv3 = (AutoCompleteTextView) findViewById(R.id.autothree);
		actv4 = (AutoCompleteTextView) findViewById(R.id.autofour);
		actv5 = (AutoCompleteTextView) findViewById(R.id.autofive);
		mailsave = (TextView) findViewById(R.id.txtsave);
		scontact1 = (TextView) findViewById(R.id.showcontacgtone);
		scontact2 = (TextView) findViewById(R.id.showcontacttwo);
		scontact3 = (TextView) findViewById(R.id.showcontactthree);
		scontact4 = (TextView) findViewById(R.id.showcontactfour);
		scontact5 = (TextView) findViewById(R.id.showcontactfive);

		emailaddress = (EditText) findViewById(R.id.emailone);
		save = (Button) findViewById(R.id.btn_save);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		pref = getSharedPreferences(filenames, 0);

	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_save:
			mailsave.setText("");
			scontact1.setText("");
			scontact2.setText("");
			scontact3.setText("");
			scontact4.setText("");
			scontact5.setText("");
			final String sSimSerial = telephonyManager.getSimSerialNumber();
			Toast.makeText(getApplicationContext(), "Contact Added",
					Toast.LENGTH_LONG).show();
			getnumber1 = actv1.getText().toString();
			getnumber2 = actv2.getText().toString();
			getnumber3 = actv3.getText().toString();
			getnumber4 = actv4.getText().toString();
			getnumber5 = actv5.getText().toString();
			getemail = emailaddress.getText().toString();

			mailsave.setText(getemail);
			scontact1.setText(getnumber1);
			scontact2.setText(getnumber2);
			scontact3.setText(getnumber3);
			scontact4.setText(getnumber4);
			scontact5.setText(getnumber5);

			SharedPreferences.Editor edit = pref.edit();
			edit.putString("key1", getnumber1);
			edit.putString("key2", getnumber2);
			edit.putString("key3", getnumber3);
			edit.putString("key4", getnumber4);
			edit.putString("key5", getnumber5);
			edit.putString("keyemail", getemail);

			edit.putString("sim", sSimSerial);
			edit.commit();

			break;

		}

	}
}