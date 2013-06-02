package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewAnimator;

import com.lovepig.engine.BoarEngine;
import com.lovepig.main.R;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarCateModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.BoarMallDetailView;
import com.lovepig.view.BoarMallView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:51:05 PM
 * 
 */
public class BoarMallManager extends BaseManager implements OnItemClickListener {
	private BoarMallView boarMallView;
	private BoarMallDetailView boarMallDetailView;
	private BoarEngine boarEngine;
	private ArrayList<BoarCateModel> datas;
	private ArrayList<BoarBrandModel> brandDatas;
	private ArrayList<BoarAreaModel> areaDatas;

	public BoarMallManager(BaseActivity c) {
		super(c);
//		boarEngine = new BoarEngine(this);

	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:

			break;
		case 2:
			break;
		case 3:

			break;

		default:
			break;
		}

	}

	@Override
	public ViewAnimator getMainDC() {
		boarMallView = new BoarMallView(context, R.layout.boar_mall, this);
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
				boarEngine.fetchCategorization();
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
				boarEngine.fetchBrand();
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
				boarEngine.fetchArea();
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
