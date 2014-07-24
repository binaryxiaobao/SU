package com.wyb.su.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.LinearLayout;

/**
 * @author yanbao
 *
 */
public class BitMapSyncTask extends AsyncTask<URL, String, Boolean> {
	private Bitmap mBitmap = null;
	private LinearLayout mLl = null;
	private SUSpider mSpider;
	
	public BitMapSyncTask(LinearLayout ll, SUSpider spider){
		mLl = ll;
		mSpider = spider;
	}
	
	//下载图片到sdcard中
	@Override
	protected Boolean doInBackground(URL... url) {
		URL urls = mSpider.getBitMap();
		if(null != urls){
			HttpGet httpRequest = new HttpGet(urls.toString());
			HttpClient client = new DefaultHttpClient();
			InputStream is = null;
			try {
				HttpResponse response = client.execute(httpRequest);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
					mBitmap = BitmapFactory.decodeStream(is);
					
					return true;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(null != is){
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return false;
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
		super.onPostExecute(result);
		if (true == result) {
			mLl.setBackgroundDrawable(new BitmapDrawable(mBitmap));
			mLl.invalidate();
		}
	}

}
