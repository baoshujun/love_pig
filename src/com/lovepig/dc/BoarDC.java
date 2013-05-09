package com.lovepig.dc;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.lovepig.main.R;
import com.lovepig.manager.BoarManager;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarCateModel;
import com.lovepig.pivot.BaseDC;
import com.lovepig.pivot.BaseManager;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 7, 2013 11:47:39 PM
 * 
 */
public class BoarDC extends BaseDC {
	// categorization 分类 brand 品牌 area 地区
	private Button categorization, brand, area;
	private ListView cateListview;
	private BoarCateListViewAdapter adapter;
	private BoarBrandAdapter brandAdapter;
	private BoarAreaAdapter areaAdapter;

	public BoarDC(Context context, int layoutId, BaseManager manager) {
		super(context, layoutId, manager);
		categorization = (Button) findViewById(R.id.leftBtn);
		categorization.setText("分类");
		brand = (Button) findViewById(R.id.middleBtn);
		brand.setVisibility(View.VISIBLE);
		brand.setText("品牌");
		area = (Button) findViewById(R.id.rightBtn);
		area.setText("地区");
		cateListview = (ListView) findViewById(R.id.boarLv);
		categorization.setOnClickListener(this);
		brand.setOnClickListener(this);
		area.setOnClickListener(this);
		cateListview.setOnItemClickListener((BoarManager) manager);
	}

	/**
	 * 根据分类
	 * 
	 * @param datas
	 */
	public void setCateAdapter(ArrayList<BoarCateModel> datas) {
		adapter = new BoarCateListViewAdapter(datas, context);
		cateListview.setAdapter(adapter);
	}

	/**
	 * 根据品牌
	 * 
	 * @param datas
	 */
	public void setBrandAdapter(ArrayList<BoarBrandModel> datas) {
		brandAdapter = new BoarBrandAdapter(datas, context);
		cateListview.setAdapter(brandAdapter);
	}

	/**
	 * 根据区域
	 * 
	 * @param datas
	 */
	public void setAreaAdapter(ArrayList<BoarAreaModel> datas) {
		areaAdapter = new BoarAreaAdapter(datas, context);
		cateListview.setAdapter(areaAdapter);
	}

}
