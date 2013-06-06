package com.lovepig.engine;

import java.util.ArrayList;

import com.lovepig.manager.BoarMallManager;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarMallModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 8, 2013 9:55:48 PM
 * 
 */
public class BoarMallEngine extends BaseEngine {
	private BoarMallManager boarManager;

	public BoarMallEngine(BaseManager manager) {
		super(manager);
		boarManager = (BoarMallManager) manager;
	}

	/**
	 * 获取广告信息
	 */
	public void fetchBoarMallInfo() {
		ArrayList<BoarMallModel> datas = new ArrayList<BoarMallModel>();
		BoarMallModel m = new BoarMallModel();
		m.type = "1";
		m.bigImg = "http://i2.sinaimg.cn/ent/s/m/2013-06-05/U8551P28T3D3937030F329DT20130605221259.jpg";
		m.bigImgId="1";
		datas.add(m);
		BoarMallModel m1 = new BoarMallModel();
		m1.type = "2";
		m1.middleImg = "http://i0.sinaimg.cn/ent/s/m/2013-06-05/U8551P28T3D3937030F358DT20130605221259.jpg";
		m1.middleImgId="2";
		datas.add(m1);
		
		BoarMallModel m2 = new BoarMallModel();
		m2.type = "3";
		
		m2.smallImg01 = "http://i1.sinaimg.cn/ent/s/h/w/2013-06-03/U7357P28T3D3935300F326DT20130603233702.jpg";
		m2.smallImgId01="3";
		
		m2.smallImg02 = "http://i1.sinaimg.cn/ent/s/h/w/2013-06-03/U7357P28T3D3935300F326DT20130603233702.jpg";
		m2.smallImgId02="4";
		
		m2.smallImg03 = "http://i1.sinaimg.cn/ent/s/h/w/2013-06-03/U7357P28T3D3935300F326DT20130603233702.jpg";
		m2.smallImgId03="5";
		
		m2.smallImg04 = "http://i1.sinaimg.cn/ent/s/h/w/2013-06-03/U7357P28T3D3935300F326DT20130603233702.jpg";
		m2.smallImgId04="6";
		datas.add(m2);
		boarManager.sendMessage(boarManager.obtainMessage(2, datas));
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
