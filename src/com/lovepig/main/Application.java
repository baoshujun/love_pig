package com.lovepig.main;

import android.os.Bundle;
import android.view.KeyEvent;
import com.lovepig.manager.MainManager;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.manager.FoodstuffManager;
import com.lovepig.manager.PriceManager;
import com.lovepig.pivot.BaseActivity;

public class Application extends BaseActivity {
	public static Application application;
	public static OnlineNewsManager onlineNewsManager;
	public static FoodstuffManager pigManager;
	public static PriceManager priceManager;

	long timeForAnimator;
	public MainManager mainManager;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = this;
		int tabID = getIntent().getIntExtra("tabID", -1);
		Configs.initTypeAndVsersion(application);
		mainManager = new MainManager(application);
		onlineNewsManager = new OnlineNewsManager(application);
		pigManager = new FoodstuffManager(application);
		priceManager = new PriceManager(application);
		setContentView(mainManager.getLayout());
		dcEngineContener = mainManager.getContainer();
		currentManager = mainManager;
		mainManager.onClicked(tabID);
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}