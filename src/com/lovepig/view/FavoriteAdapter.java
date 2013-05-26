package com.lovepig.view;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.lovepig.engine.ImageEngine;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.LogInfo;

public class FavoriteAdapter extends BaseAdapter {
	BaseManager manager;
	
	private ArrayList<NewsDetailModel> models;

	public void setModels(ArrayList<NewsDetailModel> models) {
		this.models = models;
	}

	public FavoriteAdapter(BaseManager m) {
		manager = m;

	}

	@Override
	public int getCount() {
		return models == null ? 0 : models.size();
	}

	@Override
	public Object getItem(int position) {
		return models.get(position);
	}

	@Override
	public long getItemId(int position) {
		return models.get(position).id;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder viewHolder;
		NewsDetailModel news = models.get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			view = View.inflate(Application.application.getApplicationContext(),R.layout.online_news_item, null);
			viewHolder.title = (TextView) view.findViewById(R.id.onlinetitle);
			viewHolder.instro = (TextView) view.findViewById(R.id.onlineinstro);
			viewHolder.img = (ImageView) view.findViewById(R.id.onlinepic);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		if (URLUtil.isHttpUrl(news.iconPath)) {
			ImageEngine.setImageBitmapScale(news.iconPath, viewHolder.img,
					R.drawable.ic_launcher, position);
		} else {
			viewHolder.img.setImageResource(R.drawable.ic_launcher);
		}
		LogInfo.LogOut("pos" + position);
		viewHolder.title.setText(news.title);
		viewHolder.instro.setText(news.summary);
		viewHolder.img.setFocusable(false);
		viewHolder.title.setFocusable(false);
		viewHolder.instro.setFocusable(false);
		view.setFocusable(false);
		return view;
	}

	public class ViewHolder {
		TextView title;
		TextView instro;
		ImageView img;
	}
}
