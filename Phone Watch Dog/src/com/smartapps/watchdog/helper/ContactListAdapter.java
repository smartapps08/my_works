package com.smartapps.watchdog.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.TextView;

public class ContactListAdapter extends CursorAdapter implements Filterable {
    public ContactListAdapter(Context context, Cursor c) {
        super(context, c);
        mContent = context.getContentResolver();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final TextView view = (TextView) inflater.inflate(
                android.R.layout.simple_dropdown_item_1line, parent, false);
        view.setText(cursor.getString(1));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view).setText(cursor.getString(1));
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(2);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence constraint) {
        FilterQueryProvider filter = getFilterQueryProvider();
        if (filter != null) {
            return filter.runQuery(constraint);
        }

        Uri uri = Uri.withAppendedPath(
                ContactsContract.CommonDataKinds.Phone.CONTENT_FILTER_URI,
                Uri.encode(constraint.toString()));
        return mContent.query(uri, CONTACT_PROJECTION, null, null, null);
    }

    private ContentResolver mContent;
    
    
    public static final String[] CONTACT_PROJECTION = new String[] {
        ContactsContract.CommonDataKinds.Phone._ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Phone.NUMBER
        
       
    };
    
}




