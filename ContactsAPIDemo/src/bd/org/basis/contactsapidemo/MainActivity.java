package bd.org.basis.contactsapidemo;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.provider.Browser;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnShow, btnAdd, btnUpdate, btnDelete, btnHistory;
	CallLogObserver observer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnShow = (Button) findViewById(R.id.btnShow);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnUpdate = (Button) findViewById(R.id.btnUpdate);
		btnDelete = (Button) findViewById(R.id.btnDelete);
		btnHistory = (Button) findViewById(R.id.btnShowHistory);
		btnShow.setOnClickListener(this);
		btnAdd.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);

		btnHistory.setOnClickListener(this);
		
		observer=new CallLogObserver(handler);

	}
	
	@Override
	protected void onStart() {
		super.onResume();
		ContentResolver cr = getContentResolver();
		cr.registerContentObserver(CallLog.Calls.CONTENT_URI, true, observer );
	}

	Handler handler=new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(getApplicationContext(), "Changes in CallLog", Toast.LENGTH_LONG).show();
		}
	};
	
	protected void onStop() {
		super.onStop();
		ContentResolver cr = getContentResolver();
		cr.unregisterContentObserver(observer);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnShow:
			showContacts();
			break;
		case R.id.btnAdd:
			addContact("Name1", "386875343");
			break;

		case R.id.btnUpdate:
			updateContact("Name", "0176584762");
			break;

		case R.id.btnDelete:
			deleteContact("Name");
			break;

		case R.id.btnShowHistory:
			// showBrowserHistory();
			showCallLog();
			break;
		default:
			break;
		}

	}

	private void showCallLog() {
		ContentResolver cr = getContentResolver();
		Cursor calllogCursor = cr.query(CallLog.Calls.CONTENT_URI, null, null,
				null, null);
		if (calllogCursor != null && calllogCursor.getCount() > 0) {
			while (calllogCursor.moveToNext()) {
				String number = calllogCursor.getString(calllogCursor
						.getColumnIndex(CallLog.Calls.NUMBER));
				String type = calllogCursor.getString(calllogCursor
						.getColumnIndex(CallLog.Calls.TYPE));
				long duration = calllogCursor.getLong(calllogCursor
						.getColumnIndex(CallLog.Calls.DURATION));
				Toast.makeText(getApplicationContext(),
						number + "----" + duration + "---" + type,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	private void showBrowserHistory() {
		ContentResolver cr = getContentResolver();
		Cursor browserCursor = cr.query(Browser.BOOKMARKS_URI,
				Browser.HISTORY_PROJECTION, null, null, null);
		if (browserCursor != null && browserCursor.getCount() > 0) {
			while (browserCursor.moveToNext()) {
				String url = browserCursor.getString(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.URL));
				String title = browserCursor.getString(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.TITLE));
				long time = browserCursor.getLong(browserCursor
						.getColumnIndex(Browser.BookmarkColumns.DATE));
				Toast.makeText(getApplicationContext(),
						title + "----" + url + "---" + time, Toast.LENGTH_LONG)
						.show();
			}
		}

	}

	private void showContacts() {
		ContentResolver cr = getContentResolver();
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, null, null, null);

		if (contactCursor != null && contactCursor.getCount() > 0) {
			while (contactCursor.moveToNext()) {
				String id = contactCursor.getString(contactCursor
						.getColumnIndex(ContactsContract.Contacts._ID));
				String name = contactCursor
						.getString(contactCursor
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				int hasPhoneNo = contactCursor
						.getInt(contactCursor
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

				if (hasPhoneNo > 0) {
					Cursor c = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=?", new String[] { id }, null);

					if (c != null && c.getCount() > 0) {
						while (c.moveToNext()) {
							String phoneNumber = c
									.getString(c
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							String type = c
									.getString(c
											.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));

							Toast.makeText(getApplicationContext(),
									name + "---" + phoneNumber + "----" + type,
									Toast.LENGTH_LONG).show();
						}
					}
				}

			}
		}

	}

	private void addContact(String name, String number) {
		ContentResolver cr = getContentResolver();
		Cursor contactCursor = cr.query(ContactsContract.Contacts.CONTENT_URI,
				null, ContactsContract.Contacts.DISPLAY_NAME + "=?",
				new String[] { name }, null);
		if (contactCursor != null && contactCursor.getCount() > 0) {
			Toast.makeText(
					getApplicationContext(),
					"User already exists. Do you want to update or add new contact?",
					Toast.LENGTH_LONG).show();
			return;
		}

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

		ContentProviderOperation op = ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)

				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE,
						"com.google")
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME,
						"account@gmail.com").build();

		ops.add(op);

		op = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
						name).build();

		ops.add(op);

		op = ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
				.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
						number).build();
		ops.add(op);

		try {
			cr.applyBatch(ContactsContract.AUTHORITY, ops);

			Toast.makeText(getApplicationContext(),
					"Contact " + name + " Added Successfully",
					Toast.LENGTH_LONG).show();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (OperationApplicationException e) {
			e.printStackTrace();
		}

	}

	private void deleteContact(String string) {
		// TODO Auto-generated method stub

	}

	private void updateContact(String string, String string2) {
		// TODO Auto-generated method stub

	}

}
