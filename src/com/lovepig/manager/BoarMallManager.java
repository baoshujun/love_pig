package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.lovepig.engine.BoarMallEngine;
import com.lovepig.engine.database.PigFactoryDBEngine;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.model.BoarMallModel;
import com.lovepig.model.BoarPigFactoryDetailModel;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.ConfigInfo;
import com.lovepig.utils.Utils;
import com.lovepig.view.BoarMallDetailView;
import com.lovepig.view.BoarMallPigFactoryView;
import com.lovepig.view.BoarMallView;
import com.lovepig.view.BoarPigFactoryDetailView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:51:05 PM
 * 种猪mall
 * 
 */
public class BoarMallManager extends BaseManager implements OnItemClickListener {
	public static final int GET_BOAR_MALL_DATA = 1;//获得进入商城数据
	public static final int SET_BOAR_MALL_LISTVIEW = 2;//设置商城布局
	public static final int GET_PIG_FACTORY_DETAIL_DATA = 3;
	public static final int SET_PIG_FACTORY_DETAIL_VIEW = 6;
	public static final int GET_PIG_FACTORY_LIST_DATA = 4;
	public static final int SET_PIG_FACTORY_LIST_VIEW = 5;
	public static final int CHANGE_PIG_FACTORY_LIST_VIEW = 7;
	
	private BoarMallView boarMallView;
	private BoarMallDetailView boarMallDetailView;
	private BoarMallEngine boarMallEngine;
	private ArrayList<BoarMallModel> datas;
	private ArrayList<PigFactoryModel> pigFactoryList;
	private BoarMallPigFactoryView pigFactoryView;
	private BoarPigFactoryDetailView pigFactoryDetailView;
	public PigFactoryDBEngine pigFactoryDBEngine ;

	public  BoarMallManager(BaseActivity c) {
		super(c);
		boarMallEngine = new BoarMallEngine(this);
		if (pigFactoryDBEngine == null) {
			pigFactoryDBEngine = new PigFactoryDBEngine();
		}
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case GET_BOAR_MALL_DATA://联网获取数据
			boarMallEngine.fetchBoarMallInfo();
			break;
		case SET_BOAR_MALL_LISTVIEW:
			datas = (ArrayList<BoarMallModel>)msg.obj;
			boarMallView.setListViewAdapter(datas);
			break;
		case GET_PIG_FACTORY_DETAIL_DATA://点击后获取种猪场详情数据
			String id = (String)msg.obj;
			Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
			//需要判断用户是否注册，若是无注册需要注册
			if(ConfigInfo.getMemberAccount() != null && !ConfigInfo.getMemberAccount().equals("")){
				boarMallEngine.fetchPigFactoryDetailData(id);
			} else {
				 if (!Utils.isNetworkValidate(context)) {
		                showAlert("网络不可用,请检查您的网络！");
		                return;
		            }
		            Application.userManager.EnterUserManager(R.string.user_info);
			}
			break;
		case SET_PIG_FACTORY_DETAIL_VIEW://进入种猪场详情布局
			BoarPigFactoryDetailModel pfm = (BoarPigFactoryDetailModel)msg.obj;
			pigFactoryDetailView = new BoarPigFactoryDetailView(context, R.layout.boar_pigfactory_detail, this,pfm);
			if (dcEngine.getNowDC() != pigFactoryDetailView) {
				enterSubDC(pigFactoryDetailView);
			}
			break;
		case GET_PIG_FACTORY_LIST_DATA://选择省份后获取数据
			String provinceId = (String)msg.obj;
			boarMallEngine.fetchProvincePigFactory(provinceId);
			break;
		case SET_PIG_FACTORY_LIST_VIEW://进入选择省份种猪页面
			pigFactoryView = new BoarMallPigFactoryView(context, R.layout.boar_mall_pig_factory, this);
			pigFactoryList = (ArrayList<PigFactoryModel>) msg.obj;
			pigFactoryView.setListViewAdapter(pigFactoryList);
			if (dcEngine.getNowDC() != pigFactoryView) {
				enterSubDC(pigFactoryView);
			}
			break;
		case CHANGE_PIG_FACTORY_LIST_VIEW://改变省份种猪页面
			pigFactoryList = (ArrayList<PigFactoryModel>) msg.obj;
			pigFactoryView.setListViewAdapter(pigFactoryList);
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
