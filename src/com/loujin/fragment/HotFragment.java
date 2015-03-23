package com.loujin.fragment;

import java.util.ArrayList;
import java.util.List;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loujin.adapter.FavAdapter;
import com.loujin.adapter.HotAdapter;
import com.loujin.itsnew.CallBack;
import com.loujin.itsnew.R;
import com.loujin.material.FloatingActionButton;
import com.loujin.material.ProgressBarCircular;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class HotFragment extends BaseFragment implements  OnRefreshListener2<ListView> {

	private static final String ARG_POSITION = "position";
	private View rootView = null;
	private PullToRefreshListView listview;
	private FloatingActionButton fab;
	private CallBack<Boolean> callback;
	private HotAdapter adapter;
	private List<News> ListNews = new ArrayList<News>();
	private Boolean isload = false;

	public static HotFragment newInstance(int position) {
		HotFragment f = new HotFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.e("CreatView", "3");
		if (null == rootView) {
			Log.e("onCreateView3", "onCreateView3");
			rootView = inflater.inflate(R.layout.page, container, false);
			super.init(rootView);
			initListView();
			initfab();
			showfab();
			if (isload) {
				initData();
			}
			fab.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					scrollToPosition(0);
				}
			});
		}
		ViewGroup parent = (ViewGroup) rootView.getParent();
		if (parent != null) {
			parent.removeView(rootView);
		}
		return rootView;
	}

	/**
	 * 滑动到指定位置
	 * 
	 * @param position
	 */
	private void scrollToPosition(final int position) {
		try {
			if (position < 0) {
				return;
			}
			if (listview != null) {
				listview.scrollBy(0, 0);
			}
		} catch (Exception e) {

		}
	}

	private void initListView() {
		listview = (PullToRefreshListView) rootView.findViewById(R.id.ContactListView);
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
	        listview.setOnRefreshListener(HotFragment.this);
	}

	private void initfab() {
		fab = (FloatingActionButton) rootView.findViewById(R.id.fabButton);
	}

	private void showfab() {
		if (fab.VISIBLE == View.GONE) {
			fab.setVisibility(View.VISIBLE);
		}
		fab.setDrawableIcon(getResources().getDrawable(R.drawable.plus));

		fab.setBackgroundColor(getResources().getColor(R.color.red));
	}

	private void hidefab() {
		fab.setVisibility(View.GONE);
	}

	private void setadapter() {
		adapter = new HotAdapter(getActivity(), ListNews);
		listview.setAdapter(adapter);
	}

	private void initData() {
		Log.e("加载数据3", "加载数据3");
		if (ListNews.size() != 0) {
			return;
		}
		isload = false;
		showprogress(2);
		JxHtmlTask task = JxHtmlTask.getInstance(getActivity());
		task.initHotTask(new CallBack<List<News>>() {

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
		if (isVisibleToUser) {
			if (rootView == null) {
				isload = true;
				return;
			}
			initData();
		}
	}

	@Override
	public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
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

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		
	}
}
