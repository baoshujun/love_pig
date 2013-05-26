package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.manager.PigFactoryManager;
import com.lovepig.model.BoarCateModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:40:10 PM
 * 此类展现种猪list信息
 */
public class BoarListView extends BaseView implements OnItemClickListener {
	private ListView boarListView;
	private BoarCateListViewAdapter adapter;
	private TextView categoryName;

	public BoarListView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);

		boarListView = (ListView) findViewById(R.id.boarLv);
		boarListView.setOnItemClickListener(this);
		categoryName = (TextView)findViewById(R.id.title);
		categoryName.setText("主营产品");
	}

	public void setListViewAdapter(ArrayList<BoarCateModel> datas) {
		adapter = new BoarCateListViewAdapter(datas, context);
		boarListView.setAdapter(adapter);
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
		manager.sendMessageDelayed(manager.obtainMessage(PigFactoryManager.TO_GET_BOAR_DETAIL, position,0),500);
	}
	

}
