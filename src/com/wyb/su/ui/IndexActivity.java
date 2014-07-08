package com.wyb.su.ui;


import com.wyb.su.R;
import com.wyb.su.adapter.IndexPagerAdapter;
import com.wyb.su.utils.Debug;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class IndexActivity extends FragmentActivity implements TabListener, OnPageChangeListener {
	
	private static String LOGTAG = "SU";
	private ViewPager mViewPager = null;
	private ActionBar mActionBar = null;
	private Fragment mFragmentPlaza = null;
	private Fragment mFragmnetCamare = null;
	private Fragment mFragmentMySpace = null;
	private Tab mPlazaTab = null,
			mCamareTab = null,
			mMySpaceTab = null;
	public static final int PLAZA_INDEX = 0;
	public static final int CAMARE_INDEX = 1;
	public static final int MY_SPACE_INDEX = 2;
	
	private String[] tabTitle = 
			new String[]{"广场",
			"拍一拍",
			"我的"};
	
	private IndexPagerAdapter mPagerAdapter=null;
	
	// 为ActionBar添加Tabs
	public void addTabs(Tab tab, int index){
		if (null == tab) {
			tab = mActionBar.newTab();
		}
		
		tab.setContentDescription(tabTitle[index]);
		tab.setText(tabTitle[index]);
		tab.setTabListener(this);
		mActionBar.addTab(tab);
	}
	
	//初始化首页中的所有控件
	public void initView(){
		if (null != mActionBar) {
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			mActionBar.setDisplayShowTitleEnabled(false);  
			mActionBar.setDisplayShowHomeEnabled(false);
		}else{
			if (Debug.DEBUG) {
				Log.i(LOGTAG, "mActionBar = null");
			}
		}
		
		addTabs(mPlazaTab, PLAZA_INDEX);
		addTabs(mCamareTab, CAMARE_INDEX);
		addTabs(mMySpaceTab, MY_SPACE_INDEX);
		
		mViewPager = (ViewPager) findViewById(R.id.index_pager);
		mPagerAdapter = new IndexPagerAdapter(this.getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(PLAZA_INDEX);

		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_main);
		mActionBar = getActionBar();
		initView();
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int index) {
			mActionBar.selectTab(mActionBar.getTabAt(index));
		}

}
