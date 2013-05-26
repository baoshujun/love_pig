package com.lovepig.view;

import android.content.Context;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0
 * 创建时间：May 7, 2013 11:49:10 PM
 * 
 */
public class BoarDetailDC extends BaseView {
	private TextView categoryName;

	public BoarDetailDC(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		categoryName = (TextView)findViewById(R.id.title);
		categoryName.setText("产品详情");
		
	}

}
