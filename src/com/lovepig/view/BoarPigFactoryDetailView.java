package com.lovepig.view;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 6:38:40 PM
 * 
 */
public class BoarPigFactoryDetailView extends BaseView implements OnClickListener {
	RelativeLayout toListView;
	ImageView toListViewiv;
	TextView categoryName;

	public BoarPigFactoryDetailView(Context context, int layoutId,BaseManager manager) {
		super(context, layoutId, manager);
		toListView = (RelativeLayout) findViewById(R.id.pigProduct);
		toListViewiv = (ImageView) findViewById(R.id.toListView);
		categoryName = (TextView)findViewById(R.id.title);
		categoryName.setText("猪场详情");
		toListView.setOnClickListener(this);
		toListView.setOnClickListener(this);
	}
	
	

}
