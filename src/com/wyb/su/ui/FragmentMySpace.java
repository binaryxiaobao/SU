package com.wyb.su.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wyb.su.R;

public class FragmentMySpace extends Fragment implements OnRefreshListener {
	
	private SwipeRefreshLayout swipeLayout; 
	
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
		
		initView(spaceView);
		return spaceView;
	}
	
	public void initView(View spaceView){
		swipeLayout = (SwipeRefreshLayout) spaceView.findViewById(R.id.container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_red_light, android.R.color.holo_green_light,  
                android.R.color.holo_blue_bright, android.R.color.holo_orange_light);
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		Toast.makeText(getActivity(), "别闹了，在刷新呢", Toast.LENGTH_SHORT).show();
	}

}
