package com.lovepig.engine;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.lovepig.manager.PigFactoryManager;
import com.lovepig.model.PigFactoryModel;
import com.lovepig.pivot.BaseEngine;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 5:01:01 PM
 * 
 */
public class PigFactoryEngine extends BaseEngine {
	private static String GET_URL_PRICE_LIST = "price/list?provId=2&cityId=3&countiesId=65";
	private GetPriceTask getPriceTask;
	private PigFactoryManager pigFactoryManager;
	private GetDetailTask getDetailTask;

	public PigFactoryEngine(PigFactoryManager manager) {
		super(manager);
		if (pigFactoryManager == null) {
			this.pigFactoryManager = manager;
		}
	}

	/**
	 * 
	 * @param fontSize
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
			pigFactoryManager.showLoading();
		}

		@Override
		protected Void doInBackground(String... params) {
			// String result = httpRequestThisThread(1, GET_URL_PRICE_LIST);
			// if (isStop) {
			// return null;
			// } else {
			// if (TextUtils.isEmpty(result)) {//联网失败result=null
			// pigFactoryManager.sendEmptyMessage(0);
			// return null;
			// }
			// Json json = new Json(result);
			// if (json.getString("status").equals("1")) {
			// Json[] arrays = json.getJsonArray("prices");
			//
			// ArrayList<PriceModel> datas = new ArrayList<PriceModel>();
			// for (int i = 0; i < arrays.length; i++) {
			// PriceModel m = new PriceModel();
			// m.id = arrays[i].getString("id");
			// m.price = arrays[i].getString("price");
			// m.units = arrays[i].getString("units");
			// m.type = arrays[i].getString("type");
			// m.categoiresId = arrays[i].getInt("categoiresId");
			// m.tel = arrays[i].getString("tel");
			// m.mobile = arrays[i].getString("mobile");
			// m.credibility = arrays[i].getString("credibility");
			// m.provId = arrays[i].getInt("provId");
			// m.provName = arrays[i].getString("provName");
			// m.cityName = arrays[i].getString("cityName");
			// m.cityId = arrays[i].getInt("cityId");
			// m.updateTime = arrays[i].getString("updateTime");
			// m.cityId = arrays[i].getInt("cityId");
			// m.createTime = arrays[i].getString("createTime");
			// m.userId = arrays[i].getString("userId");
			// m.countiesName = arrays[i].getString("countiesName");
			// m.contiresId = arrays[i].getInt("contiresId");
			// datas.add(m);
			// }
			ArrayList<PigFactoryModel> datas = new ArrayList<PigFactoryModel>();
			for (int i = 0; i < 10; i++) {
				PigFactoryModel m = new PigFactoryModel();
				m.id = i;
				m.pigFactoryName = "北京顺陈养殖有限公司";
				m.pigFactoryDesc = "公司占地面积200亩，存栏基础母猪1200头。现经营品种为：英系大白、美系大白、美系长白、台系杜洛克、美系杜洛克。";
				m.pigFactoryBrandInfo = "品牌信息";
				m.pigFactoryGradebarNum = 4.5f;
				datas.add(m);
			}
			pigFactoryManager.sendMessage(pigFactoryManager.obtainMessage(1,
					datas));

			// LogInfo.LogOut("result:" + result);

			// }
			// }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			super.onPostExecute(result);
			pigFactoryManager.dismissLoading();
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

	public void fetchDetail(int id) {
		getDetailTask = new GetDetailTask();
		getDetailTask.execute(id);
	}

	private class GetDetailTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			// 联网获取详情
			pigFactoryManager.sendMessage(pigFactoryManager.obtainMessage(PigFactoryManager.SETDETAILVIEW,
					null));
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

	}
}
