package com.the.greatschools;

import android.app.Activity;
import android.os.Bundle;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends Activity {
	private GoogleMap map;
	private MapView mapview;
	private JSONObject mBD;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail);

		TextView name = (TextView) findViewById(R.id.name);
		TextView phone = (TextView) findViewById(R.id.phone);
		TextView url = (TextView) findViewById(R.id.url);
		TextView percent = (TextView) findViewById(R.id.percent);
		TextView allsales = (TextView) findViewById(R.id.allSales);
		ImageView mon = (ImageView) findViewById(R.id.mon_image);
		ImageView tue = (ImageView) findViewById(R.id.tue_image);
		ImageView wed = (ImageView) findViewById(R.id.wed_image);
		ImageView thur = (ImageView) findViewById(R.id.thur_image);
		ImageView fri = (ImageView) findViewById(R.id.fri_image);
		Button address = (Button) findViewById(R.id.address);
		mapview = (MapView) findViewById(R.id.map);
		mapview.onCreate(savedInstanceState);
		TextView note = (TextView) findViewById(R.id.notes);
		

		try {
	        mBD = new JSONObject(getIntent().getStringExtra("business"));        

			// set the detail section
			name.setText(mBD.getString("Name"));
			phone.setText(mBD.getString("Phone Number"));
			url.setText(mBD.getString("Website"));
			percent.setText(mBD.getString("Percent String"));
			if (mBD.getString("All Sales").contentEquals("Yes"))
				allsales.setText(getResources().getString(R.string.allsales));
			
			// set the days section
			if (mBD.getString("Monday").contentEquals("1")) {
				mon.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getString("Tuesday").contentEquals("1")) {
				tue.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getString("Wednesday").contentEquals("1")) {
				wed.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getString("Thursday").contentEquals("1")) {
				thur.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getString("Friday").contentEquals("1")) {
				fri.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			
			// setup the map
			address.setText(mBD.getString("Address"));
			address.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			        // Do something in response to button click
			    	String url=null;
					try {
						url = "http://maps.google.com/maps?daddr="+mBD.getDouble("Latitude")+","+mBD.getDouble("Longitude")+"&mode=driving";
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
			    	intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			    	startActivity(intent);
			    }
			});
			// Needs to call MapsInitializer before doing any CameraUpdateFactory calls
			map = mapview.getMap();
			try {
				MapsInitializer.initialize(this);
			} catch (GooglePlayServicesNotAvailableException e) {
				e.printStackTrace();
			}
			
			LatLng mapCenter = new LatLng(mBD.getDouble("Latitude"), mBD.getDouble("Longitude"));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 14));
			map.addMarker(new MarkerOptions().position(mapCenter));
			
			// The note
			note.setText(mBD.getString("Notes"));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mapview.onDestroy();
	}

	@Override
	public void onLowMemory() {
				super.onLowMemory();
		mapview.onLowMemory();
	}

	@Override
	public void onPause() {
				super.onPause();
		mapview.onPause();
	}

	@Override
	public void onResume() {
				super.onResume();
		mapview.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
				super.onSaveInstanceState(outState);
		mapview.onSaveInstanceState(outState);
	}
	
	

}
