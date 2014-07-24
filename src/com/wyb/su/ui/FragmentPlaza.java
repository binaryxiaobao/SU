package com.wyb.su.ui;

import java.net.MalformedURLException;
import java.net.URL;

import com.wyb.su.R;
import com.wyb.su.utils.BitMapSyncTask;
import com.wyb.su.utils.SUSpider;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class FragmentPlaza extends Fragment {
	private LinearLayout[] Ll = new LinearLayout[7];;
	//需要进行解析的网站
	private String[] mChanel = new String[] {
			"http://www.meishichina.com/",
			"http://www.uimaker.com/tags.php?/%CD%BC%B1%EAui/",
			"http://www.hao123.com/star",
			"http://www.quanjing.com/feature/household.html",
			"http://www.mamiakids.com/photo.html",
			"http://www.pengfu.com/jinghuatu_1.html",
			"http://www.mnsfz.com/"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View plazaView = inflater.inflate(R.layout.plaza_fragment_layout, container, false);
		
		findViewId(plazaView);
		
		return plazaView;
	}
	
	
	public void findViewId(View plazaView){
		Ll[0] = (LinearLayout) plazaView.findViewById(R.id.recommend);
		Ll[1] = (LinearLayout) plazaView.findViewById(R.id.recommend2);
		Ll[2] = (LinearLayout) plazaView.findViewById(R.id.recommend3);
		Ll[3] = (LinearLayout) plazaView.findViewById(R.id.recommend11);
		Ll[4] = (LinearLayout) plazaView.findViewById(R.id.recommend22);
		Ll[5] = (LinearLayout) plazaView.findViewById(R.id.recommend33);
		Ll[6] = (LinearLayout) plazaView.findViewById(R.id.recommend111);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		for(int i=0; i<mChanel.length; i++){
			URL url = null;
			try {
				url = new URL(mChanel[i]);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SUSpider spider = new SUSpider(url);
			BitMapSyncTask task = new BitMapSyncTask(Ll[i], spider);
			task.execute(url);
		}
		
	}

}
