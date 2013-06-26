package com.lovepig.engine;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.util.Log;

import com.lovepig.manager.BoarMallManager;
import com.lovepig.model.BoarAreaModel;
import com.lovepig.model.BoarBrandModel;
import com.lovepig.model.BoarCateModel;
import com.lovepig.model.BoarMallModel;
import com.lovepig.model.BoarPigFactoryDetailModel;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Json;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 8, 2013 9:55:48 PM
 * 
 */
public class BoarMallEngine extends BaseEngine {
	private BoarMallManager boarManager;
	private static String GET_BOAR_MALL_INFO = "ad/getAds?";
	private static String GET_PROVINCE_PIG_FACTORY = "pig/listHoggeryByLocal?";
	private static String GET_PIG_FACTORY_DETAIL = "pig/getHoggeryInfo?";

	public BoarMallEngine(BaseManager manager) {
		super(manager);
		boarManager = (BoarMallManager) manager;
	}

	/**
	 * 获取广告信息
	 */
	public void fetchBoarMallInfo() {
		FetchBoarMallInfoTask task = new FetchBoarMallInfoTask();
		task.execute();
	}
	
	class FetchBoarMallInfoTask extends AsyncTask<String, String, BoarMallStatus>{

		@Override
		protected BoarMallStatus doInBackground(String... params) {
			// TODO Auto-generated method stub
			String result = httpRequestThisThread(1, GET_BOAR_MALL_INFO, false);
//			Log.d("LKP", "result--->" + result);
			return parseHttpBoarMall(result, 1);
		}
		
		@Override
		protected void onPostExecute(BoarMallStatus result) {
			super.onPostExecute(result);
			if (result != null) {
				if (result.code.equals("hasData")) {
					boarManager.sendMessage(boarManager.obtainMessage(2, result.boarMallList));
				} else if (result.code.equals("neterror")) {
					// 网络错误
//					manager.ShowNewsError("网络不可用,请检查您的网络！");
				} else {
					// 服务器正常返回但没内容
//					manager.ShowNewsError(result.code);
//					manager.SetMoreBtn(false);
				}

			}
		}
		
	}
	
	class BoarMallStatus {
		public ArrayList<BoarMallModel> boarMallList = new ArrayList<BoarMallModel>();// 新闻数据
		public String code;// hasnews 表示有新闻 neterror 表示网络异常
	}
	
	private BoarMallStatus parseHttpBoarMall(String result, int flag) {
		BoarMallStatus boarMallStatus = new BoarMallStatus();
		if (result != null) {
			Json json = new Json(result);
			if (json.getString("status").equals("1")) {
				Json[] allAds = json.getJsonArray("ads");
				BoarMallModel big = new BoarMallModel();
				BoarMallModel middle = new BoarMallModel();
				ArrayList<BoarMallModel> smallList = new ArrayList<BoarMallModel>();
				int L = allAds.length;
				if (L > 0) {
					boarMallStatus.code = "hasData";
					int j = 0;
					BoarMallModel tmp = new BoarMallModel();
					for (int i = 0; i < allAds.length; i++) {
						//大图
						if(allAds[i].getInt("type") == 1){
							big.bigImgId = allAds[i].getString("pfId");
							big.type = "1";
							big.bigImg = allAds[i].getString("url");
						}else if(allAds[i].getInt("type") == 3){//中图
							middle.middleImgId = allAds[i].getString("pfId");
							middle.type = "2";
							middle.middleImg = allAds[i].getString("url");
						} else if(allAds[i].getInt("type") == 2){//小图
							j++;
							switch (j) {
							case 1:
								tmp.smallImgId01 = allAds[i].getString("pfId");
								tmp.type = "3";
								tmp.smallImg01 = allAds[i].getString("url");
								break;
							case 2:
								tmp.smallImgId02 = allAds[i].getString("pfId");
								tmp.type = "3";
								tmp.smallImg02 = allAds[i].getString("url");
								break;
							case 3:
								tmp.smallImgId03 = allAds[i].getString("pfId");
								tmp.type = "3";
								tmp.smallImg03 = allAds[i].getString("url");
								break;
							case 4:
								tmp.smallImgId04 = allAds[i].getString("pfId");
								tmp.type = "3";
								tmp.smallImg04 = allAds[i].getString("url");
								smallList.add(tmp);
								j = 0;
								tmp = new BoarMallModel();
								break;
							default:
								break;
							}
						}
					}
					
				}
				//开始组装数据
				ArrayList<BoarMallModel> arrList = new ArrayList<BoarMallModel>();
				arrList.add(big);
//				Log.d("LKP", arrList.size()+"");
				for(int i = 0;i < 3 ; i++){
					arrList.add(smallList.get(i));
				}
				arrList.add(middle);
				if(smallList.size() > 4){
					arrList.addAll(3, smallList);
				}
				boarMallStatus.boarMallList = arrList;
			} else {
				boarMallStatus.code = json.getString("msg");// 服务器正常返回，但没数据
			}
		} else {
			boarMallStatus.code = "neterror";// 网络异常
		}
		return boarMallStatus;
	}
	
	
	public void fetchProvincePigFactory(String provinceId){
		ArrayList<PigFactoryModel> datas = new ArrayList<PigFactoryModel>();
		StringBuffer sb = new StringBuffer();
		sb.append(GET_PROVINCE_PIG_FACTORY).append("provId=").append(provinceId)
				.append("&cityId=").append("0").append("&countiesId=")
				.append("0").append("&limit=").append("10").append("&maxId=300");
		FetchProvincePigFactoryTask task = new FetchProvincePigFactoryTask();
		task.execute(sb.toString());
	}
	
