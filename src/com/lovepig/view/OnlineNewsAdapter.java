package com.lovepig.view;

import android.util.Log;
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

    public OnlineNewsAdapter(OnlineNewsManager m, ListView listView, TlcyListLayout mTlcyListLayout) {
        manager = m;
        layoutInflater = LayoutInflater.from(manager.context);
        mListView = listView;
        this.mTlcyListLayout = mTlcyListLayout;
        syncImageLoader = new ListViewImageEngine(mListView, this.mTlcyListLayout);
    }

    @Override
    public int getCount() {
        return manager.news.size() > 0 ? manager.news.size() - 1 : 0;
    }

    @Override
    public Object getItem(int position) {
        return manager.news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return manager.news.get(position).id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder;
        // 从第二条记录开始
        NewsModel news = manager.news.get(position + 1);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.online_news_item, null);
            viewHolder.title = (TextView) view.findViewById(R.id.onlinetitle);
            viewHolder.instro = (TextView) view.findViewById(R.id.onlineinstro);
            viewHolder.img = (ImageView) view.findViewById(R.id.onlinepic);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.img.setVisibility(View.VISIBLE);
        if (URLUtil.isHttpUrl(news.iconPath)) {
            syncImageLoader.imageLoaderScale(viewHolder.img, news.iconPath, R.drawable.ic_launcher, position);
        } else {
            viewHolder.img.setImageResource(R.drawable.ic_launcher);
            viewHolder.img.setVisibility(View.GONE);
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
