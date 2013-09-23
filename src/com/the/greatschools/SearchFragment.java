package com.the.greatschools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SearchFragment extends ListFragment {

	String[] countries = new String[] {
	        "India",
	        "Pakistan",
	        "Sri Lanka",
	        "China",
	        "Bangladesh",
	        "Nepal",
	        "Afghanistan",
	        "North Korea",
	        "South Korea",
	        "Japan"
	    };
    
	private BusinessDirectory mBD;
    private SimpleAdapter mSimpleAdapter;
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View fragment = inflater.inflate(R.layout.search, container, false);
		
        mBD = ((MainActivity) getActivity()).getBusinessDirectory();
        JSONArray jArray = null;
		try {
			jArray = mBD.getDirectoryArray();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        for(int i=0; i < jArray.length() ; i++) {
            JSONObject json_data = null;
			try {
				json_data = jArray.getJSONObject(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            String name= null, 
            		address = null;
			try {
				name = json_data.getString("Name");
				address = json_data.getString("Address");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> datum  = new HashMap<String, String>(2);
			datum.put("Name", name);
			datum.put("Address", address);
			data.add(datum);
        }

        mSimpleAdapter = new SimpleAdapter(inflater.getContext(), data,
                android.R.layout.simple_list_item_2, 
                new String[] {"Name", "Address" }, 
                new int[] {android.R.id.text1, android.R.id.text2 });;
        setListAdapter(mSimpleAdapter);
 
        // Search EditText
        EditText inputSearch= (EditText) fragment.findViewById(R.id.inputSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                mSimpleAdapter.getFilter().filter(cs);   
            }
             
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                    int arg3) {
                // TODO Auto-generated method stub
                 
            }
             
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub                          
            }
        });
        // TODO Auto-generated method stub
		return fragment;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		System.out.printf("Position clicked: %d", position);
		Object foo = getListAdapter().getItem(position);
		
		//super.onListItemClick(l, v, position, id);
	}

	
	
}
