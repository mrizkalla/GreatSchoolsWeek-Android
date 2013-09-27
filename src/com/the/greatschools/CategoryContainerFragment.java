package com.the.greatschools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CategoryContainerFragment extends Fragment {
	CategoryPagerAdapter mCategoryPagerAdapter;
    ViewPager mViewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View fragment = inflater.inflate(R.layout.pager, container, false);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mCategoryPagerAdapter =
                new CategoryPagerAdapter(
                        getFragmentManager());
        mViewPager = (ViewPager) fragment.findViewById(R.id.pager);
        mViewPager.setAdapter(mCategoryPagerAdapter);
        
		return fragment;
	}

}
