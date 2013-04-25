package com.lovepig.dc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.engine.ListViewImageEngine;
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsModel;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.TlcyListLayout;

public class OnlineNewsAdapter extends BaseAdapter {
	OnlineNewsManager manager;
	private LayoutInflater layoutInflater = null;
	private ListView mListView;
	ListViewImageEngine syncImageLoader;
	TlcyListLayout mTlcyListLayout;

	public OnlineNewsAdapter(OnlineNewsManager m, ListView listView,
			TlcyListLayout mTlcyListLayout) {
		manager = m;
		layoutInflater = LayoutInflater.from(manager.context);
		mListView = listView;
		this.mTlcyListLayout = mTlcyListLayout;
		syncImageLoader = new ListViewImageEngine(mListView,
				this.mTlcyListLayout);
	}

	@Override
	public int getCount() {

		return manager.engine.getData().size();
	}

	@Override
	public Object getItem(int position) {
		return manager.engine.getData().get(position);
	}

	@Override
	public long getItemId(int position) {
		return manager.engine.getData().get(position).id;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		if (position == 0) {
			return 1;
		}
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder viewHolder;
		LogInfo.LogOut("OnlineNewsAdapter", "OnlineNewsAdapter-->getView:"
				+ manager.news.size());
		NewsModel news = manager.engine.getData().get(position);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			if (position == 0) {
				view = new OnlineNewsFirstDC(manager.context,
						R.layout.online_news_item_first, manager);
			} else {
				view = layoutInflater.inflate(R.layout.online_news_item, null);
				viewHolder.title = (TextView) view
						.findViewById(R.id.onlinetitle);
				viewHolder.instro = (TextView) view
						.findViewById(R.id.onlineinstro);
				viewHolder.img = (ImageView) view.findViewById(R.id.onlinepic);
			}
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
			view.setFocusable(true);
			view.requestFocus();
		}
		if (position == 0) {
			((OnlineNewsFirstDC) view).initData(news);
		} else {
			viewHolder.img.setVisibility(View.VISIBLE);
			if (position == 0) {
				// syncImageLoader.imageLoader(viewHolder.img, news.picurl,
				// R.drawable.news_detail_head, position);
			} else {
				if (URLUtil.isHttpUrl(news.picurl)) {
					syncImageLoader.imageLoaderScale(viewHolder.img,
							news.picurl + "_160x85.jpg", R.drawable.item_bg,
							position);
				} else {
					viewHolder.img.setImageResource(R.drawable.item_bg);
					// viewHolder.img.setVisibility(View.GONE);
				}
			}

			LogInfo.LogOut("pos" + position);
			viewHolder.title.setText(news.title);
			viewHolder.instro.setText(news.intro);
			viewHolder.img.setFocusable(false);
			viewHolder.title.setFocusable(false);
			viewHolder.instro.setFocusable(false);
			view.setFocusable(false);
		}
		return view;
	}

	public class ViewHolder {
		TextView title;
		TextView instro;
		ImageView img;
	}
}
