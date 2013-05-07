package com.lovepig.engine;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.lovepig.manager.PriceManager;
import com.lovepig.model.PriceModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 5:01:01 PM
 * 
 */
public class PriceEngine extends BaseEngine {
	private static String GET_URL_PRICE_LIST = "price/list?provId=2&cityId=3&countiesId=65";
	private GetPriceTask getPriceTask;
	private boolean isStop;
	private PriceManager priceManager;

	public PriceEngine(BaseManager manager) {
		super(manager);
		if(priceManager != null){
			priceManager = (PriceManager) manager;	
		}
	}

	/**
	 * 
	 * @param id
	 * @param type
	 */
	public void fetchPrice() {
		getPriceTask = new GetPriceTask();
		getPriceTask.execute();
	}

	/**
	 * 获取价格
	 * 
	 * @author greenboy
	 * 
	 */
	class GetPriceTask extends AsyncTask<String, Void, Void> {
		boolean isStop;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			priceManager.showLoading();
		}

		@Override
		protected Void doInBackground(String... params) {
			String result = httpRequestThisThread(1, GET_URL_PRICE_LIST);
			if (isStop) {
				return null;
			} else {
				Json json = new Json(result);
				if (json.getString("status").equals("1")) {
					Json[] arrays = json.getJsonArray("news");

					ArrayList<PriceModel> datas = new ArrayList<PriceModel>();
					for (int i = 0; i < arrays.length; i++) {
						PriceModel m = new PriceModel();
						m.id = arrays[i].getString("id");
						m.price = arrays[i].getString("price");
						m.units = arrays[i].getString("units");
						m.type = arrays[i].getString("type");
						m.categoiresId = arrays[i].getInt("categoiresId");
						m.tel = arrays[i].getString("tel");
						m.mobile = arrays[i].getString("mobile");
						m.credibility = arrays[i].getString("credibility");
						m.provId = arrays[i].getInt("provId");
						m.provName = arrays[i].getString("provName");
						m.cityName = arrays[i].getString("cityName");
						m.cityId = arrays[i].getInt("cityId");
						m.updateTime = arrays[i].getString("updateTime");
						m.cityId = arrays[i].getInt("cityId");
						m.createTime = arrays[i].getString("createTime");
						m.userId = arrays[i].getString("userId");
						m.countiesName = arrays[i].getString("countiesName");
						m.contiresId = arrays[i].getInt("contiresId");
						datas.add(m);
					}
					priceManager.sendMessage(priceManager.obtainMessage(1, datas));

					LogInfo.LogOut("result:" + result);

				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			priceManager.dismissLoading();
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

}
