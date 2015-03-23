package com.loujin.fragment;
import java.util.ArrayList;
import java.util.List;

import staggered.StaggeredGridView;

import com.loujin.adapter.FavAdapter;
import com.loujin.itsnew.CallBack;
import com.loujin.itsnew.R;
import com.loujin.material.FloatingActionButton;
import com.loujin.model.News;
import com.loujin.task.JxHtmlTask;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

public class FavFragment extends BaseFragment {
	
	private static final String ARG_POSITION = "position";
	private int position;
	private View rootView = null;
//	private ListView listview;
	private FloatingActionButton fab;
	private CallBack<Boolean> callback;
	private FavAdapter adapter;
	private StaggeredGridView grildview;
	private List<News> ListNews = new ArrayList<News>();
	 public static FavFragment newInstance(int position) {
		    FavFragment f = new FavFragment();
	        Bundle b = new Bundle();
	        b.putInt(ARG_POSITION, position);
	        f.setArguments(b);
	        return f;
	    }
	 @Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			Log.e("onCreateView2", "onCreateView2");
			if(rootView==null){
				rootView = inflater.inflate(R.layout.fav_layout, container, false);
				super.init(rootView);
			    initListView();
			    initfab();
			    showfab();
				fab.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(getActivity(), "点我了", Toast.LENGTH_LONG).show();
					}
				});
			}
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null) {
				parent.removeView(rootView);
			}
			return rootView;
		}
	 
	 private void initListView(){
//		  listview = (ListView)rootView
//		    		.findViewById(R.id.ContactListView);
		  grildview = (StaggeredGridView)rootView.findViewById(R.id.ContactListView);
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
		 adapter = new FavAdapter(getActivity(), ListNews);
		 grildview.setAdapter(adapter);
	 }
	 private void initData(){
		 	Log.e("加载数据1", "加载数据1");
		 	if(ListNews.size()!=0){
		 		return;
		 	}
		 	showprogress(2);
			JxHtmlTask task = JxHtmlTask.getInstance(getActivity());
			task.initFavTask(new CallBack<List<News>>() {

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
			  initData();
		}
	}
}
