package com.loujin.fragment;

import com.loujin.itsnew.R;
import com.loujin.material.ProgressBarCircular;

import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {
	
	private ProgressBarCircular progressBarCircular;
	
	public void init(View view){
		progressBarCircular = (ProgressBarCircular) view
				.findViewById(R.id.progress);
	}
	
	public void showprogress(int position){
		progressBarCircular.setVisibility(View.VISIBLE);
		switch (position) {
		case 0:
			progressBarCircular.setBackgroundColor(getResources().getColor(
					R.color.material_deep_teal_500));
			break;
		case 1:
			progressBarCircular.setBackgroundColor(getResources().getColor(
					R.color.red));

			break;
		case 2:
			progressBarCircular.setBackgroundColor(getResources().getColor(
					R.color.blue));
			break;
		case 3:
			
			progressBarCircular.setBackgroundColor(getResources().getColor(
					R.color.material_blue_grey_800));

			break;
		}
	}
	public void hideprogress(){
		progressBarCircular.setVisibility(View.GONE);
	}

}