	class FetchProvincePigFactoryTask extends AsyncTask<String, String, Boolean>{
		ArrayList<PigFactoryModel> pfmList = new ArrayList<PigFactoryModel>();
		@Override
		protected Boolean doInBackground(String... params) {
			String result = httpRequestThisThread(1, params[0], false);
			if(result != null){
				Json json = new Json(result);
				PigFactoryModel pfm = new PigFactoryModel();
				if(json.getString("status").equals("1")){
					Json[] arr = json.getJsonArray("hoggeries");
					for(int i=0; i < arr.length; i++){
						pfm.id = arr[i].getInt("id");
						pfm.pigFactoryName = arr[i].getString("hoggeryName");
						pfm.pigFactoryDesc = arr[i].getString("description");
						pfm.credibility = arr[i].getString("credibility");
//						pfm.img = arr[i].getString("imgUrl");
						pfm.img = "http://955.cc/eNbS";
						pfm.scale = arr[i].getString("scale");
						String[] temp = arr[i].getString("species").split(",");
						Log.d("LKP", "size:" +temp.length);
						pfm.species = arr[i].getString("species").replace("[", "").replace("]", "");
						Log.d("LKP", "array="+pfm.species);
//						pfm.pigFactoryBrandInfo = arr[i].getString("remark");
						pfm.product = arr[i].getString("remark");
						pfm.recommendNum = (float)arr[i].getInt("credibility");
						pfmList.add(pfm);
					}
					//保存数据
					boarManager.pigFactoryDBEngine.save(pfmList);
				} else {
					return false;
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				boarManager.sendMessage(boarManager.obtainMessage(5, pfmList));
			}
		}
		
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
	/**
	 * 
	 * @param id 猪场的ID
	 */
	public void fetchPigFactoryDetailData(String id){
		StringBuffer sb = new StringBuffer();
		sb.append(GET_PIG_FACTORY_DETAIL).append("hoggeryId=").append(id);
		GetPigFactoryDetail getPigFactoryDetail = new GetPigFactoryDetail();
		getPigFactoryDetail.execute(sb.toString());
	}
	
	private class GetPigFactoryDetail extends AsyncTask<String, Void, Boolean> {
		BoarPigFactoryDetailModel pfm = new BoarPigFactoryDetailModel();
		@Override
		protected Boolean doInBackground(String... params) {
			// 联网获取猪场的主营产品
			ArrayList<BoarCateModel> datas = new ArrayList<BoarCateModel>();
			String result = httpRequestThisThread(1, params[0], false);
			if(result != null){
				Json json = new Json(result);
				if(json.getString("status").equals("1")){
//					pfm.id = 
					pfm.pigFactoryName = json.getString("hoggeryName");
					pfm.img = json.getString("imgUrl");
					pfm.pigFactoryDesc = json.getString("description");
					pfm.product = json.getString("remark");
					pfm.pigImgs = json.getString("pigImgs");
					pfm.scale = json.getString("scale");
					pfm.contact = json.getString("contact");
					pfm.mobile = json.getString("mobile");
					pfm.tel = json.getString("tel");
					pfm.site = json.getString("site");
					pfm.email = json.getString("email");
					pfm.address = json.getString("address");
				} else {
					return false;
				}
			}
			return true;
		}
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if(result){
				boarManager.sendMessage(boarManager.obtainMessage(BoarMallManager.SET_PIG_FACTORY_DETAIL_VIEW,
						pfm));
			} else {
				
			}
		}
		
	}

}
