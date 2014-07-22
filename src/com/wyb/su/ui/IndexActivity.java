package com.wyb.su.ui;


import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.wyb.su.ManagerActivities;
import com.wyb.su.R;
import com.wyb.su.adapter.IndexPagerAdapter;
import com.wyb.su.utils.CONSTANTS;
import com.wyb.su.utils.Debug;

/**
 * @author yanbao
 *
 */
/**
 * @author yanbao
 *
 */
public class IndexActivity extends FragmentActivity implements TabListener {
	
	private static String LOGTAG = "SU";
	private ViewPager mViewPager = null;
	private ActionBar mActionBar = null;
	private Fragment mFragmentPlaza = null;
	private FragmentCamare mFragmnetCamare = null;
	private Fragment mFragmentMySpace = null;
	private Fragment[] mAllFragment = new Fragment[3];
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
	
	private int[] tabIcon =
			new int[]{
			R.drawable.square_icon_checked,
			R.drawable.record_icon_normal,
			R.drawable.my_icon_checked
	};
	
	private IndexPagerAdapter mPagerAdapter=null;
	
	private Fragment mFragment;
	
	public IndexActivity (Fragment fr) {
		mFragment = fr;
	}
	
	public IndexActivity () {
		
	}
	
	/**
	 * 为ActionBar添加Tabs
	 * @param tab
	 * @param index
	 */
	public void addTabs(Tab tab, int index){
		Fragment fragment = null;
		if (null == tab) {
			tab = mActionBar.newTab();
		}
		
		tab.setContentDescription(tabTitle[index]);
		//tab.setText(tabTitle[index]);
		tab.setIcon(tabIcon[index]);
		tab.setTabListener(new IndexActivity(mAllFragment[index]));
		mActionBar.addTab(tab);
	}
	
	/**
	 * 初始化首页中的所有控件
	 */
	public void initView(){
		if (null != mActionBar) {
			// 设置ActionBar属性
			mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			mActionBar.setDisplayShowTitleEnabled(false);  
			mActionBar.setDisplayShowHomeEnabled(false);
			mActionBar.setDisplayUseLogoEnabled(true);
		}else{
			if (Debug.DEBUG) {
				Log.i(LOGTAG, "mActionBar = null");
			}
		}
		
		mAllFragment[0] = new FragmentPlaza();
		mAllFragment[1] = new FragmentCamare();
		mAllFragment[2] = new FragmentMySpace();
		
		// 为ActionBar添加需要的三个导航的Tabs
		addTabs(mPlazaTab, PLAZA_INDEX);
		addTabs(mCamareTab, CAMARE_INDEX);
		addTabs(mMySpaceTab, MY_SPACE_INDEX);
		
		// 初始化ViewPager
		/*mViewPager = (ViewPager) findViewById(R.id.index_pager);
		mPagerAdapter = new IndexPagerAdapter(this.getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setCurrentItem(CAMARE_INDEX);*/

		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.index_main);
		mActionBar = getActionBar();
		initView();
		
		ManagerActivities.getInstance().addActivity(this);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
		if (Debug.DEBUG) {
			Log.i(LOGTAG, "index--->ondestroy");
		}
		ManagerActivities.getInstance().removeAll();
	}

	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		/*if (null != mViewPager) {
			mViewPager.setCurrentItem(tab.getPosition());
		}*/
		
		ft.add(R.id.context, mFragment);
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
		ft.remove(mFragment);
	}

	/*@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int index) {
		if (null != mActionBar) {
			mActionBar.selectTab(mActionBar.getTabAt(index));
		}
		
		mFragmnetCamare = (FragmentCamare) mPagerAdapter.getItem(CAMARE_INDEX);
		if (CAMARE_INDEX != index) {
			mFragmnetCamare.disableCamare();
		} else if(!FragmentCamare.isInitCamare){
			//mFragmnetCamare.enableCamare();
		}
			
	}*/
	
	@Override
	public void onBackPressed() {		
		if (1 == this.getActionBar().getSelectedTab().getPosition()) {
			mActionBar.show();
			mActionBar.setSelectedNavigationItem(PLAZA_INDEX);
		}else {
			if (Debug.DEBUG) {
				Log.i(LOGTAG, "index--->finish");
			}
			finish();
		}
	}

}
