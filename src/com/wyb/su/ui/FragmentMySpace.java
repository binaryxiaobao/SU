package com.wyb.su.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyb.su.R;

public class FragmentMySpace extends Fragment {
	
	//private BootstrapButton
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View spaceView = inflater.inflate(R.layout.my_space_fragment_layout, container, false);
		return spaceView;
	}

}
