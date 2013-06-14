package com.lovepig.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
public class BoarMallPigFactoryView extends BaseView implements OnItemClickListener {
	private ListView pigFactoryListView;
	private PigFactoryListViewAdapter adapter;
	private TextView tv;
	private EditText pigName;
	private Button brand,scale;
	private PopupWindow window;
	private List<Map<String, Object>> mDatas;
	private ListView list;
	

	public BoarMallPigFactoryView(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		pigFactoryListView = (ListView) findViewById(R.id.pigfactoryLv);
		pigFactoryListView.setOnItemClickListener(this);
		tv = (TextView)findViewById(R.id.tv);
		tv.setText("猪场");
		pigName = (EditText)findViewById(R.id.provineceET);
		pigName.setHint("请输入猪场名");
		brand = (Button)findViewById(R.id.brand);
		scale = (Button)findViewById(R.id.pigScale);
		brand.setOnClickListener(brandListener);
		scale.setOnClickListener(scaleListener);
	}
	
	private void initBrandData(){
		mDatas = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "品系" + i*10 );
			map.put("id",i*10);
			mDatas.add(map);
		}
	}
	
	private void initScaleData(){
		mDatas = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", "规模" + i*10 );
			map.put("id",i*10);
			mDatas.add(map);
		}
	}
	
	public  OnClickListener brandListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			window = null;
			initBrandData();
			popAwindow(v);
		}
	};
	
	public OnClickListener scaleListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			window = null;
			initScaleData();
			popAwindow(v);
		}
	};

	private void popAwindow(View et) {
		if (window == null) {
			LayoutInflater lay = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = lay.inflate(R.layout.pig_factory_popupwindow, null);
			list = (ListView) v.findViewById(R.id.lv);
			BoarMallPigPW adapter = new BoarMallPigPW(context,mDatas);
			list.setAdapter(adapter);
			list.setItemsCanFocus(false);
			list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			list.setOnItemClickListener(listClickListener);
			window = new PopupWindow(v,LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		}
		window.setBackgroundDrawable(new BitmapDrawable());
		window.setFocusable(true);
		window.update();
		window.showAsDropDown(et);
	}

	OnItemClickListener listClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(context, mDatas.get(position).get("id").toString(), Toast.LENGTH_SHORT).show();
			if (window != null) {
				window.dismiss();
			}
		}
	};

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
		manager.sendMessageDelayed(manager.obtainMessage(PigFactoryManager.TO_GET_FACTORY_DETAIL, position,0),500);
	}
	

}
