package com.lovepig.pivot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lovepig.engine.PushNewsService;
/**
 * 开机启动服务，主要用于轮训
 * @author shujun62
 *
 */
public class BootBroadcastReceiver extends BroadcastReceiver {  
    //重写onReceive方法  
    @Override  
    public void onReceive(Context context, Intent intent) {  
    	  Intent mIntent=new Intent(context,PushNewsService.class);
    	  context.startService(mIntent);    
           
    }  

}
