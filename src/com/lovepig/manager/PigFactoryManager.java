package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.widget.ViewAnimator;

import com.lovepig.engine.PigFactoryEngine;
import com.lovepig.main.R;
import com.lovepig.model.BoarCateModel;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.BoarDetailDC;
import com.lovepig.view.BoarListView;
import com.lovepig.view.PigFactoryDetailView;
import com.lovepig.view.PigFactoryView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:34:29 PM
 * 
 */
public class PigFactoryManager extends BaseManager  {
	public static final int TO_GET_FACTORY_DETAIL = 3; 
	public static final int SETDETAILVIEW = 4;
	public static final int TOPRODUCTLISTVIEW  = 5;
	public static final int SETPRODUCTLISTVIEW = 6;
	public static final int TO_GET_BOAR_DETAIL = 7;
	public static final int SET_BOAR_DETAIL = 8;
	private PigFactoryView pigFactoryView;
	private PigFactoryDetailView detailView;
	private BoarListView boarListView;
	private BoarDetailDC boarDetailView;
	
	private int factoryId ;
	private int boarId;

	private PigFactoryEngine pigFactoryEngine;
	private ArrayList<PigFactoryModel> datas;
	private ArrayList<BoarCateModel> productDatas;
	

	public PigFactoryManager(BaseActivity c) {
		super(c);
		pigFactoryEngine = new PigFactoryEngine(this);
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case -1:
			fetchPriceListData();
			break;
		case 0:
			showAlert("联网超时，获取数据失败！");
			break;
		case 1: //获取listview
			datas = (ArrayList<PigFactoryModel>) msg.obj;
			pigFactoryView.setListViewAdapter(datas);
			break;
		case SETDETAILVIEW: //去设置详情页
			this.dismissLoading();
			if (detailView == null) {
				detailView = new PigFactoryDetailView(context,R.layout.pigfactory_detail, this);
			}
			if (dcEngine.getNowDC() != detailView) {
				enterSubDC(detailView);
			}
			break;
		case PigFactoryManager.TO_GET_FACTORY_DETAIL: //获取猪场详情
			factoryId = datas.get(msg.arg1).id;
			fetchPigDetailData(factoryId);
			break;
		case PigFactoryManager.TO_GET_BOAR_DETAIL: //获取种猪详情
			boarId = productDatas.get(msg.arg1).categorizationId;
			fetchBoarDetail(factoryId);
			break;
		case SET_BOAR_DETAIL: //去设置种猪详情页
			this.dismissLoading();
			if (boarDetailView == null) {
				boarDetailView = new BoarDetailDC(context,R.layout.boar_detail, this);
			}
			if (dcEngine.getNowDC() != boarDetailView) {
				enterSubDC(boarDetailView);
			}
			break;

		case PigFactoryManager.SETPRODUCTLISTVIEW: //设置主营产品页面
			if(boarListView == null){
				boarListView = new BoarListView(context, R.layout.boar, this);
			}
			if(dcEngine.getNowDC() != boarListView){
				enterSubDC(boarListView);
			}
			productDatas = (ArrayList<BoarCateModel>) msg.obj;
			boarListView.setListViewAdapter(productDatas);
			break;

		default:
			break;
		}
	}

	@Override
	public ViewAnimator getMainDC() {
		pigFactoryView = new PigFactoryView(context, R.layout.pig_factory, this);
		dcEngine.setMainDC(pigFactoryView);
		return super.getMainDC();
	}

	@Override
	public void onClicked(int id) {
		// TODO Auto-generated method stub
		super.onClicked(id);
		switch (id) {
		case R.id.leftBtn:
//			fetchPriceListData();
			break;
		case R.id.rightBtn:
			showAlert("222");
			break;
		case R.id.toListView:
		case R.id.pigProduct:
			showToast("11111");
			fetchPigProduct(factoryId);
			break;
		default:
			break;
		}
	}

	/**
     * 
     */
	protected void fetchPriceListData() {
		// （1）判断是否联网
		if (Utils.isNetworkValidate(context)) {
			// a.获取数据
			pigFactoryEngine.fetchPrice();
			// b.删除本地数据库
			// c.save 数据
			// d.显示页面
		} else {
			// a.显示本地数据
		}
	}
	/**
	 * 
	 */
	protected void fetchPigDetailData(int id) {
		// （1）判断是否联网
		if (Utils.isNetworkValidate(context)) {
			// a.获取数据
			pigFactoryEngine.fetchDetail(id);
			// b.删除本地数据库
			// c.save 数据
			// d.显示页面
		} else {
			// a.显示本地数据
		}
	}
	
	/**
	 * 获得猪场的主营产品信息
	 * params: id 猪场的ID
	 */
	protected void fetchPigProduct(int id) {
		// （1）判断是否联网
		if (Utils.isNetworkValidate(context)) {
			// a.获取数据
			pigFactoryEngine.fetchPigProduct(id);
			// b.删除本地数据库
			// c.save 数据
			// d.显示页面
		} else {
			// a.显示本地数据
		}
	}
	/**
	 * 获得猪场的主营产品信息
	 * params: id 猪场的ID
	 */
	protected void fetchBoarDetail(int id) {
		// （1）判断是否联网
		if (Utils.isNetworkValidate(context)) {
			// a.获取数据
			pigFactoryEngine.fetchBoarDetail(id);
			// b.删除本地数据库
			// c.save 数据
			// d.显示页面
		} else {
			// a.显示本地数据
		}
	}

}
