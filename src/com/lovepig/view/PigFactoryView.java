package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lovepig.main.R;
import com.lovepig.manager.PigFactoryManager;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:40:10 PM
 * 
 */
public class PigFactoryView extends BaseView implements OnItemClickListener {
	private ListView pigFactoryListView;
	private PigFactoryListViewAdapter adapter;

	public PigFactoryView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);

		pigFactoryListView = (ListView) findViewById(R.id.pigfactoryLv);
		pigFactoryListView.setOnItemClickListener(this);
	}

	public void setListViewAdapter(ArrayList<PigFactoryModel> datas) {
		adapter = new PigFactoryListViewAdapter(datas, context);
		pigFactoryListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onShow() {
		super.onShow();
		manager.sendEmptyMessage(-1);
	}

	@Override
	public void onItemClick(AdapterView<?> itemview, View view, int position, long arg3) {
		showToast("111111");
		manager.showLoading();
		manager.sendMessageDelayed(manager.obtainMessage(PigFactoryManager.TOGETDETAILDATA, position,0),500);
	}
	

}
