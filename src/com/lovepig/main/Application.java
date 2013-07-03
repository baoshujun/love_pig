package com.lovepig.main;

import java.io.File;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.lovepig.engine.PushNewsService;
import com.lovepig.manager.AboutManager;
import com.lovepig.manager.BoarMallManager;
import com.lovepig.manager.BoarManager;
import com.lovepig.manager.CheckUserManager;
import com.lovepig.manager.FoodstuffManager;
import com.lovepig.manager.MainManager;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.manager.PigFactoryManager;
import com.lovepig.manager.UserInfoManager;
import com.lovepig.manager.UserManager;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.utils.ConfigInfo;
import com.lovepig.utils.LogInfo;

public class Application extends BaseActivity  implements ServiceConnection,OnClickListener{
	public static Application application;
	public static OnlineNewsManager onlineNewsManager;
	public static FoodstuffManager pigManager;
	public static PigFactoryManager pigFactoryManager;
	public static BoarManager boarManager;
	public static UserManager userManager;
	public static UserInfoManager userInfoManager;
	public static CheckUserManager checkUserManager;
	public static AboutManager aboutManager;
	public static BoarMallManager boarMallManager;
	public PushNewsService service;

	long timeForAnimator;
	public MainManager mainManager;
	private ImageView initImage;
	private boolean isEnterNews;
	private InitDataTask initTask;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int loading=getIntent().getIntExtra("loading", 0);
		//加载启动页面
		if (loading==0) {
			setContentView(R.layout.init);
		}
		
		initImage=(ImageView)findViewById(R.id.initImage);
		LogInfo.LogOut("11","initImage..................");
		if (initImage!=null) {
			initImage.setOnClickListener(this);
			LogInfo.LogOut("11","onclick..................");
		}
		application = this;
		// int tabID = getIntent().getIntExtra("tabID", -1);
		// Configs.initTypeAndVsersion(application);
		mainManager = new MainManager(application);
		onlineNewsManager = new OnlineNewsManager(application);
		userInfoManager = new UserInfoManager(application);
		userManager = new UserManager(application);
		aboutManager = new AboutManager(application);
		pigManager = new FoodstuffManager(application);
		// 加载价格
		pigFactoryManager = new PigFactoryManager(application);
		// 加载兽药
		boarManager = new BoarManager(application);
		// 加载种猪Mall
		boarMallManager = new BoarMallManager(application);
		if (loading==0) {
			new InitDataTask().execute();
		}else {
			enterNews();
		}
		// 获得用户名
		initTask=new InitDataTask();
		initTask.execute();
		// 开始轮训
		Intent mIntent = new Intent(this, PushNewsService.class);
		startService(mIntent);
		bindService(mIntent, this, Service.BIND_AUTO_CREATE);
	}

	class InitDataTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			try {
				// 创建文件目录，现在主要用于放数据库文件
				File file = new File(Configs.lovePigPath);
				if (!file.isDirectory()) {
					if (file.exists()) {
						file.delete();
					}
					file.mkdirs();
				}
				// 检查版本是否更新
				
				SystemClock.sleep(500);
            
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (isEnterNews) {
				isEnterNews=false;
				return;
			}
			enterNews();
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					while (true) {
//						onlineNewsManager.sendEmptyMessage(1000);
//						SystemClock.sleep(50000);
//					}
//				}
//			}).start();

			onlineNewsManager.updateVersion();
			
		}

		

	}
	public void enterNews() {
		Application.this.getWindow().clearFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(mainManager.getLayout());
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.commtitle);
		dcEngineContener = mainManager.getContainer();
		currentManager = mainManager;
		mainManager.onClicked(R.id.menu_news);
		Configs.userid = ConfigInfo.getUserInfo()[0];
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (currentManager instanceof OnlineNewsManager) {// 隐藏菜单
//				if (((OnlineNewsManager) currentManager).isHiddenMenu()) {
//					return true;
//				}
//			}
			if (currentManager != null && !currentManager.backOnKeyDown()) {
				if (managerStack.size() > 0) {
					managerStack.pop();
				}
				if (managerStack.size() > 0) {
					if (managerStack.size() > 1) {// 二级或者三级四级manger
						setSubManager(managerStack.pop());
					} else {
						setMainManager(managerStack.pop());
					}
				} else {
					finish();
				}
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onServiceConnected(ComponentName name, IBinder binder) {
		PushNewsService.LocalBinder mBinder = (PushNewsService.LocalBinder)binder;
         service = mBinder.getService();
	}

	@Override
	public void onServiceDisconnected(ComponentName name) {
		// TODO Auto-generated method stu
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	
		try {
			unbindService(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		LogInfo.LogOut("11","onclick..................");
		isEnterNews=true;
		enterNews();
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				while (true) {
//					onlineNewsManager.sendEmptyMessage(1000);
//					SystemClock.sleep(5000);
//				}
//			}
//		}).start();

		onlineNewsManager.updateVersion();
		
	}
}