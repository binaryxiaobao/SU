package com.wyb.su.ui;


import net.simonvt.menudrawer.MenuDrawer;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.ActivityManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.wyb.su.ManagerActivities;
import com.wyb.su.R;
import com.wyb.su.adapter.IndexPagerAdapter;
import com.wyb.su.utils.CONSTANTS;
import com.wyb.su.utils.Debug;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author yanbao
 *
 */
/**
 * @author yanbao
 *
 */
public class IndexActivity extends FragmentActivity implements TabListener, OnClickListener {
	
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
	
	private MenuDrawer mMenu;
	
	private CircleImageView mLoginBtn = null;
	
	//获取已经注册到SDK的平台实例列表
	Platform[] platformList = ShareSDK.getPlatformList(this);
	
	// 获取单个平台
	//Platform plat = ShareSDK.getPlatform(this, TencentWeibo.NAME);
	
	
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
		
		mLoginBtn = (CircleImageView) mMenu.findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(this);
		
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
		mMenu = MenuDrawer.attach(this);
		mMenu.setContentView(R.layout.index_main);
		mMenu.setMenuView(R.layout.left_menu);
		//setContentView(R.layout.index_main);
		mActionBar = getActionBar();
		initView();
		ShareSDK.initSDK(this);
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
		ShareSDK.stopSDK(this);
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login_btn:
			Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
			startActivityForResult(intent, 1);
			//authorize();
			break;

		default:
			break;
		}
		
	}
	
	/*private void authorize(Platform plat) {
        if (plat == null) {
                popupOthers();
                return;
        }
        
        if(plat.isValid()) {
                String userId = plat.getDb().getUserId();
                if (userId != null) {
                        UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                        login(plat.getName(), userId, null);
                        return;
                }
        }
        plat.setPlatformActionListener(this);
        // true不使用SSO授权，false使用SSO授权
        plat.SSOSetting(true);
        plat.showUser(null);
}*/
	
	private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        
        // 分享时Notification的图标和文字
        oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(this);
   }
	
	

}
