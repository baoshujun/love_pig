package com.lovepig.main;

import java.io.File;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import com.lovepig.manager.AboutManager;
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

public class Application extends BaseActivity {
	public static Application application;
	public static OnlineNewsManager onlineNewsManager;
	public static FoodstuffManager pigManager;
	public static PigFactoryManager pigFactoryManager;
	public static BoarManager boarManager;
	public static UserManager userManager;
	public static UserInfoManager userInfoManager;
	public static CheckUserManager checkUserManager;
	public static AboutManager aboutManager;

	long timeForAnimator;
	public MainManager mainManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,//全屏
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.init);
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
		
		// 获得用户名
		new InitDataTask().execute();
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
                SystemClock.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
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

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (currentManager instanceof OnlineNewsManager) {// 隐藏菜单
				if (((OnlineNewsManager) currentManager).isHiddenMenu()) {
					return true;
				}
			}
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

}