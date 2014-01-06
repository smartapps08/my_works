/* Gracenote Android Music SDK Sample Application
 *
 * Copyright (C) 2010 Gracenote, Inc. All Rights Reserved.
 */
package com.customer.example;

import java.io.ByteArrayInputStream;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BioReviewActivity extends Activity{


	TextView trackTxt, artistTxt, albumTxt, bioReviewTxt;
	ImageView coverArtImage;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bio_review);

		LinearLayout layout = (LinearLayout) findViewById(R.id.TextLayout);
		layout.setVisibility(View.VISIBLE);
		bioReviewTxt = (TextView)findViewById(R.id.bioReviewtxt);
		bioReviewTxt.setVisibility(View.VISIBLE);

		showBioReview();

	}

	/**
	 * This method will set UI parameters for Album Review.
	 */
	private void showBioReview() {
		coverArtImage 	= (ImageView) findViewById(R.id.CoverArtImage);
		trackTxt   		= (TextView) findViewById(R.id.TrackTextView);
		artistTxt  		= (TextView) findViewById(R.id.ArtistTextView);
		albumTxt   		= (TextView) findViewById(R.id.AlbumTextView);

		//We require artist & album only
		trackTxt.setVisibility(View.GONE);
		artistTxt.setVisibility(View.VISIBLE);
		albumTxt.setVisibility(View.VISIBLE);

		String type = getIntent().getExtras().getString("type");
		if (type.equalsIgnoreCase("bio")){
			artistTxt.setVisibility(View.GONE);
			albumTxt.setVisibility(View.GONE);
		}else{
			artistTxt.setVisibility(View.VISIBLE);
			albumTxt.setVisibility(View.VISIBLE);
		}

		// Set Artist Text
		if (getIntent().getExtras().getString("artist") != null ){
			artistTxt.setText(getIntent().getExtras().getString("artist"));	
		}else{
			artistTxt.setVisibility(View.GONE);
		}

		// Set Album Text
		if (getIntent().getExtras().getString("album") != null ){
			albumTxt.setText(getIntent().getExtras().getString("album"));	
		}else{
			albumTxt.setVisibility(View.GONE);
		}

		if (getIntent().getExtras().getString("data") != null  && getIntent().getExtras().getString("data").length()>0){
			bioReviewTxt.setText(getIntent().getExtras().getString("data"));	
		}else{
			if (type.equalsIgnoreCase("bio")){
				bioReviewTxt.setText("No artist details available.");
			}else{
				bioReviewTxt.setText("No album review available.");
			}
		}
		// Set Cover Art Image
		try {
			if (getIntent().getExtras().getByteArray("cover_art") != null 
					&& getIntent().getExtras().getByteArray("cover_art").length>0){
				ByteArrayInputStream is = new ByteArrayInputStream(getIntent().getExtras()
						.getByteArray("cover_art"));
				//Drawable coverArt = Drawable.createFromStream(is, "src");
				Drawable coverArt;
				try {
					 coverArt = Drawable.createFromStream(is, "src");	
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
					coverArt = null;
				}
				if (coverArt != null) {
					coverArtImage.setVisibility(View.VISIBLE);
					coverArtImage.setImageDrawable(coverArt);
				} else {
					coverArtImage.setVisibility(View.GONE);
				}
			}else{
				coverArtImage.setVisibility(View.GONE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			coverArtImage.setVisibility(View.GONE);
		}
	}
}
