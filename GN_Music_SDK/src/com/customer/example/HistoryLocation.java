/* Gracenote Android Music SDK Sample Application
 *
 * Copyright (C) 2010 Gracenote, Inc. All Rights Reserved.
 */
package com.customer.example;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * 
 * <p>This class render all location on which made search.
 *
 */
public final class HistoryLocation extends MapActivity{
	List<Locations> locatorList = new  ArrayList<Locations>();
	public static final String TAG = "Gracenote";	
	public List<Locations> getLocatorList() {
		return locatorList;
	}
	
	public void setLocatorList(List<Locations> locatorList) {
		this.locatorList = locatorList;
	}
	
	String[] rangeArray;
	
	TextView titleText;
	ListView branchListView;
	ProgressDialog progressDialog;
	boolean isFisrtTimeLocation=true;
	DatabaseAdapter db = null; 
	List<Locations> locList = new ArrayList<Locations>(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		db = new DatabaseAdapter(HistoryLocation.this);
		db.open();
		Cursor c = db.getLocations();
		//System.out.println("locaton count is :"+c.getCount());
		
		c.moveToFirst();
		do{
			Locations l = new Locations();
			l.ID = c.getString(0);
			l.ArtistName = c.getString(1);
			l.AlbumName = c.getString(2);
			l.Latitude = c.getString(3);
			l.Longitude = c.getString(4);
			locList.add(l);
		}while(c.moveToNext());
		setLocatorList(locList);
		c.close();
		for(int i = 0; i < locList.size() ;i ++){
			//System.out.println("[" + i +"] is lat :" + locList.get(i).Latitude +"long :"+locList.get(i).Longitude);
		}
		setOverlaysOnMap();
		db.close();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	public void finish() {
		super.finish();
	}
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if(keyCode==KeyEvent.KEYCODE_BACK) {
    		finish();
    	}
     return super.onKeyDown(keyCode, event);
    }

	private void setOverlaysOnMap() {
		ArrayList<MapLocation> gpList = new ArrayList<MapLocation>();
		for (int i = 0; i < locList.size(); i++) {
			try {
				gpList.add(new MapLocation(locList.get(i).AlbumName, Double
						.parseDouble(locList.get(i).Latitude), Double
						.parseDouble(locList.get(i).Longitude)));	
			} catch (Exception e) {
				Log.e("GN_HISTORY_ERROR", "Number format exception in history location.");
			}
		}

		LocationFinder ticker = new LocationFinder(this);
		ticker.setContext(HistoryLocation.this);
		ticker.setMapZoomControls(true);
		ticker.setMapZoomLevel(11);
		ticker.setmapOverlay();
		ticker.setSatelliteView(false);
		ticker.setItemisedOverlay();
		ticker.invalidatemap();
		try {
			for (int count = 0; count < gpList.size(); count++)
				ticker.createOverlayItem(gpList.get(count).getPoint(), gpList.get(count).getName());
		} catch (Exception exception) {
			Log.e(TAG, "Failed to se overlays on map - "+ exception.getMessage());
		}
	}
	
	/**
	 * LocationFinder is a generalized class which can be used as plug-in 
	 * to put overlay item on the map in any application.
	 * It have few setters & getters method to set parameter to Google map to place
	 * overlay item.
	 * It must set latitude, longitude, map Ticker Header and Ticker description.
	 * In the need of zoom control on screen, It should pass pass true to setMapZoomControls,
	 * because by default setBuiltInZoomControls is false.
	 * Zooming level can be change using setZoomLevel() method. 
	 * Use generateGeoPoint method to create a GeoPoint using lat.-long.
	 * 
	 * 
	 */
	private final class LocationFinder {
		private HistoryLocation historyLocator;
		private MapView mapView=null;
		private MapController mapController=null;
		private Context context;
		// We need to create a list of item to be placed on the map.
		private List<Overlay> mapOverlays;
		private MapItemizedOverlay itemizedoverlay;
		private Drawable drawable;
		// This is our geo-point, where we will place our item on map.
		private GeoPoint point;
		
		private int lat, lon ;
		private String mapHeader, mapDesc;

		public void setContext(Context context){
			this.context = context;
		}
		/**
		 * Constructor will create a map instance as well as map controller instance.
		 */
		public LocationFinder(HistoryLocation serviceCenterLocator){
			this.historyLocator = serviceCenterLocator;
			// MapView will show a map on screen.
			mapView = (MapView) this.historyLocator.findViewById(R.id.myMapView1);
			// we need controller foe event on map
			mapController = mapView.getController();
		}
		/**
		 * get header from the map overlay 
		 */
		public String getMapHeader() {
			return mapHeader;
		}

		/**
		 * set header for the map overlay 
		 */
		public void setMapHeader(String mapHeader) {
			this.mapHeader = mapHeader;
		}

		
		/**
		 * get description for the map overlay 
		 */
		public String getMapDesc() {
			return mapDesc;
		}
		
