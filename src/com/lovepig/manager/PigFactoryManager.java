package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewAnimator;

import com.lovepig.engine.PigFactoryEngine;
import com.lovepig.engine.PriceEngine;
import com.lovepig.main.R;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.OnlineNewsDetailsView;
import com.lovepig.view.PigFactoryView;
import com.lovepig.view.PigFactoryDetailView;
import com.lovepig.view.PriceDetailView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:34:29 PM
 * 
 */
public class PigFactoryManager extends BaseManager  {
	public static final int TOGETDETAILDATA = 3; 
	public static final int SETDETAILVIEW = 4;
	private PigFactoryView pigFactoryView;
	private PigFactoryDetailView detailView;

	private PigFactoryEngine pigFactoryEngine;
	private ArrayList<PigFactoryModel> datas;
	

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
			if (detailView == null) {
				detailView = new PigFactoryDetailView(context,R.layout.pigfactory_detail, this);
			}
			if (dcEngine.getNowDC() != detailView) {
				enterSubDC(detailView);
			}
			break;
		case PigFactoryManager.TOGETDETAILDATA: //获取详情
			
			int id = datas.get(msg.arg1).id;
			fetchPigDetailData(id);
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
			fetchPriceListData();
			break;
		case R.id.rightBtn:
			showAlert("222");
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

}
