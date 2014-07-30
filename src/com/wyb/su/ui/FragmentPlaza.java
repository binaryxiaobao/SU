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
	private LinearLayout[] Ll = new LinearLayout[15];;
	//需要进行解析的网站
	private String[] mChanel = new String[] {
			"http://www.meishichina.com/",
			"http://www.uimaker.com/tags.php?/%CD%BC%B1%EAui/",
			"http://www.hao123.com/star",
			"http://www.quanjing.com/feature/household.html",
			"http://www.ik123.com/q/haokan/xiaohai.html",
			"http://gaoxiaoo.com/archives/category/meinvtupiandaquan",
			"http://www.mnsfz.com/",
			"http://www.lanrentuku.com/sort/%D7%E3%C7%F2/",
			"http://www.tuweng.com/life/",
			"",
			"http://www.ivsky.com/tupian/ziranfengguang/",
			"http://roll.sohu.com/20111227/n330417211.shtml",
			"http://car.autohome.com.cn/Pic/",
			"http://tieba.baidu.com/f?kw=nba&tp=1",
			"http://product.yesky.com/c/506003_17215.shtml"
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
		Ll[7] = (LinearLayout) plazaView.findViewById(R.id.recommend222);
		Ll[8] = (LinearLayout) plazaView.findViewById(R.id.recommend333);
		Ll[9] = (LinearLayout) plazaView.findViewById(R.id.recommend1111);
		Ll[10] = (LinearLayout) plazaView.findViewById(R.id.recommend2222);
		Ll[11] = (LinearLayout) plazaView.findViewById(R.id.recommend3333);
		Ll[12] = (LinearLayout) plazaView.findViewById(R.id.recommend11111);
		Ll[13] = (LinearLayout) plazaView.findViewById(R.id.recommend22222);
		Ll[14] = (LinearLayout) plazaView.findViewById(R.id.recommend33333);
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
			BitMapSyncTask task = new BitMapSyncTask(Ll[i], spider, getActivity());
			task.execute(url);
		}
		
	}

}
