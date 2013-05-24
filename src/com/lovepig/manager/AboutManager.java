package com.lovepig.manager;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0
 * 创建时间：May 19, 2013 6:06:13 PM
 * 
 */

import android.os.Message;
import android.widget.ViewAnimator;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.view.AboutView;

public class AboutManager extends BaseManager {
	AboutView mainDC;

	public AboutManager(BaseActivity c) {
		super(c);
	}

	@Override
	public void handleMessage(Message msg) {

	}

	@Override
	public void onClicked(int id) {
		switch (id) {
		case R.id.leftBtn:
			back();
			break;
		default:
			super.onClicked(id);
			break;
		}

	}

	@Override
	public ViewAnimator getMainDC() {
		if (mainDC == null) {
			mainDC = new AboutView(context, R.layout.about, this);
		}
		dcEngine.setMainDC(mainDC);
		return super.getMainDC();
	}
}
