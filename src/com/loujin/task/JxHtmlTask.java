package com.loujin.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import cn.trinea.android.common.entity.HttpResponse;
import cn.trinea.android.common.service.HttpCache;
import cn.trinea.android.common.service.HttpCache.HttpCacheListener;
import cn.trinea.android.common.util.CacheManager;
import cn.trinea.android.common.util.ResourceUtils;

import com.loujin.configs.Const;
import com.loujin.itsnew.CallBack;
import com.loujin.model.News;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class JxHtmlTask {

	private static JxHtmlTask instance;
	private Context context;
	private CallBack<Boolean> callback;
	private HttpCache          httpCache;

	private JxHtmlTask(Context context) {
		this.context = context;
	}

	public static JxHtmlTask getInstance(Context context) {
		if (instance == null) {
			synchronized (JxHtmlTask.class) {
				if (instance == null) {
					instance = new JxHtmlTask(context);
				}
			}
		}
		return instance;
	}

	public void initNewTask(CallBack<List<News>> callback) {
		new LoadDataTask(callback,1).execute();
	}
	
	public void initFavTask(CallBack<List<News>> callback) {
		new LoadDataTask(callback,2).execute();
	}
	public void initHotTask(CallBack<List<News>> callback) {
		new LoadDataTask(callback,3).execute();
	}
	 String Html = null;
	private String getHtmlByUrl(){
		 Html = null;
		 httpCache = CacheManager.getHttpCache(context);
		 httpCache.httpGet(Const.IpAddress, new HttpCacheListener() {

			@Override
			protected void onPreGet() {
				// TODO Auto-generated method stub
				super.onPreGet();
			}

			@Override
			protected void onPostGet(HttpResponse httpResponse,
					boolean isInCache) {
				// TODO Auto-generated method stub
				super.onPostGet(httpResponse, isInCache);
				if(httpResponse!=null){
					 StringBuilder sb = new StringBuilder(256);
                     sb.append("is in cache: ").append(isInCache).append("\r\n");
                     if (isInCache) {
                         sb.append("expires: ").append(new Date(httpResponse.getExpiredTime()).toGMTString())
                                 .append("\r\n");
                     }
                     Log.e("sb", sb.toString()+"执行到啊");
                     Html = httpResponse.getResponseBody().toString();
				}
			}
			 
		});
		
		return Html;
	}
	
	class LoadDataFyTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog pdlg;
		Document doc;
		public CallBack<List<News>> callback;
		private List<News> ListNews = new ArrayList<News>();
		private int position;
		private int pagecount = 0;
		private boolean islocal;
		private int pagesize = 15;
		public LoadDataFyTask(int pagecount,CallBack<List<News>> callback,int position) {
			this.callback = callback;
			this.position = position;
			islocal = true;
			this.pagecount = pagecount;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Document doc1 = null;
				if(islocal){
					String html = ResourceUtils.geFileFromAssets(context,"test.html");
					doc1 = Jsoup.parse(html);
				}else{
					doc = Jsoup.connect(Const.IpAddress).timeout(5000).post();
					doc1 = Jsoup.parse(doc.toString());
	
				}
				Elements div1 ;
				if(position==1){
					div1 = doc1.select("section#column-new");
				}else if(position==2){
					div1 = doc1.select("section#column-rising");
				}else{
					div1 = doc1.select("section#column-hot");
				}
				Document doc2 = Jsoup.parse(div1.toString());
				Elements div2 = doc2.select(".recent-article");
				Log.e("共", div2.size()+"条");
				if(position==1){
					Const.NewsSize = div2.size();
				}else if(position==2){
					Const.FavSize = div2.size();
				}else{
					Const.HotSize = div2.size();
				}
				if(div2.size()<=15){
					for (Element ele : div2) {
						News news = new News();
						String category = ele.getElementsByClass("article-category")
								.text();
						news.setCategory(category);
						String title = ele.getElementsByClass("recent-title")
								.text();
						news.setTitle(title);
						String hdnum = ele.getElementsByClass("num").text();
						news.setCommentCount(hdnum);
						String picurl = ele.getElementsByTag("img").attr(
								"data-original");
						news.setImgrul(picurl);
						String aa = ele.select("a").attr("href").trim();
						news.setContentUrl(aa);
						ListNews.add(news);
					}
				}else{
					int totle = pagecount*pagesize;
					if(totle>div2.size()){
						int postion =div2.size()%15;
						for(int i=div2.size()-postion;i<div2.size();i++){
							Element ele = div2.get(i);
							News news = new News();
							String category = ele.getElementsByClass("article-category")
									.text();
							news.setCategory(category);
							String title = ele.getElementsByClass("recent-title")
									.text();
							news.setTitle(title);
							String hdnum = ele.getElementsByClass("num").text();
							news.setCommentCount(hdnum);
							String picurl = ele.getElementsByTag("img").attr(
									"data-original");
							news.setImgrul(picurl);
							String aa = ele.select("a").attr("href").trim();
							news.setContentUrl(aa);
							ListNews.add(news);
						}
					}else{
						for(int i=pagecount-1;i<pagecount*pagesize;i++){
							Element ele = div2.get(i);
							News news = new News();
							String category = ele.getElementsByClass("article-category")
									.text();
							news.setCategory(category);
							String title = ele.getElementsByClass("recent-title")
									.text();
							news.setTitle(title);
							String hdnum = ele.getElementsByClass("num").text();
							news.setCommentCount(hdnum);
							String picurl = ele.getElementsByTag("img").attr(
									"data-original");
							news.setImgrul(picurl);
							String aa = ele.select("a").attr("href").trim();
							news.setContentUrl(aa);
							ListNews.add(news);
						}
					}
				
				}
				Log.e("List", ListNews.size() + "ge ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			callback.invoke(ListNews);
		}
	}

	class LoadDataTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog pdlg;
		Document doc;
		public CallBack<List<News>> callback;
		private List<News> ListNews = new ArrayList<News>();
		private int position;
		private boolean islocal;
		public LoadDataTask(CallBack<List<News>> callback,int position) {
			this.callback = callback;
			this.position = position;
			islocal = true;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			try {
				Document doc1 = null;
				if(islocal){
					String html = ResourceUtils.geFileFromAssets(context,"test.html");
					doc1 = Jsoup.parse(html);
				}else{
					doc = Jsoup.connect(Const.IpAddress).timeout(5000).post();
					doc1 = Jsoup.parse(doc.toString());
//					doc1 = Jsoup.parse(Html);
				}
				Elements div1 ;
				if(position==1){
					div1 = doc1.select("section#column-new");
				}else if(position==2){
					div1 = doc1.select("section#column-rising");
				}else{
					div1 = doc1.select("section#column-hot");
				}
				Document doc2 = Jsoup.parse(div1.toString());
				Elements div2 = doc2.select(".recent-article");
				Log.e("共", div2.size()+"条");
				for (Element ele : div2) {
					News news = new News();
					String fenlei = ele.getElementsByClass("article-category")
							.text();
					Log.e("分类", fenlei);
					String title = ele.getElementsByClass("recent-title")
							.text();
					Log.e("头标题", title);
					news.setTitle(title);
					String hdnum = ele.getElementsByClass("num").text();
					Log.e("互动次数", hdnum);
					news.setCommentCount(hdnum);
					String picurl = ele.getElementsByTag("img").attr(
							"data-original");
					Log.e("图片呢", picurl);
					news.setImgrul(picurl);
					String aa = ele.select("a").attr("href").trim();
					Log.e("内容链接地址", aa);
					news.setContentUrl(aa);
					ListNews.add(news);
				}
				Log.e("List", ListNews.size() + "ge ");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			callback.invoke(ListNews);
		}
	}
}
