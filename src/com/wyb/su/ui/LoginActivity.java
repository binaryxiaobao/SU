package com.wyb.su.ui;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;

import com.wyb.su.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;

public class LoginActivity extends Activity implements OnClickListener, PlatformActionListener, Callback {
	
	private ImageView mWeibo = null;
	private ImageView mQQ = null;
	
	private static final int MSG_USERID_FOUND = 1;
	private static final int MSG_LOGIN = 2;
	private static final int MSG_AUTH_CANCEL = 3;
	private static final int MSG_AUTH_ERROR= 4;
	private static final int MSG_AUTH_COMPLETE = 5;
	
	private Platform weibo = null;
	
	public void initView(){
		mWeibo = (ImageView) findViewById(R.id.login_by_weibo);
		mQQ = (ImageView) findViewById(R.id.login_by_qq);
		
		mWeibo.setOnClickListener(this);
		mQQ.setOnClickListener(this);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ShareSDK.initSDK(this);
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_by_weibo:
			ShareSDK.initSDK(this);
			weibo = new SinaWeibo(this);
			authorize(weibo);
			break;
			
		case R.id.login_by_qq:
			
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		ShareSDK.stopSDK(this);
	}
	
	private void authorize(Platform plat) {
		if (plat == null) {
			//popupOthers();
			return;
		}

		if(plat.isValid()) {
			Log.i("SU", "plat.isValid");
			String userId = plat.getDb().getUserId();
			if (!TextUtils.isEmpty(userId)) {
				UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
				login(plat.getName(), userId, null);
				return;
			}
		}
		plat.setPlatformActionListener(this);
		plat.SSOSetting(true);
		plat.showUser(null);
	}
	
	/*private void popupOthers() {
		Dialog dlg = new Dialog(this);
		View dlgView = View.inflate(this, R.layout.other_plat_dialog, null);
		View tvFacebook = dlgView.findViewById(R.id.tvFacebook);
		tvFacebook.setTag(dlg);
		tvFacebook.setOnClickListener(this);
		View tvTwitter = dlgView.findViewById(R.id.tvTwitter);
		tvTwitter.setTag(dlg);
		tvTwitter.setOnClickListener(this);

		dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dlg.setContentView(dlgView);
		dlg.show();
	}*/

	@Override
	public void onCancel(Platform arg0, int action) {
		// TODO Auto-generated method stub
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
		}
	}

	@Override
	public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
		// TODO Auto-generated method stub
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
			login(platform.getName(), platform.getDb().getUserId(), res);
		}
		System.out.println(res);
		Log.i("SU", "complete-->"+res);
		
	}

	@Override
	public void onError(Platform arg0, int action, Throwable t) {
		// TODO Auto-generated method stub
		if (action == Platform.ACTION_USER_INFOR) {
			UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
		}
		t.printStackTrace();
	}

	@Override
	public boolean handleMessage(Message arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void login(String plat, String userId, HashMap<String, Object> userInfo) {
		Message msg = new Message();
		msg.what = MSG_LOGIN;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}
	

}
