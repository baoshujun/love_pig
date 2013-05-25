package com.lovepig.manager;

import java.util.ArrayList;

import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ViewAnimator;

import com.lovepig.engine.DeviceEngine;
import com.lovepig.main.R;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.PigFactoryView;

/**
 * 
 * 
 */
public class DeviceManager extends BaseManager implements OnItemClickListener {
    private PigFactoryView priceDC;

    private DeviceEngine devicesEngine;
    private ArrayList<PriceModel> datas;

    public DeviceManager(BaseActivity c) {
        super(c);
        devicesEngine = new DeviceEngine(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case 1:
            datas = (ArrayList<PriceModel>) msg.obj;
//            priceDC.setListViewAdapter(datas);
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_ALL:
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_BY_CAT:
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_BY_PRICE:
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_BY_MANU:
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_BY_BRAND:
            break;
        case DeviceEngine.MSG_WHAT_SUCCESS_LIST_BY_NAME:
            break;
        default:
            break;
        }
    }

    @Override
    public ViewAnimator getMainDC() {
        priceDC = new PigFactoryView(context, R.layout.pig_factory, this);
        dcEngine.setMainDC(priceDC);
        return super.getMainDC();
    }

    @Override
    public void onClicked(int id) {
        // TODO Auto-generated method stub
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
