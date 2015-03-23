package com.loujin.itsnew;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
public class StartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				 HttpResponse response = http.httpGet(Const.IpAddress);
//		    	 Log.e("hehe", response.getResponseCode()+"头部"+response.getExpiresHeader()+"缓存"+response.isInCache()+"是否过期"+response.isExpired());
//			}
//		}).start();
		new Handler().postDelayed(new Runnable() {
		      @Override
		      public void run() {
		    	 Intent intent = new Intent(StartActivity.this, SampleActivity.class);
		    	 startActivity(intent);
		    	 finish();
		      }
		    }, 1000);
	}
}
