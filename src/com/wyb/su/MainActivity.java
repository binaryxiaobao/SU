package com.wyb.su;

import com.wyb.su.ui.IndexActivity;
import com.wyb.su.utils.Debug;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends Activity {
	
	private AlarmManager mAlarmManager;
	private Context mContext = null;
	private long mTime = 3*1000;
	private final String LOGTAG = "SU";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ManagerActivities.getInstance().addActivity(this);
		mContext = this.getApplicationContext();
		mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
		//注意这个时间是要作为设置AlarmManager中的第二个参数
		mTime = SystemClock.elapsedRealtime()+mTime;
		
		//使用PendingIntent进行延迟3秒
		Intent intent = new Intent();
		intent.setClass(this, IndexActivity.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
		mAlarmManager.set(AlarmManager.ELAPSED_REALTIME, mTime, pIntent);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		ManagerActivities.getInstance().removeActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (Debug.DEBUG) {
			Log.d(LOGTAG, getPackageName()+":onDestroy");
		}
		//取消闹钟的定时
		if (null != mAlarmManager) {
			Intent intent = new Intent(this, IndexActivity.class);
			PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);
			mAlarmManager.cancel(pIntent);
		}
		
	}



}
