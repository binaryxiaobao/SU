package com.wyb.su.adapter;

import com.wyb.su.ui.FragmentCamare;
import com.wyb.su.ui.FragmentMySpace;
import com.wyb.su.ui.FragmentPlaza;
import com.wyb.su.ui.IndexActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class IndexPagerAdapter extends FragmentPagerAdapter {
	

	public IndexPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
		case IndexActivity.PLAZA_INDEX:
			return new FragmentPlaza();

		case IndexActivity.CAMARE_INDEX:
			return new FragmentCamare();

		case IndexActivity.MY_SPACE_INDEX:
			return new FragmentMySpace();

		default:
			return new FragmentPlaza();
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}
	
	

}
