package com.lovepig.pivot;

import android.os.AsyncTask;
import android.util.Log;

import com.lovepig.main.Application;
import com.lovepig.utils.HttpUtils;

public abstract class BaseEngine {
    public BaseManager manager;
    HttpRequest httpRequest = null;

    public BaseEngine(BaseManager manager) {
        this.manager = manager;
    }

    /**
     * 发起http线程内请求，默认30秒超时，最多主服务器和备用服务器各重试请求两次
     * 
     * @param url
     */
    public void httpRequestNewThread(int server, String params) {
        httpRequest = new HttpRequest();
        httpRequest.execute(String.valueOf(server), params);
    }

    class HttpRequest extends AsyncTask<String, Void, Void> {
        boolean isStop = false;

        @Override
        protected void onPreExecute() {
            onPreHttp();
        }

        @Override
        protected Void doInBackground(String... params) {
            String rString = null;
            int server = Integer.parseInt(params[0]);
            rString = httpRequestThisThread(server, params[1],false);
            if (isStop) {
                return null;
            }
            onParseHttp(rString);
            return null;
        }

        public void stop() {
            isStop = true;
            cancel(true);
        }

        @Override
        protected void onPostExecute(Void result) {
            if (!isStop) {
                onPostHttp();
            }
        }
    }

    /**+
     * server 为Configs.HostName中server号 params 为构造好的参数
     * 默认30秒超时，最多主服务器和备用服务器各重试请求两次
     */
    public String httpRequestThisThread(int server, String params,boolean isPost) {
        String rString = null;
        Log.d("LKP", "server=" + server + "params=" + params );
        rString = HttpUtils.getServerString(Application.application.getApplicationContext(), server, params,isPost);
        return rString;
    }

    /**
     * 在http线程内请求之前UI线程调用，一般写弹出提示代码
     */
    public void onPreHttp() {
    };

    /**
     * 在http线程内请求之后子线程调用，一般写解析代码
     */
    public void onParseHttp(String response) {
    };

    /**
     * 在http线程内请求之后UI线程调用，一般写取消提示代码
     */
    public void onPostHttp() {
    };

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
    
    /**
     * userDuplicate = 100010;
    //用户名或者密码错误
     public static final Integer userPassIncorrect = 100020;
     public static final Integer codeExpire = 100030;
     public static final Integer codeAlreadyExsits = 100040;
     public static final Integer mobileInconsistent = 100050;
     * @param errorCode
     */
	public String getErrorMsg(int errorCode) {
		Log.d("LKP", "errorCode:" + errorCode);
		String str = null;
		switch (errorCode) {
		case 100010:
			str = "电话号码已经注册！";
			break;
		case 100020:
			str = "用户名或者密码错误！";
			break;
		case 100030:
			str = "注册码无效！";
			break;
		case 100040:
			str = "注册码已经发送，请稍等！";
			break;
		case 100050:
			str = "电话号码不一致！";
			break;
		default:
			break;
		}
		return str;
	}

}
