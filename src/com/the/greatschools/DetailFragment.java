package com.the.greatschools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailFragment extends Fragment {
	private GoogleMap map;
	private MapView mapview;
	private int index;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View fragment = inflater.inflate(R.layout.detail, container, false);
		TextView name = (TextView) fragment.findViewById(R.id.name);
		TextView phone = (TextView) fragment.findViewById(R.id.phone);
		TextView url = (TextView) fragment.findViewById(R.id.url);
		TextView percent = (TextView) fragment.findViewById(R.id.percent);
		TextView allsales = (TextView) fragment.findViewById(R.id.allSales);
		ImageView mon = (ImageView) fragment.findViewById(R.id.mon_image);
		ImageView tue = (ImageView) fragment.findViewById(R.id.tue_image);
		ImageView wed = (ImageView) fragment.findViewById(R.id.wed_image);
		ImageView thur = (ImageView) fragment.findViewById(R.id.thur_image);
		ImageView fri = (ImageView) fragment.findViewById(R.id.fri_image);
		Button address = (Button) fragment.findViewById(R.id.address);
		mapview = (MapView) fragment.findViewById(R.id.map);
		mapview.onCreate(savedInstanceState);
		TextView note = (TextView) fragment.findViewById(R.id.notes);
		
        BusinessDirectory mBD = ((MainActivity) getActivity()).getBusinessDirectory();        
        index = getArguments().getInt("Index");

		try {
			// set the detail section
			name.setText(mBD.getName(index));
			phone.setText(mBD.getPhone(index));
			url.setText(mBD.getURL(index));
			percent.setText(mBD.getPercent(index));
			if (mBD.getAllSales(index).contentEquals("Yes"))
				allsales.setText(getResources().getString(R.string.allsales));
			
			// set the days section
			if (mBD.getDay(index, "Monday")) {
				mon.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getDay(index, "Tuesday")) {
				tue.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getDay(index, "Wednesday")) {
				wed.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getDay(index, "Thursday")) {
				thur.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			if (mBD.getDay(index, "Friday")) {
				fri.setBackground(getResources().getDrawable(R.drawable.green_dot));
			}
			
			// setup the map
			address.setText(mBD.getAddress(index));
			address.setOnClickListener(new View.OnClickListener() {
			    public void onClick(View v) {
			        // Do something in response to button click
			        BusinessDirectory mBD = ((MainActivity) getActivity()).getBusinessDirectory();        
			    	String url=null;
					try {
						url = "http://maps.google.com/maps?daddr="+mBD.getLat(index)+","+mBD.getLong(index)+"&mode=driving";
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
				MapsInitializer.initialize(this.getActivity());
			} catch (GooglePlayServicesNotAvailableException e) {
				e.printStackTrace();
			}
			
			LatLng mapCenter = new LatLng(mBD.getLat(index), mBD.getLong(index));
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 14));
			map.addMarker(new MarkerOptions().position(mapCenter));
			
			// The note
			note.setText(mBD.getNote(index));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fragment;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mapview.onDestroy();
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		mapview.onLowMemory();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mapview.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapview.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		mapview.onSaveInstanceState(outState);
	}
	
	

}
