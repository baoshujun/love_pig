package com.lovepig.utils;

import android.util.Log;

import com.lovepig.main.Configs;

/**
 * 这里记录调试日志
 * 
 * 
 */
public class LogInfo {
	public static void LogOut(String info) {

		if (Configs.isDebug && info != null) {
			StackTraceElement stack[] = (new Throwable()).getStackTrace();  
		    if(stack.length > 1){
		        StackTraceElement s = stack[1];  
		        String[] names= s.getClassName().split("\\.");
		        Log.d("love",names[names.length-1]+":"+s.getLineNumber()+ info);
		    }else{
		        Log.d("love", info);
		    }
		}
	}
	public static void LogOut(String tag,String info) {
        if (Configs.isDebug && info != null) {
            Log.d(tag, info);
        }
    }

}
