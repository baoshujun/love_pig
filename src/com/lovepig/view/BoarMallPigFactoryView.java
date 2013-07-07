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

import com.lovepig.engine.database.PigFactoryDBEngine;
import com.lovepig.main.R;
import com.lovepig.manager.BoarMallManager;
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
	private Button brand,scale,search;
	private PopupWindow window;
	private List<Map<String, Object>> mDatas;//规模或者品类
	private List<PigFactoryModel> pfmDatas;
	private ListView list;
	private boolean isScale;
	

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
		search = (Button)findViewById(R.id.areaSearch);
		brand.setOnClickListener(brandListener);
		scale.setOnClickListener(scaleListener);
		search.setOnClickListener(searchListener);
		
	}
	
	private void initBrandData(){
		mDatas = new ArrayList<Map<String, Object>>();
/*		1	杜洛克
		2	大约克
		3	长白
		4	皮特兰
		5	汉普夏
		6	巴克夏
		7	配套系
		8	黑猪
		9	二元
		10	其他 */
		String arr[] = {"杜洛克","大约克","长白","皮特兰","汉普夏","巴克夏","配套系","黑猪","二元","其他"};
		
		for (int i = 1; i <= 10 ; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", arr[i-1]);
			map.put("id",i);
			mDatas.add(map);
		}
	}
	
	private void initScaleData(){
		mDatas = new ArrayList<Map<String, Object>>();
		/**
		 * 	201	1000以下
	     	202	1000-3000
			203	3000-5000
			204	5000-8000
			205	8000-10000
			206	10000以上
		 */
		String arr[] = {"1000以下","1000-3000","3000-5000","5000-8000","8000-10000","10000以上"};
		for (int i = 1; i <= 6; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title", arr[i-1]);
			map.put("id","20"+i);
			mDatas.add(map);
		}
	}
	
	public  OnClickListener brandListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			window = null;
			isScale = false;
			initBrandData();
			popAwindow(v);
		}
	};
	
	public OnClickListener scaleListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			window = null;
			initScaleData();
			isScale = true;
			popAwindow(v);
		}
	};
	public OnClickListener searchListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mDatas = new ArrayList<Map<String, Object>>();
			String name = null;
			if(pigName.getEditableText()== null || pigName.getEditableText().toString().equals("")){
				Toast.makeText(context, "输入猪场名字为空", Toast.LENGTH_SHORT).show();
			} else {
				name = pigName.getEditableText().toString();
				ArrayList<PigFactoryModel> pfmList = new PigFactoryDBEngine().findPigFactory(name, null, null);
				if(pfmList.size() == 0){
					showToast("查无此猪场哦！");
				} else {
					pfmDatas = pfmList;
					manager.sendMessage(manager.obtainMessage(BoarMallManager.CHANGE_PIG_FACTORY_LIST_VIEW, pfmDatas));
				}
			}
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
			String name = null;
			if(pigName.getEditableText()== null || pigName.getEditableText().toString().equals("")){
//				Toast.makeText(context, "输入猪场名字为空", Toast.LENGTH_SHORT).show();
				name = null;
			} else {
				name = pigName.getEditableText().toString();
			}
			ArrayList<PigFactoryModel> pfmList ;
			if(isScale){
				pfmList = new PigFactoryDBEngine().findPigFactory(name, null,mDatas.get(position).get("id").toString());
			} else {
				pfmList = new PigFactoryDBEngine().findPigFactory(name, mDatas.get(position).get("id").toString(),null);
			}
			
			if(pfmList.size() == 0){
				showToast("查无此猪场哦！");
			} else {
				pfmDatas = pfmList;
				manager.sendMessage(manager.obtainMessage(BoarMallManager.CHANGE_PIG_FACTORY_LIST_VIEW, pfmDatas));
			}
			if (window != null) {
				window.dismiss();
			}
			
		}
	};

	public void setListViewAdapter(ArrayList<PigFactoryModel> datas) {
		pfmDatas = datas;
		adapter = new PigFactoryListViewAdapter(datas, context);
		pigFactoryListView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
 
	@Override
	public void onShow() {
		super.onShow();
		manager.sendEmptyMessage(-1);
	}
   //进入猪场详情页，需要判断是否已经登录，若是已经登录可以直接进入 若是无登录需要显示登录页面
	@Override
	public void onItemClick(AdapterView<?> itemview, View view, int position, long arg3) {
		String choiceId = pfmDatas.get(position).id +"";
		manager.sendMessage(manager.obtainMessage(BoarMallManager.GET_PIG_FACTORY_DETAIL_DATA, choiceId));
	}
	

}
