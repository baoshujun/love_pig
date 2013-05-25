package com.lovepig.pivot;

import android.os.AsyncTask;

import com.lovepig.main.Configs;
import com.lovepig.utils.HttpUtils;
import com.lovepig.utils.LogInfo;

public class HttpEngine {
    public BaseManager manager;
    HttpRequest httpRequest = null;
    private HttpEngineListener listener;

    public interface HttpEngineListener {
        /**
         * 在http线程内请求之前UI线程调用，一般写弹出提示代码
         */
        public void onPreHttp();

        /**
         * 在http线程内请求之后子线程调用，一般写解析代码,如果结果正确，返回ok,错误返回错误信息。
         */
        public String onParseHttp(String response);

        /**
         * 在http线程内请求之后UI线程调用，一般写取消提示代码
         */
        public void onPostHttp(String result);
    }

    public HttpEngine(BaseManager manager) {
        this.manager = manager;
    }

    public void setListener(HttpEngineListener listener) {
        this.listener = listener;
    }

    /**
     * 发起http线程内请求，默认30秒超时，最多主服务器和备用服务器各重试请求两次
     * 
     * @param url
     */
    public void httpRequestNewThread(int server, String params) {
        LogInfo.LogOut("-------params = " + params);
        httpRequest = new HttpRequest();
        httpRequest.execute(String.valueOf(server), params);
    }

    class HttpRequest extends AsyncTask<String, Void, String> {
        boolean isStop = false;

        @Override
        protected void onPreExecute() {
            listener.onPreHttp();
        }

        @Override
        protected String doInBackground(String... params) {
            String rString = null;
            int server = Integer.parseInt(params[0]);
            if (isStop) {
                return "ok";
            }
            rString = httpRequestThisThread(server, params[1],false);
            if (isStop) {
                return "ok";
            }
            if (rString != null) {
                try {
                    return listener.onParseHttp(rString);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogInfo.LogOut("json:解析出错！");
                    return "json:解析出错！";
                }

            } else {
                return "网络连接超时！";
            }

        }

        public void stop() {
            isStop = true;
            cancel(true);
        }

        @Override
        protected void onPostExecute(String result) {
            if (!isStop) {
                listener.onPostHttp(result);
            }
        }
    }

    /**
     * server 为Configs.HostName中server号 params 为构造好的参数
     * 默认30秒超时，最多主服务器和备用服务器各重试请求两次
     */
    public String httpRequestThisThread(int server, String params,boolean isPost) {
        String rString = null;
        rString = HttpUtils.getServerString(manager.context.getApplicationContext(), server, params,isPost);
        return rString;
    }

    /**
     * 取消http线程内请求
     */
    public void cancelRequest() {
        if (httpRequest != null) {
            httpRequest.stop();
        }
    }

    public boolean moreBtnFlag = false;

    public boolean getMoreBtnFlag() {
        return moreBtnFlag;
    }

    public void setMoreBtnFlag(boolean flag) {
        moreBtnFlag = flag;
    }

}
