package com.wyb.su.utils;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SUSpider {
	
	public URL mUrl;
	private static final String TAG = "SU";
	
	public SUSpider(URL url) {
		mUrl = url;
	}
	
	public URL getBitMap(){
		if (null != mUrl) {
			try {
				Document doc = Jsoup.connect(mUrl.toString()).get();
				Elements es = doc.select("a[target=_blank] img[src]");	
				Element  e = es.first();
				if (es.iterator().hasNext()) {
					e = es.iterator().next();
				}
				if(null == e){
					return null;
				}
				String downloadUrl = e.attr("src");
				
				return new URL(downloadUrl);
				//URL myUrl = new URL(downloadUrl);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}

}
