package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

public class FavoriteView extends BaseView implements OnItemClickListener {
	ArrayList<NewsDetailModel> list;
	ListView mListView;
	FavoriteAdapter mFavoriteAdapte;
	Button mBackBtn;
	private BaseManager manager;
	private TextView title;
	private RelativeLayout headRelativeLayout;

	public FavoriteView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		this.manager = manager;
		 headRelativeLayout = (RelativeLayout) findViewById(R.id.leftBtnLayout);
	        headRelativeLayout.setVisibility(View.VISIBLE);
		title = (TextView) findViewById(R.id.title);
	       
	        title.setText("收藏列表");
	        title.setVisibility(View.VISIBLE);
		mListView = (ListView) findViewById(R.id.listView1);
		mListView.setOnItemClickListener(this);
		mFavoriteAdapte = new FavoriteAdapter(manager);

		mBackBtn = (Button) findViewById(R.id.leftBtn);
		mBackBtn.setVisibility(View.VISIBLE);

		mBackBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FavoriteView.this.manager.back();
			}
		});

	}

	public void setDatas(ArrayList<NewsDetailModel> datas) {
		list = datas;
		mFavoriteAdapte.setModels(list);
		mListView.setAdapter(mFavoriteAdapte);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view, int postion, long id) {
               manager.sendMessage(manager.obtainMessage(2, list.get(postion)));
	}

}
