package com.lovepig.manager;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.view.MainView;

public class MainManager extends BaseManager {
    public final static int MSG_WHAT_HAVE_NEW_INFO = 0;
    MainView mainDC;
    public MainManager(BaseActivity c) {
        super(c);
        mainDC = new MainView(context, R.layout.main, this);
    }

    @Override
    public void handleMessage(Message msg) {
        if(mainDC!=null && msg.what == MSG_WHAT_HAVE_NEW_INFO){
            mainDC.setTipVisibility(msg.arg1);
        }
    }

    @Override
    public void onClicked(int id) {
        switch (id) {
        case R.id.menu_news:
        case R.id.newsMsg: 	
            if(!(Application.application.currentManager instanceof OnlineNewsManager)){
                Application.application.setMainManager(Application.onlineNewsManager);
        		Application.onlineNewsManager.initData();
        		mainDC.viewReset(R.id.menu_news);
            }
           
            break;
        case R.id.menu_boar://种猪Mall
        	  if(!(Application.application.currentManager instanceof BoarMallManager)){
                  Application.application.setMainManager(Application.boarMallManager);
                  Application.boarMallManager.initData();
                  mainDC.viewReset(R.id.menu_boar);
              }
            break;
        case R.id.menu_pig_factory://附近猪场
        	 if(!(Application.application.currentManager instanceof PigFactoryManager)){
                 Application.application.setMainManager(Application.pigFactoryManager);
                 Application.pigFactoryManager.initData();
                 mainDC.viewReset(R.id.menu_pig_factory);
             }
        	break;
        case R.id.deviceMsg://设备
          
            break;
        case R.id.menu_user_info:
        	 if(!(Application.application.currentManager instanceof UserInfoManager)){
                 Application.application.setMainManager(Application.userInfoManager);
                 mainDC.viewReset(R.id.menu_user_info);
             }
        break;

        default:
            break;
        }
    }

    /**
     * 返回主界面布局
     */
    public View getLayout() {
        return mainDC;
    }
    public RelativeLayout getContainer(){
        return (RelativeLayout) mainDC.findViewById(R.id.content_container);
    }

    public void setBottomLayoutVisibility(int visible) {
        mainDC.setMenuLayoutVisibility(visible);
        
    }
    public boolean getMenuVisiblity(){
        return mainDC.getMenuVisiblity();
    }

}
