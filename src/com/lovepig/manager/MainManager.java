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
        case R.id.menu_boar://种猪
        case R.id.priceMsg:
        	  if(!(Application.application.currentManager instanceof PriceManager)){
                  Application.application.setMainManager(Application.priceManager);
                  Application.priceManager.initData();
                  mainDC.viewReset(R.id.menu_boar);
              }
            break;
        case R.id.menu_pig_factory://附近猪场
        	break;
        case R.id.deviceMsg://设备
          
            break;

//        case R.id.menu_veterinary_drugs:
//        case R.id.boarMsg: //兽药 
//        	 if(!(Application.application.currentManager instanceof BoarManager)){
//                 Application.application.setMainManager(Application.boarManager);
//                 Application.boarManager.initData();
//             }
//            break;
//        case R.id.menu_answer_questions: 
//        case R.id.expertMsg://答疑
//          
//            break;
//        case R.id.menu_foodstuff: //食料
//            
//            break;

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
