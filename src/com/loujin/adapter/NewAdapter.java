package com.loujin.adapter;
import java.util.List;

import com.loujin.itsnew.R;
import com.loujin.model.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewAdapter extends BaseAdapter {
	private Context context;
	public List<News> ListNews;
	private LayoutInflater inflater;
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;
	public NewAdapter(Context context,List<News> ListNews){
		this.context = context;
		this.ListNews = ListNews;
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_launcher)
		.showImageForEmptyUri(R.drawable.ic_launcher)
		.showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
		.cacheOnDisc(true).considerExifParams(true)
		.bitmapConfig(Bitmap.Config.RGB_565)
		// .displayer(new RoundedBitmapDisplayer(10))//设置圆角
		.build();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ListNews.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return ListNews.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (inflater == null)
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.feed_item, null);

		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView txthd = (TextView) convertView
				.findViewById(R.id.txthd);
		ImageView profilePic = (ImageView) convertView
				.findViewById(R.id.profilePic);
	    News news = ListNews.get(position);
	    name.setText(news.getTitle());
	    txthd.setText(news.getCommentCount()+"----pos"+position);
	    imageLoader.displayImage(news.getImgrul(), profilePic,options ,new ImageLoadingListener() {
			
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingFailed(String imageUri, View view,
					FailReason failReason) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				
			}
		});
		return convertView;
	}
	

}
