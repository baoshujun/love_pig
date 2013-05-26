package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lovepig.main.R;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

public class FavoriteView extends BaseView implements OnItemClickListener{
	ArrayList<NewsDetailModel> list;
	ListView mListView;
	FavoriteAdapter mFavoriteAdapte;

	public FavoriteView (Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		mListView =(ListView )findViewById(R.id.listView1); 
		mListView.setOnItemClickListener(this);
		mFavoriteAdapte=new FavoriteAdapter(manager);
		
		
	}
	 public void setDatas(ArrayList<NewsDetailModel> datas){
		 list=datas;
		 mFavoriteAdapte.setModels(list);
		 mListView.setAdapter(mFavoriteAdapte);
	 }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
	}
	

}
