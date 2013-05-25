package com.lovepig.engine;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.lovepig.manager.DeviceManager;
import com.lovepig.model.DevicesModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

/**
 *
 * 
 */
public class DeviceEngine extends BaseEngine {
    private static String GET_URL_DEVICE_LIST_ALL = "device/listAll";
    private static String GET_URL_DEVICE_LIST_BY_CAT = "device/listByCat?catId=2";
    private static String GET_URL_DEVICE_LIST_BY_PRICE = "device/listByPrice?price=7000";
    private static String GET_URL_DEVICE_LIST_BY_MANU = "device/listByManu?manuId=366";
    private static String GET_URL_DEVICE_LIST_BY_BRAND = "device/listByBrand?brandId=5devName=";
    private static String GET_URL_DEVICE_LIST_BY_NAME = "device/listByName?devName=";

    public static final int MSG_WHAT_SUCCESS_LIST_ALL = 2;
    public static final int MSG_WHAT_SUCCESS_LIST_BY_CAT = 3;
    public static final int MSG_WHAT_SUCCESS_LIST_BY_PRICE = 4;
    public static final int MSG_WHAT_SUCCESS_LIST_BY_MANU = 5;
    public static final int MSG_WHAT_SUCCESS_LIST_BY_BRAND = 6;
    public static final int MSG_WHAT_SUCCESS_LIST_BY_NAME = 7;

    private DeviceManager deviceManager;
    private GetListByCatTask getListByCatTask;
    private GetListByPrice getListByPrice;
    private GetListByBrandTask getListByBrandTask;
    private GetListByNameTask getListByNameTask;
    private GetListAllTask getListAllTask;
    private GetListByManuTask getListByManuTask;

    public DeviceEngine(BaseManager manager) {
        super(manager);
        deviceManager = (DeviceManager) manager;
    }

    public void fetchListAll() {
        if (getListAllTask != null) {
            getListAllTask.stop();
            getListAllTask = null;
        }
        getListAllTask = new GetListAllTask();
        getListAllTask.execute();
    }

    public void fetchListByBrandTask() {
        if (getListAllTask != null) {
            getListAllTask.stop();
            getListAllTask = null;
        }
        getListAllTask = new GetListAllTask();
        getListAllTask.execute();
    }

    class GetListAllTask extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_ALL,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_ALL, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    public void fetchListByCat() {
        if (getListByCatTask != null) {
            getListByCatTask.stop();
            getListByCatTask = null;
        }
        getListByCatTask = new GetListByCatTask();
        getListByCatTask.execute();
    }

    class GetListByCatTask extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_BY_CAT,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_BY_CAT, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    public void fetchListByPrice() {
        if (getListByPrice != null) {
            getListByPrice.stop();
            getListByPrice = null;
        }
        getListByPrice = new GetListByPrice();
        getListByPrice.execute();
    }

    class GetListByPrice extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_BY_PRICE,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_BY_PRICE, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    public void fetchListByManu() {
        if (getListByManuTask != null) {
            getListByManuTask.stop();
            getListByManuTask = null;
        }
        getListByManuTask = new GetListByManuTask();
        getListByManuTask.execute();
    }

    class GetListByManuTask extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_BY_MANU,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_BY_MANU, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    public void fetchListByBrand() {
        if (getListByBrandTask != null) {
            getListByBrandTask.stop();
            getListByBrandTask = null;
        }
        getListByBrandTask = new GetListByBrandTask();
        getListByBrandTask.execute();
    }

    class GetListByBrandTask extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_BY_BRAND,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_BY_BRAND, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    public void fetchByName() {
        if (getListByNameTask != null) {
            getListByNameTask.stop();
            getListByNameTask = null;
        }
        getListByNameTask = new GetListByNameTask();
        getListByNameTask.execute();
    }

    class GetListByNameTask extends AsyncTask<String, Void, Void> {
        boolean isStop;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            deviceManager.showLoading();
        }

        @Override
        protected Void doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_URL_DEVICE_LIST_BY_NAME,false);
            if (isStop) {
                return null;
            } else {
                Json json = new Json(result);
                if (json.getString("status").equals("1")) {
                    Json[] arrays = json.getJsonArray("news");

                    ArrayList<DevicesModel> datas = new ArrayList<DevicesModel>();
                    for (int i = 0; i < arrays.length; i++) {
                        DevicesModel m = new DevicesModel();
                        datas.add(m);
                    }
                    deviceManager.sendMessage(deviceManager.obtainMessage(MSG_WHAT_SUCCESS_LIST_BY_NAME, datas));

                    LogInfo.LogOut("result:" + result);

                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            deviceManager.dismissLoading();
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

}
