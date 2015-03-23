package com.loujin.fragment;
import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loujin.adapter.NewAdapter;
import com.loujin.itsnew.CallBack;
import com.loujin.itsnew.R;
import com.loujin.material.FloatingActionButton;
import com.loujin.model.News;
import com.loujin.task.JxHtmlTask;

import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class NewFragment extends BaseFragment implements  OnRefreshListener2<ListView> {
	
	private static final String ARG_POSITION = "position";
	private View rootView = null;
	private FloatingActionButton fab;
	private CallBack<Boolean> callback;
	private NewAdapter adapter;
	private PullToRefreshListView listview = null;
	private List<News> ListNews = new ArrayList<News>();
	public static NewFragment newInstance(int position) {
		    NewFragment f = new NewFragment();
	        Bundle b = new Bundle();
	        b.putInt(ARG_POSITION, position);
	        f.setArguments(b);
	        return f;
	    }
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.e("onCreateView1", "onCreateView1");
			if(rootView==null){
				rootView = inflater.inflate(R.layout.page, container, false);
				super.init(rootView);
			    initListView();
			    initfab();
			    showfab();
			    initData();
			}
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
			return rootView;
		}
	 
	 private void initListView(){
		  listview = (PullToRefreshListView)rootView.findViewById(R.id.ContactListView);
		  Drawable loadingDrawable = getResources().getDrawable(R.drawable.pull_to_refresh_indicator);
		  final int indicatorWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,
	                getResources().getDisplayMetrics());
	        loadingDrawable.setBounds(new Rect(0, indicatorWidth, 0, indicatorWidth));
	        listview.getLoadingLayoutProxy().setLoadingDrawable(loadingDrawable);

	        listview.getRefreshableView().setCacheColorHint(Color.WHITE);
	        listview.getRefreshableView().setSelector(new ColorDrawable(Color.WHITE));
	        listview.getRefreshableView().addHeaderView(
	                LayoutInflater.from(getActivity()).inflate(R.layout.tt_messagelist_header,
	                		listview.getRefreshableView(), false));
	        listview.setOnRefreshListener(NewFragment.this);
	 }
	 private void initfab(){
		 fab = (FloatingActionButton) rootView
					.findViewById(R.id.fabButton);
	 }
	 private void showfab(){
		 if(fab.VISIBLE==View.GONE){
			 fab.setVisibility(View.VISIBLE);
		 }
		 fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));
			
		 fab.setBackgroundColor(getResources().getColor(R.color.red));
	 }
	 private void hidefab(){
		 fab.setVisibility(View.GONE);
	 }
	 private void setadapter(){
		 adapter = new NewAdapter(getActivity(), ListNews);
		 listview.setAdapter(adapter);
	 }
	 private void initData(){
		 	Log.e("加载数据", "加载数据");
		 	if(ListNews.size()!=0){
		 		return;
		 	}
		 	showprogress(1);
			JxHtmlTask task = JxHtmlTask.getInstance(getActivity());
			task.initNewTask(new CallBack<List<News>>() {

				@Override
				public void invoke(List<News> arg) {
					// TODO Auto-generated method stub
					ListNews = arg;
					setadapter();
					hideprogress();
				}
			});

	 }
	 
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		if(isVisibleToUser){
			
		}
	}
	@Override
	public void onPullUpToRefresh (PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPullDownToRefresh (final PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		refreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ListView mlist = listview.getRefreshableView();
                if (!(mlist).isStackFromBottom()) {
                    mlist.setStackFromBottom(true);
                }
                mlist.setStackFromBottom(false);
				Log.e("执行到？", "执行");
                refreshView.onRefreshComplete();
			}
		}, 2000);
	}
}
