package com.lovepig.manager;

import java.util.ArrayList;
import java.util.List;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewAnimator;

import com.lovepig.engine.BoarEngine;
import com.lovepig.engine.BoarMallEngine;
import com.lovepig.main.R;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarCateModel;
import com.lovepig.model.BoarMallModel;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.BoarMallDetailView;
import com.lovepig.view.BoarMallPigFactoryView;
import com.lovepig.view.BoarMallView;
import com.lovepig.view.BoarPigFactoryDetailView;
import com.lovepig.view.PigFactoryView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:51:05 PM
 * 种猪mall
 * 
 */
public class BoarMallManager extends BaseManager implements OnItemClickListener {
	private BoarMallView boarMallView;
	private BoarMallDetailView boarMallDetailView;
	private BoarMallEngine boarMallEngine;
	private ArrayList<BoarMallModel> datas;
	private ArrayList<PigFactoryModel> pigFactoryList;
	private BoarMallPigFactoryView pigFactoryView;
	private BoarPigFactoryDetailView pigFactoryDetailView;

	public  BoarMallManager(BaseActivity c) {
		super(c);
		boarMallEngine = new BoarMallEngine(this);
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1://联网获取数据
			boarMallEngine.fetchBoarMallInfo();
			break;
		case 2:
			datas = (ArrayList<BoarMallModel>)msg.obj;
			boarMallView.setListViewAdapter(datas);
			break;
		case 3://点击后获取种猪场数据
			String id = (String)msg.obj;
			Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
			boarMallEngine.fetchPigFactoryDetailData(id);
			break;
		case 6://进入种猪场布局
			pigFactoryDetailView = new BoarPigFactoryDetailView(context, R.layout.boar_pigfactory_detail, this);
			if (dcEngine.getNowDC() != pigFactoryDetailView) {
				enterSubDC(pigFactoryDetailView);
			}
			break;
		case 4://选择省份后获取数据
			String provinceId = (String)msg.obj;
			boarMallEngine.fetchProvincePigFactory(provinceId);
			break;
		case 5://进入选择省份种猪页面
			pigFactoryView = new BoarMallPigFactoryView(context, R.layout.boar_mall_pig_factory, this);
			pigFactoryList = (ArrayList<PigFactoryModel>) msg.obj;
			pigFactoryView.setListViewAdapter(pigFactoryList);
			if (dcEngine.getNowDC() != pigFactoryView) {
				enterSubDC(pigFactoryView);
			}
			break;
		default:
			break;
		}

	}
	
	@Override
	public void initData() {
		sendMessage(obtainMessage(1, 0));
	}

	@Override
	public ViewAnimator getMainDC() {
		boarMallView = new BoarMallView(context, R.layout.boar_mall_view, this);
		dcEngine.setMainDC(boarMallView);
//		boarMallDetailView = new (context, R.layout.boar, this);
		return super.getMainDC();
	}

	@Override
	public void onItemClick(AdapterView<?> ListView, View itemView,
			int position, long id) {

	}

	@Override
	public void onClicked(int id) {
		super.onClicked(id);
		switch (id) {
		case R.id.leftBtn:
			// （1）判断是否联网
			if (Utils.isNetworkValidate(context)) {
				// a.获取数据
				// b.删除本地数据库
				// c.save 数据
				// d.显示页面

			} else {
				// a.显示本地数据
			}
			break;
		case R.id.middleBtn:
			// （1）判断是否联网
			if (Utils.isNetworkValidate(context)) {
				// a.获取数据
				boarMallEngine.fetchBrand();
				// b.删除本地数据库
				// c.save 数据
				// d.显示页面
			} else {
				// a.显示本地数据
			}
			break;
		case R.id.rightBtn:
			// （1）判断是否联网
			if (Utils.isNetworkValidate(context)) {
				// a.获取数据
				boarMallEngine.fetchArea();
				// b.删除本地数据库
				// c.save 数据
				// d.显示页面

			} else {
				// a.显示本地数据
			}
			break;

		default:
			break;
		}
	}

}
