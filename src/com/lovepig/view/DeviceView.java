package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.lovepig.main.R;
import com.lovepig.manager.PigFactoryManager;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseView;
import com.lovepig.pivot.BaseManager;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:40:10 PM
 * 
 */
public class DeviceView extends BaseView {
	// livePig 生猪 piglet仔猪
	private Button livePig, piglet;
	private ListView priceListview;
	private PigFactoryListViewAdapter adapter;

	public DeviceView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		livePig = (Button) findViewById(R.id.leftBtn);
		piglet = (Button) findViewById(R.id.rightBtn);
		livePig.setText("生猪");
		piglet.setText("仔猪");
		
		priceListview = (ListView) findViewById(R.id.pigfactoryLv);
		livePig.setOnClickListener(this);
		piglet.setOnClickListener(this);
//		priceListview.setOnItemClickListener((PigFactoryManager)manager);
	}

	

	public void setListViewAdapter(ArrayList<PriceModel> datas) {
//		adapter = new PigFactoryListViewAdapter(datas, context);
		priceListview.setAdapter(adapter);
	}

}
