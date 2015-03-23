package com.loujin.itsnew;

import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.litepal.tablemanager.Connector;














import com.loujin.configs.Const;
import com.loujin.task.TaskManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	private  Document doc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SQLiteDatabase db = Connector.getDatabase(); 
		new MyTask().execute();
	}
	
	class MyTask extends AsyncTask<Void,Void,Void>{
		ProgressDialog pdlg;
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try{
				Log.e("开始", new Date()+"");
				doc = Jsoup.connect(Const.IpAddress).timeout(5000).post();
				Document doc1 = Jsoup.parse(doc.toString());
				Elements div1 = doc1.select("section#column-new");
				Document doc2 = Jsoup.parse(div1.toString());
				Elements div2 = doc2.select(".recent-article");
				Log.e("长度", div2.size()+"条");
				for(Element ele :div2){
					String title = ele.getElementsByClass("article-category").text();
					Log.e("头标题", title);
					String hdnum = ele.getElementsByClass("num").text();
					Log.e("互动次数", hdnum);
				    String picurl =ele.getElementsByTag("img").attr("data-original");
				    Log.e("图片呢",picurl);
					String aa = ele.select("a").attr("href").trim();
					Log.e("内容链接地址", aa);
				}
				Log.e("结束", new Date()+"");
				
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
//			pdlg = ProgressDialog.show(getApplicationContext(), "加载", "拼命加载中····");
//			pdlg.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//			pdlg.show();
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			pdlg.dismiss();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
