package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ViewAnimator;
import android.widget.AdapterView.OnItemClickListener;

import com.lovepig.dc.PriceDC;
import com.lovepig.dc.PriceListViewAdapter;
import com.lovepig.engine.PriceEngine;
import com.lovepig.main.R;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 3:34:29 PM
 * 
 */
public class PriceManager extends BaseManager implements OnItemClickListener {
	private PriceDC priceDC;

	private PriceEngine priceEngine;
	private ArrayList<PriceModel> datas;
	

	public PriceManager(BaseActivity c) {
		super(c);
		priceEngine=new PriceEngine(this);
	}

	@Override
	public void handleMessage(Message msg) {
       switch (msg.what) {
	case 1:
		datas=(ArrayList<PriceModel>)msg.obj;
		priceDC.setListViewAdapter(datas);
		break;

	default:
		break;
	}
	}

  
	@Override
	public ViewAnimator getMainDC() {
		priceDC = new PriceDC(context, R.layout.price, this);
		dcEngine.setMainDC(priceDC);
		return super.getMainDC();
	}

	@Override
	public void onClicked(int id) {
		// TODO Auto-generated method stub
		super.onClicked(id);
		switch (id) {
		case R.id.leftBtn:
			//（1）判断是否联网
			if(Utils.isNetworkValidate(context)){
				//a.获取数据
				
				priceEngine.fetchPrice();
				//b.删除本地数据库
				//c.save 数据
				//d.显示页面
                				
			} else {
				//a.显示本地数据
			}
			break;
		case R.id.rightBtn:
			showAlert("222");
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> ListView, View itemView, int postion, long id) {
		
		
	}
	
	

}
