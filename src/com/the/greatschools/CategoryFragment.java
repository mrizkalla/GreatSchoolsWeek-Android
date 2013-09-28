package com.the.greatschools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CategoryFragment extends ListFragment {

	private BusinessDirectory mBD;
	private SimpleAdapter mSimpleAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View fragment = inflater.inflate(R.layout.category, container, false);
		Bundle args = getArguments();
		String category = args.getString("Category");
		int mode = args.getInt(CategoryPagerAdapter.BROWSE_KEY);
		String percentInt = args.getString(CategoryPagerAdapter.PERCENTINT_KEY);

		
		mBD = ((MainActivity) getActivity()).getBusinessDirectory();
		try {

			List<Map<String, String>> data = new ArrayList<Map<String, String>>();
			for (int i = 0; i < mBD.getLength(); i++) {
				String name = null, address = null;
				int id = 0;
				
				if ((mode == CategoryPagerAdapter.CATEGORY_VAL && mBD.getCategory(i).contentEquals(category)) ||
						(mode == CategoryPagerAdapter.DAYS_VAL && mBD.getDay(i, category)) ||
						(mode == CategoryPagerAdapter.PERCENT_VAL && mBD.getPercentInt(i).contains(percentInt)))
				{
					name = mBD.getName(i);
					address = mBD.getAddress(i);
					id = mBD.getID(i);

					Map<String, String> datum = new HashMap<String, String>(3);
					datum.put("Name", name);
					datum.put("Address", address);
					datum.put("Id", String.valueOf(id));
					data.add(datum);
				} 
				
			}

			mSimpleAdapter = new SimpleAdapter(inflater.getContext(), data,
					android.R.layout.simple_list_item_2, new String[] { "Name",
							"Address" }, new int[] { android.R.id.text1,
							android.R.id.text2 });
			setListAdapter(mSimpleAdapter);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	    Intent intent = new Intent(getActivity(), DetailActivity.class);
	    try {
			intent.putExtra("business", mBD.getBusinessJSONString(theSelectedId-1));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    getActivity().startActivity(intent);
	    
		// open the detail fragment
		/*Fragment fragment = new DetailFragment();
		Bundle args = new Bundle();
		args.putInt("Index", theSelectedId-1);
		fragment.setArguments(args);
		
		FragmentManager fragmentManager = getChildFragmentManager();
		fragmentManager.beginTransaction()
				.replace(android.R.id.content, fragment)
				.addToBackStack(null)
				.commit();
		*/
		//Dismiss the keyboard if it is up
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
	}

}
