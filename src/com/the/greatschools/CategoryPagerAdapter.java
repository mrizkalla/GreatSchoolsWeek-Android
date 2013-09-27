package com.the.greatschools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryPagerAdapter extends FragmentPagerAdapter {

	public CategoryPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	String[] category = {"Restaurants", 
							"Services",
							"Retailers",
							"Drinks and Desserts",
							"Grocers",
							"Family Entertainment",
							"Food Trucks"};
	
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		Fragment fragment = new CategoryFragment();
		Bundle args = new Bundle();
		args.putString("Category", category[arg0]);
		return (Fragment)fragment;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return category.length;
	}

}

