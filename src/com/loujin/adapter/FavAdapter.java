package com.loujin.adapter;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.loujin.configs.Options;
import com.loujin.itsnew.R;
import com.loujin.model.News;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FavAdapter extends BaseAdapter {
	private Context context;
	private List<News> ListNews;
	private LayoutInflater inflater;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	DisplayImageOptions options;

	public FavAdapter(Context context, List<News> ListNews) {
		this.context = context;
		this.ListNews = ListNews;
		// options = new DisplayImageOptions.Builder()
		// .showImageOnLoading(R.drawable.ic_launcher)
		// .showImageForEmptyUri(R.drawable.ic_launcher)
		// .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
		// .cacheOnDisc(true).considerExifParams(true)
		// .bitmapConfig(Bitmap.Config.RGB_565)
		// // .displayer(new RoundedBitmapDisplayer(10))//设置圆角
		// .build();
		options = Options.getListOptions();
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
		if (convertView == null){
			convertView = inflater.inflate(R.layout.fav_item, null);
		}
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView txthd = (TextView) convertView.findViewById(R.id.txthd);
		ImageView profilePic = (ImageView) convertView
				.findViewById(R.id.feedImage1);
		News news = ListNews.get(position);
		name.setText(news.getTitle());
		txthd.setText(news.getCommentCount());
		imageLoader.displayImage(news.getImgrul(), profilePic,options,animateFirstListener);
		return convertView;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}

}