		/**
		 * set description for the map overlay 
		 */
		public void setMapDesc(String mapDesc) {
			this.mapDesc = mapDesc;
		}

		/**
		 * get latitude for GeoPoint 
		 * @return int - latitude
		 */
		
		public int getLat() {
			return lat;
		}

		/**
		 * set latitude for GeoPoint
		 * @param latitude
		 * @return void
		 */
		public void setLat(int lat) {
			this.lat = lat;
		}

		/**
		 * get longitude for GeoPoint
		 * @return int
		 */
		public int getLon() {
			return lon;
		}

		/**
		 * set longitude for GeoPoint
		 * @param longitude
		 * @return void
		 */
		public void setLon(int lon) {
			this.lon = lon;
		}
		
		/**
		 * true  - if you want default zoom control
		 * false - otherwise.
		 * @param flag
		 * @return void
		 */
		public void setMapZoomControls(boolean flag){
			this.mapView.setBuiltInZoomControls(flag);
		}
		
		/**
		 * Set the satellite view if flag is true.
		 * @param flag
		 * @return void
		 */
		public void setSatelliteView(boolean flag){
			mapView.setSatellite(flag);
		}
		
		/**
		 * Sets the zoom level of the map. 
		 * The value will be clamped to be between 1 and 21 inclusive.
		 * @param zoom
		 * @return void 
		 */
		public void setMapZoomLevel(int zoom){
			this.mapController.setZoom(zoom);
		}
		
		/**
		 * Get a GeoPoint using provided lat.-long.
		 * @param latitude
		 * @param longitude
		 */
		public void generateGeoPoint(int lat, int lon){
			point = new GeoPoint(lat, lon);
		}
		
		public void setGeoPoint(GeoPoint geo){
			this.point = geo;
			
		}
		/**
		 * It place a ticker on map using OverlayItem
		 * @see GeoPoint, MapController, Overlay, OverlayItem
		 */
		public void setItemisedOverlay(){
			this.itemizedoverlay = new MapItemizedOverlay(drawable,context);
		}
		public void setmapOverlay(){
			mapOverlays = mapView.getOverlays();	
			drawable = this.context.getResources().getDrawable(R.drawable.pindown);
		}
		public void createOverlayItem(GeoPoint gp,String Name){
			
			OverlayItem overlayitem = new OverlayItem(gp, Name,null);
			mapController.animateTo(gp);
			mapOverlays.add(itemizedoverlay);
			itemizedoverlay.addOverlay(overlayitem);
		}
		public void invalidatemap(){
			this.mapView.invalidate();
			mapOverlays.removeAll(mapOverlays);
		}
		public void getMapWithOverlay() {
			try {
		        // get a overlay from map view.
				mapOverlays = mapView.getOverlays();
				drawable = this.context.getResources().getDrawable(R.drawable.pindown);
				itemizedoverlay = new MapItemizedOverlay(drawable,this.context);
				mapController.animateTo(point);
				OverlayItem  overlayitem = new OverlayItem(point, mapHeader, mapDesc);
				itemizedoverlay.addOverlay(overlayitem);
				mapOverlays.add(itemizedoverlay);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean onCreateOptionsMenu(Menu menu) {
			menu.add(0, 0, 0, "open Dialog");
			return true;
		}
		public boolean onMenuItemSelected(int featureId, MenuItem item) {
			switch (item.getItemId()) {
			 case 0:{
				 break;
			 }
		 }
		return true;
		}
	}

	private final class MapItemizedOverlay extends com.google.android.maps.ItemizedOverlay {
		Context mContext;
		Dialog dialog;
		boolean flag = false;
		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();

		/**
		 * Constructor
		 * @param defaultMarker
		 * @param context
		 */
		public MapItemizedOverlay(Drawable defaultMarker,Context context) {
			super(boundCenterBottom(defaultMarker));
			mContext = context;
		}
		/**
		 * add a overlay item to the overlay arraylist.
		 * @param overlayitem
		 */
		public void addOverlay(OverlayItem overlayitem) {
			mOverlays.add(overlayitem);
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}
		protected boolean onTap(final int index){
			
			try {
				OverlayItem item = mOverlays.get(index);
				dialog = new Dialog(mContext);
				dialog.setContentView(R.layout.custom_dialog);
				dialog.setTitle(item.getTitle());
				TextView text = (TextView) dialog.findViewById(R.id.artist_name);
				text.setText(""+getLocatorList().get(index).ArtistName);
				dialog.show();	
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}	
	}
	
	private final class Locations {
		String ID;
		String ArtistName;
		String AlbumName;
		String Latitude;
		String Longitude;
	}
	
	private final class MapLocation {
		GeoPoint point;
		String name;
		public MapLocation(String name,double latitude, double longitude) {
			this.name = name;
			point = new GeoPoint((int)(latitude*1e6),(int)(longitude*1e6));
		}

		public GeoPoint getPoint() {
			return point;
		}

		public String getName() {
			return name;
		}
	}
}