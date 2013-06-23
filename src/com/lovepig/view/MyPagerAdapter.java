package com.lovepig.view;

import java.util.List;

import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class MyPagerAdapter extends PagerAdapter {

	List<View> views;
	public MyPagerAdapter(List<View> v) {
		super();
	    views = v;
	}
	@Override
	public void destroyItem(View v, int pos, Object arg2) {
		((ViewPager) v).removeView(views.get(pos));
	}

	@Override
	public void finishUpdate(View arg0) {
		
	}

	@Override
	public int getCount() {
		if (views != null)
        {
            return views.size();
        }
        
        return 0;
	}

	@Override
	public Object instantiateItem(View v, int pos) {
		((ViewPager) v).addView(views.get(pos));
		return views.get(pos);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
		
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
		
	}
}
