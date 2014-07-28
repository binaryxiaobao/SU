package com.wyb.su.ui;

import com.wyb.su.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LoginActivity extends Activity implements OnClickListener {
	
	private ImageView mWeibo = null;
	private ImageView mQQ = null;
	
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
		
		initView();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_by_weibo:
			//LoginActivity.this.startActivity(new Intent(LoginActivity.this, WBAuthActivity.class));
			break;
			
		case R.id.login_by_qq:
			
			break;

		default:
			break;
		}
		
	}

}
