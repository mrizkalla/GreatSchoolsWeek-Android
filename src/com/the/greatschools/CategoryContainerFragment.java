package com.the.greatschools;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryContainerFragment extends Fragment {
	int mode;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View fragment = inflater.inflate(R.layout.pager, container, false);

		mode = getArguments().getInt(CategoryPagerAdapter.BROWSE_KEY);
        
		return fragment;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
		mViewPager.setAdapter(new CategoryPagerAdapter(getChildFragmentManager(), mode));
		
		if (mode == CategoryPagerAdapter.DAYS_VAL) {
			int dayVal = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			// Sun = 1, so we need to get Mon = 0;
			int result = Math.max(0, dayVal - 2);
			mViewPager.setCurrentItem(result);
		}
	}

	
}
