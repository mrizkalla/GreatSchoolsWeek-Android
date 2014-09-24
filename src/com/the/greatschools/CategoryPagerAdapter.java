package com.the.greatschools;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryPagerAdapter extends FragmentPagerAdapter {
	public static final int CATEGORY_VAL = 1;
	public static final int DAYS_VAL = 2;
	public static final int PERCENT_VAL = 3;
	public static final String BROWSE_KEY = "Browse";
	public static final String PERCENTINT_KEY = "PercentInt";
	
	private int mMode;
	
	private String[] type = {"Restaurants", 
			"Services",
			"Retailers",
			"Drinks and Desserts",
			"Grocers",
			"Family Entertainment",
			"Food Trucks"};
	
	private String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

	private String[] percents = {"All Sales", "10% or under", "15%", "20%", "25%", "30% or over"};
	private String[] category;
	
	public CategoryPagerAdapter(FragmentManager fm, int mode) {
		super(fm);
		mMode = mode;
		switch (mode) {
		case CATEGORY_VAL:
			category = type;
			break;
		case DAYS_VAL:
			category = days;
			break;
		case PERCENT_VAL:
			category = percents;
			break;}
	}	
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = new CategoryFragment();
		Bundle args = new Bundle();
		args.putString("Category", category[arg0]);
		args.putInt(BROWSE_KEY, mMode);
		if (mMode == PERCENT_VAL) {			
			args.putString(PERCENTINT_KEY, String.valueOf(arg0));
		}
		fragment.setArguments(args);
		
		return (Fragment)fragment;
	}

	@Override
	public int getCount() {
		return category.length;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return category[position];
	}
	
	

}

