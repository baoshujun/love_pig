package com.lovepig.engine;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;

import com.lovepig.engine.NewsEngine.NewsState;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.utils.HttpUtils;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;

public class PushNewsService extends Service {
	private IBinder binder = new PushNewsService.LocalBinder();
	private Handler hander;
	private static final int LIMIT_PUSHNEWS=20;
	private static String PUSH_NEWS = "news/pushNews?";
	

	public void onCreate() {
		super.onCreate();
		initHandler();
	    new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (true) {
					hander.sendEmptyMessage(1);
					SystemClock.sleep(5000);
				}
			}
		}).start();
		//
	}

	 private void initHandler() {
	        hander = new Handler() {
	            @Override
	            public void handleMessage(Message msg) {
	                super.handleMessage(msg);
	               pushNews(0, 0, Utils.returnNowTime());
	            }
	        };
	    }
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_REDELIVER_INTENT;
	}

	// 定义内容类继承Binder
	public class LocalBinder extends Binder {
		// 返回本地服务
	public	PushNewsService getService() {
			return PushNewsService.this;
		}
	}
	
	/**
	 * 新闻轮询
	 * 
	 * news/pushNews (新增)新闻推送 param: catId limit maxId time
	 */
	public void pushNews(int catId,int maxId,String time){
		StringBuilder mStrBuilder = new StringBuilder("catId=");
		mStrBuilder.append(catId).append("&limit=").append(LIMIT_PUSHNEWS)
				.append("&maxId=").append(maxId).append("&time=").append(System.currentTimeMillis());
		PushNewsTask mPushNewsTask = new PushNewsTask();
		mPushNewsTask.execute(mStrBuilder.toString());
		
	}
	/**
	 * 获取最新新闻
	 * 
	 * 
	 */
	class PushNewsTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
			String result = httpRequestThisThread(1, PUSH_NEWS + params[0],
					false);
			LogInfo.LogOut("111111111", "result:"+result);
			
	     return null;
			
		}

		@Override
		protected void onPostExecute(NewsState result) {
			NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
			Notification  n = new Notification(R.drawable.ic_launcher, "爱养猪!", System.currentTimeMillis());             
			n.flags = Notification.FLAG_ONGOING_EVENT;
			
			Intent i = new Intent(PushNewsService.this, Application.class);
			i.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			
			PendingIntent contentIntent = PendingIntent.getActivity(
					PushNewsService.this, 
			        R.string.app_name, 
			        i, 
			        PendingIntent.FLAG_UPDATE_CURRENT);
			
			n.setLatestEventInfo(
					PushNewsService.this,
			        "爱养猪!", 
			        "人一样的猪，猪一样的人！", 
			        contentIntent);
			
			nm.notify(R.string.app_name, n);
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}
	 /**
     * server 为Configs.HostName中server号 params 为构造好的参数
     * 默认30秒超时，最多主服务器和备用服务器各重试请求两次
     */
    public String httpRequestThisThread(int server, String params,boolean isPost) {
        String rString = null;
        rString = HttpUtils.getServerString(getApplicationContext(), server, params,isPost);
        return rString;
    }
}
