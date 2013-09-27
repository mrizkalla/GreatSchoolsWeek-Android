package com.the.greatschools;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class SearchFragment extends ListFragment {
    
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
            int	id = 0;
			try {
				name = json_data.getString("Name");
				address = json_data.getString("Address");
				id = json_data.getInt("id");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Map<String, String> datum  = new HashMap<String, String>(3);
			datum.put("Name", name);
			datum.put("Address", address);
			datum.put("Id", String.valueOf(id));
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

	@SuppressWarnings("unchecked")
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		System.out.printf("Position clicked: %d", position);
		HashMap<String,String> theSelectedItem = null;
		if (getListAdapter().getItem(position) instanceof HashMap) {
			 theSelectedItem = (HashMap<String, String>) getListAdapter().getItem(position);
		}
		int theSelectedId = Integer.parseInt(theSelectedItem.get("Id"));
		
		// open the detail fragment
		Fragment fragment = (Fragment) new DetailFragment();
		Bundle args = new Bundle();
		args.putInt("Index", theSelectedId-1);
		fragment.setArguments(args);
		
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.content_frame, fragment)
				.addToBackStack(null)
				.commit();
		
		//Dismiss the keyboard if it is up
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	}

}
