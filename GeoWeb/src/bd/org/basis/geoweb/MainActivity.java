package bd.org.basis.geoweb;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

@SuppressLint("JavascriptInterface")
public class MainActivity extends Activity implements LocationListener {

	private WebView webview;
	private LocationManager manager;
	private String provider;
	private Location currentLocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webview = (WebView) findViewById(R.id.webView1);

		webview.getSettings().setJavaScriptEnabled(true);
		webview.addJavascriptInterface(new Locator(), "locator");
		webview.loadUrl("file:///android_asset/geoweb.html");

		manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		provider = LocationManager.GPS_PROVIDER;
		currentLocation = manager.getLastKnownLocation(provider);

	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.requestLocationUpdates(provider, 5000, 10, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(Location location) {
		//
		currentLocation=location;
		
		String javascript=("javascript:whereami("+location.getLatitude()+","+location.getLongitude()+")");
		Log.e("JSCall", javascript);
		webview.loadUrl(javascript);

	}

	@Override
	public void onProviderDisabled(String arg0) {
	}

	@Override
	public void onProviderEnabled(String arg0) {
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	class Locator {
		public String getLocation() throws JSONException {
			if (currentLocation != null) {
				Location location=manager.getLastKnownLocation(provider);
				JSONObject obj = new JSONObject();
				obj.put("lat", location.getLatitude());
				obj.put("lng", location.getLongitude());
				String jsonStr = obj.toString();
				Log.e(getClass().getSimpleName(), jsonStr);
				return jsonStr;
			} else {
				return null;
			}

		}
	}

}
