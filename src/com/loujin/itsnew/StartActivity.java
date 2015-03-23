package com.loujin.itsnew;
import cn.trinea.android.common.util.ResourceUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
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
