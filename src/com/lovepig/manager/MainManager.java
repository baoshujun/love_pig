package com.lovepig.manager;

import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;

import com.lovepig.dc.MainDC;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;

public class MainManager extends BaseManager {
    public final static int MSG_WHAT_HAVE_NEW_INFO = 0;
    MainDC mainDC;
    public MainManager(BaseActivity c) {
        super(c);
        mainDC = new MainDC(context, R.layout.main, this);
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
            if(!(Application.application.currentManager instanceof OnlineNewsManager)){
                Application.application.setMainManager(Application.onlineNewsManager);
            }
           
            break;
        case R.id.menu_price://价格
            
            break;
        case R.id.menu_equipment:  //设备
           
          
            break;

        case R.id.menu_veterinary_drugs: // 兽医  
            
           
            break;
        case R.id.menu_answer_questions: //答疑
          
           
          
            break;
        case R.id.menu_foodstuff: //食料
            
            
            
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
