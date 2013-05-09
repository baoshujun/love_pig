package com.lovepig.engine;

import java.util.ArrayList;

import com.lovepig.manager.BoarManager;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarCateModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 8, 2013 9:55:48 PM
 * 
 */
public class BoarEngine extends BaseEngine {
	private BoarManager boarManager;

	public BoarEngine(BaseManager manager) {
		super(manager);
		boarManager = (BoarManager) manager;
	}

	/**
	 * 获取分类信息
	 */
	public void fetchCategorization() {
		ArrayList<BoarCateModel> datas = new ArrayList<BoarCateModel>();
		for (int i = 0; i < 5; i++) {
			BoarCateModel m = new BoarCateModel();
			m.boarName = "泻立停";
			m.brandInfo = "品牌信息";
			m.categorization = "第" + i + "分类";
			m.categorizationId = i;
			m.imgUrl = "1111";
			m.info = "哈哈哈哈哈哈哈";
			m.star = i;
			datas.add(m);
		}
		boarManager.sendMessage(boarManager.obtainMessage(1, datas));
	}

	/**
	 * 获取品牌信息
	 */
	public void fetchBrand() {
		ArrayList<BoarBrandModel> datas = new ArrayList<BoarBrandModel>();
		for (int i = 0; i < 5; i++) {
			BoarBrandModel m = new BoarBrandModel();
			m.brandName = "长白母猪";
			datas.add(m);
		}
		boarManager.sendMessage(boarManager.obtainMessage(2, datas));
	}

	/**
	 * 获取区域信息
	 */
	public void fetchArea() {
		ArrayList<BoarAreaModel> datas = new ArrayList<BoarAreaModel>();
		for (int i = 0; i < 5; i++) {
			BoarAreaModel m = new BoarAreaModel();
			m.boarName = "长白母猪";
			m.brandInfo = "品牌信息";
			m.area = "第" + i + "国家";
			m.categorizationId = i;
			m.imgUrl = "1111";
			m.info = "哈哈哈哈哈哈哈";
			m.star = i;
			datas.add(m);
		}
		boarManager.sendMessage(boarManager.obtainMessage(3, datas));
	}

}
