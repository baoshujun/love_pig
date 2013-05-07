package com.lovepig.manager;

import android.os.Message;
import android.widget.ViewAnimator;

import com.lovepig.dc.FoodstuffDC;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;

public class FoodstuffManager extends BaseManager {

    private FoodstuffDC mainDC;

    public FoodstuffManager(BaseActivity c) {
        super(c);

    }

    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message msg) {

    }

    @Override
    public boolean backOnKeyDown() {

        return super.backOnKeyDown();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onLoadingCacel() {

        super.onLoadingCacel();
    }

    @Override
    public ViewAnimator getMainDC() {
        if (mainDC == null) {
            mainDC = new FoodstuffDC(context, R.layout.pig, this);
            dcEngine.setMainDC(mainDC);
        }
        return super.getMainDC();
    }

}
