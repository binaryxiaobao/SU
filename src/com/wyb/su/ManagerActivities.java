package com.wyb.su;

import java.util.Stack;

import android.app.Activity;

public class ManagerActivities {
	private static ManagerActivities mAppManager = null;
	private static Stack<Activity> mActivityStack = null;

	private ManagerActivities(){
		
	}
	
	// single instance 
	public static ManagerActivities getInstance () {
		if (null == mAppManager) {
			mAppManager = new ManagerActivities();
		}
		
		return mAppManager;
	}
	
	// add activity to stack
	public void addActivity(Activity activity) {
		if (null == mActivityStack) {
			mActivityStack = new Stack<Activity>();
		}
		
		mActivityStack.add(activity);
	}
	
	//remove activity from stack
	public void removeActivity(Activity activity) {
		if (null != activity && mActivityStack != null) {
			mActivityStack.removeElement(activity);
			activity.finish();
			activity = null;
		}
	}
	
	// obtain the top stack activity
	public Activity getTopActivity() {
		if (mActivityStack != null) {
			return mActivityStack.get(0);
		}
		return null;
	}
	
	// remove the top activity
	public void removeTop() {
		if (mActivityStack != null) {
			Activity activity = mActivityStack.lastElement();
			removeActivity(activity);
		}
	}
	
	// remove all activity from the stack
	public void removeAll() {
		if (null != mActivityStack) {
			
			for (int i=0; i< mActivityStack.size(); i++) {
				Activity activity = mActivityStack.get(i);
				removeActivity(activity);
			}
			mActivityStack.clear();
		}
	}

}
