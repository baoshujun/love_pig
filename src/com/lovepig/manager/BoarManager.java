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
import com.lovepig.view.BoarDC;
import com.lovepig.view.BoarDetailDC;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:51:05 PM
 * 
 */
public class BoarManager extends BaseManager implements OnItemClickListener {
	private BoarDC boarDC;
	private BoarDetailDC boarDetailDC;
	private BoarEngine boarEngine;
	private ArrayList<BoarCateModel> datas;
	private ArrayList<BoarBrandModel> brandDatas;
	private ArrayList<BoarAreaModel> areaDatas;

	public BoarManager(BaseActivity c) {
		super(c);
		boarEngine = new BoarEngine(this);

	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case 1:
			datas = (ArrayList<BoarCateModel>)msg.obj;
			//2013-05-09 00:12:37
			boarDC.setCateAdapter(datas);
			break;
		case 2:
			brandDatas = (ArrayList<BoarBrandModel>)msg.obj;
			boarDC.setBrandAdapter(brandDatas);
			break;
		case 3:
			areaDatas = (ArrayList<BoarAreaModel>)msg.obj;
			//2013-05-09 00:12:37
			boarDC.setAreaAdapter(areaDatas);
			break;

		default:
			break;
		}

	}

	@Override
	public ViewAnimator getMainDC() {
		boarDC = new BoarDC(context, R.layout.boar, this);
		dcEngine.setMainDC(boarDC);
		boarDetailDC = new BoarDetailDC(context, R.layout.boar_detail, this);
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
