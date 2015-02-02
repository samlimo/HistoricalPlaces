package com.ftfl.historicalplaces.map;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ftfl.historicalplaces.database.Historical;
import com.ftfl.historicalplaces.database.HistoricalDataSource;
import com.ftfl.mylastvisiting.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class ShowMapActivity extends FragmentActivity implements
		LocationListener {
	GoogleMap mGoogleMap;
	HistoricalDataSource mShoppingComplexDS = null;
	Historical mUpdateShoppingComplex = null;

	String mID = "";
	Long mLId = null;
	String mAddress = "";
	String mLatitude = "";
	String mLongitude = "";
	public final static String ID = "id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_map);

		Intent mActivityIntent = getIntent();
		mID = mActivityIntent.getStringExtra(ID);

		if (mID != null) {
			mLId = Long.parseLong(mID);

			/*
			 * get the activity which include all data from database according
			 * profileId of the clicked item.
			 */
			mShoppingComplexDS = new HistoricalDataSource(this);
			mUpdateShoppingComplex = mShoppingComplexDS.singlePlaceData(mLId);

			mAddress = mUpdateShoppingComplex.getmAddress();
			mLatitude = mUpdateShoppingComplex.getmLatitute();
			mLongitude = mUpdateShoppingComplex.getmLongitude();
		}

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			try {
				// Loading map
				initilizeMap();

			} catch (Exception e) {
				e.printStackTrace();
			}

			// Enabling MyLocation Layer of Google Map
			mGoogleMap.setMyLocationEnabled(true);

			try {
				LocationManager locMgr = (LocationManager) this
						.getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				String locProvider = locMgr.getBestProvider(criteria, false);
				Location location = locMgr.getLastKnownLocation(locProvider);

				// getting GPS status
				boolean isGPSEnabled = locMgr
						.isProviderEnabled(LocationManager.GPS_PROVIDER);
				// getting network status
				boolean isNWEnabled = locMgr
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

				if (isGPSEnabled && isNWEnabled)

				{
					if (isNWEnabled)
						if (locMgr != null)
							location = locMgr
									.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (isGPSEnabled)
						if (location == null)
							if (locMgr != null)
								location = locMgr
										.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				}
				onLocationChanged(location);
				// locMgr.requestLocationUpdates(provider, 20000, 0, this);
			} catch (NullPointerException ne) {
				Log.e("Current Location", "Current Lat Lng is Null");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * function to load map. If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (mGoogleMap == null) {
			mGoogleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (mGoogleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onLocationChanged(Location location) {

		// Getting latitude of the current location
		double latitude = location.getLatitude();

		// Getting longitude of the current location
		double longitude = location.getLongitude();

		// Creating a LatLng object for the current location
		LatLng latLng = new LatLng(latitude, longitude);

		Double dLatitude = Double.parseDouble(mLatitude);
		Double dLongitude = Double.parseDouble(mLongitude);

		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(dLatitude, dLongitude)).title(mAddress);
		marker.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		mGoogleMap.addMarker(marker);

		String url = makeURL(latitude, longitude, dLatitude, dLongitude);
		connectAsyncTask myTask = new connectAsyncTask(url);
		myTask.execute();

		mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	/*
	 * make URL for getting data which is use for draw path.
	 */

	public String makeURL(double sourcelat, double sourcelog, double destlat,
			double destlog) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");// from
		urlString.append(Double.toString(sourcelat));
		urlString.append(",");
		urlString.append(Double.toString(sourcelog));
		urlString.append("&destination=");// to
		urlString.append(Double.toString(destlat));
		urlString.append(",");
		urlString.append(Double.toString(destlog));
		urlString.append("&sensor=false&mode=driving&alternatives=true");
		return urlString.toString();
	}

	/*
	 * Draw path from current location to destination.
	 */

	public void drawPath(String result) {

		try {
			// Transform the string into a json object
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				@SuppressWarnings("unused")
				Polyline line = mGoogleMap.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(2).color(Color.BLUE).geodesic(true));
			}

		} catch (JSONException e) {

		}
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	private class connectAsyncTask extends AsyncTask<Void, Void, String> {
		private ProgressDialog progressDialog;
		String url;

		connectAsyncTask(String urlPass) {
			url = urlPass;
		}

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			progressDialog = new ProgressDialog(ShowMapActivity.this);
			progressDialog.setMessage(getString(R.string.fetchingRoute));
			progressDialog.setIndeterminate(true);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(Void... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(url);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progressDialog.hide();
			if (result != null) {
				drawPath(result);
			}
		}
	}
}