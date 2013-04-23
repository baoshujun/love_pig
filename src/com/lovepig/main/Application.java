package com.lovepig.main;

import android.os.Bundle;
import android.view.KeyEvent;
import com.lovepig.manager.MainManager;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.pivot.BaseActivity;


public class Application extends BaseActivity {
    public static Application application;
    public static OnlineNewsManager onlineNewsManager;
    
    
    
    long timeForAnimator;
    public MainManager mainManager;
 
   

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = this;
        Configs.initTypeAndVsersion(application);
                mainManager = new MainManager(application);
        onlineNewsManager = new OnlineNewsManager(application);
        setContentView(mainManager.getLayout());
        dcEngineContener = mainManager.getContainer();
        currentManager = mainManager;
        setMainManager(onlineNewsManager);
        onlineNewsManager.initData();
        
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