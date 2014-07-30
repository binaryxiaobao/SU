package com.wyb.su.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.LinearLayout;

/**
 * @author yanbao
 * 使用双缓存减少图片给内存带来的压力
 */
public class BitMapSyncTask extends AsyncTask<URL, String, Boolean> {
	private static final String TAG = "SU";
	private Bitmap mBitmap = null;
	private LinearLayout mLl = null;
	private SUSpider mSpider;
	public static HashMap<String, SoftReference<Bitmap>> mImageCache = new HashMap<String, SoftReference<Bitmap>>();
	public Context mContext;
	private URL urls = null;
	
	
	public BitMapSyncTask(LinearLayout ll, SUSpider spider, Context context){
		mLl = ll;
		mSpider = spider;
		mContext = context;
	}
	
	/*static{
		//让HashMap线程安全
		Collections.synchronizedMap(mImageCache);
	}*/
	
	private void cacheToSDCard (String url, Bitmap bitmap) {
		//如果有SD卡挂在
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File dir = new File("/mnt/sdcard/su/");
			
			//如果缓存目录不存在就创建一个新的
			if(!dir.exists())
				dir.mkdir();
			
			File bitmapFile = new File("/mnt/sdcard/su/", url.toString().substring(url.toString().lastIndexOf("/")+1));
			
			if(!bitmapFile.exists()){
				try {
					bitmapFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(bitmapFile);
				if(null != bitmap){
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				}
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally{
				if(null != fos){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
				
		}
	}
	
	//尝试在缓存中取Bitmap图片
	public boolean chooseCache(URL url){
		//尝试在内存中查看是否有缓存存在
		if(mImageCache.containsKey(url)) {
			SoftReference<Bitmap> reference = mImageCache.get(url);
			mBitmap = reference.get();
			
			return true;
		} else {
			//尝试在本地SD卡中查看是否存在
			String name = url.toString().substring(url.toString().lastIndexOf("/")+1);
			File dir = new File("/mnt/sdcard/su/");
			File[] files = dir.listFiles();
			
			for(int i=0; i<files.length; i++){
				Log.i(TAG, "i="+i+"leng="+files.length);
				if(files[i].getName().equals(name) && i<files.length){
					mBitmap = BitmapFactory.decodeFile("/mnt/sdcard/su/"+name);
					if(null != mBitmap){
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//将从网络中爬取图片URL保存到SharedPreference当中
	public void saveBitmapUrl(URL value, URL key){
		String urlString = value.toString();
		SharedPreferences sp = mContext.getSharedPreferences("urls", Context.MODE_PRIVATE);
		Editor edit = sp.edit();
		edit.putString(key.toString(), urlString);
		edit.commit();
	}
	
	//从SharedPreference当中取得
	public boolean getUrlFromSP(URL key){
		SharedPreferences sp = mContext.getSharedPreferences("urls", Context.MODE_PRIVATE);
		if(null == sp || null == key){
			return false;
		}
		String k = key.toString();
		String urlString = sp.getString(k, null);
		try {
			urls = new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if(null != urls){
			return true;
		}
		
		return false;
	}
	
	//下载图片到sdcard中
	@Override
	protected Boolean doInBackground(URL... url) {
		
		if(!getUrlFromSP(mSpider.mUrl)){
			//获得图片的URL
			urls = mSpider.getBitMap();
		}
		
		//根据图片URL下载图片流
		if(null != urls){
			
			//保存URL到SharedPreference当中
			saveBitmapUrl(urls, mSpider.mUrl);
			
			//查看是否在两级缓存中已经存在
			if(chooseCache(urls))
				return true;
			
			HttpGet httpRequest = new HttpGet(urls.toString());
			HttpClient client = new DefaultHttpClient();
			InputStream is = null;
			try {
				HttpResponse response = client.execute(httpRequest);
				if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
					HttpEntity entity = response.getEntity();
					is = entity.getContent();
					Bitmap myBitmap = BitmapFactory.decodeStream(is);
					mBitmap = myBitmap;
					//将图片缓存到内存中
					mImageCache.put(urls.toString(), new SoftReference<Bitmap>(myBitmap));
					//将图片缓存到本地SDCard中
					cacheToSDCard(urls.toString(),myBitmap);
					
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
