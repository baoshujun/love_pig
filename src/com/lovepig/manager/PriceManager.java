package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewAnimator;

import com.lovepig.engine.PriceEngine;
import com.lovepig.main.R;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.PriceView;
import com.lovepig.view.PriceDetailView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:34:29 PM
 * 
 */
public class PriceManager extends BaseManager implements OnItemClickListener {
	private PriceView priceDC;
	private PriceDetailView detailDC;

	private PriceEngine priceEngine;
	private ArrayList<PriceModel> datas;

	public PriceManager(BaseActivity c) {
		super(c);
		priceEngine = new PriceEngine(this);
	}

	@Override
	public void initData() {
		super.initData();
		
	}

	@SuppressWarnings("unchecked")
    @Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case -1:
            fetchPriceListData();
            break;
		case 0:
            showAlert("联网超时，获取数据失败！");
            break;   
		case 1:
			datas = (ArrayList<PriceModel>) msg.obj;
			priceDC.setListViewAdapter(datas);
			break;
		
		default:
			break;
		}
	}

	@Override
	public ViewAnimator getMainDC() {
		priceDC = new PriceView(context, R.layout.price, this);
		dcEngine.setMainDC(priceDC);
		detailDC = new PriceDetailView(context, R.layout.price_detail, this);
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
        	priceEngine.fetchPrice();
        	// b.删除本地数据库
        	// c.save 数据
        	// d.显示页面
        } else {
        	// a.显示本地数据
        }
    }

	@Override
	public void onItemClick(AdapterView<?> ListView, View itemView,
			int postion, long id) {
		enterSubDC(detailDC);

	}

}
