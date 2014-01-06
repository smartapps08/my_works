package com.smartapps.holidaycalendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smartapps.helpers.BijoyFontUtil;

@SuppressLint("ValidFragment")
public class HelpDialog extends DialogFragment {

	private TextView txtLabel, txtWeekly, txtGeneral, txtExecutive,
			txtOptional;
	private LinearLayout layout;
	private Typeface tf;
	BijoyFontUtil tfUtil;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.help_layout, container);
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		txtLabel = (TextView) view.findViewById(R.id.txtCaption);
		txtWeekly = (TextView) view.findViewById(R.id.txtWeekly);
		txtGeneral = (TextView) view.findViewById(R.id.txtGeneral);
		txtExecutive = (TextView) view.findViewById(R.id.txtExecutive);
		txtOptional = (TextView) view.findViewById(R.id.txtOptional);
		tf = Typeface.createFromAsset(getActivity().getAssets(),
				"font/suttony.ttf");
		tfUtil = new BijoyFontUtil();
		txtLabel.setTypeface(tf);
		txtWeekly.setTypeface(tf);
		txtGeneral.setTypeface(tf);
		txtExecutive.setTypeface(tf);
		txtOptional.setTypeface(tf);
		txtWeekly.setTextColor(Color.BLACK);
		txtGeneral.setTextColor(Color.BLACK);
		txtExecutive.setTextColor(Color.BLACK);
		txtOptional.setTextColor(Color.BLACK);

		String strLabel = "সাপ্তাহিক ছুটি";
		String strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtWeekly.setText(strLabelEncoded);

		strLabel = "সাধারণ ছুটি";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtGeneral.setText(strLabelEncoded);

		strLabel = "নির্বাহী আদেশে সরকারি ছুটি";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtExecutive.setText(strLabelEncoded);

		strLabel = "ঐচ্ছিক ছুটি (ধর্মীয়)";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtOptional.setText(strLabelEncoded);

		strLabel = "সাহায্য";
		strLabelEncoded = tfUtil.convertUnicode2BijoyString(strLabel);
		txtLabel.setText(strLabelEncoded);
		return view;
	}

}
