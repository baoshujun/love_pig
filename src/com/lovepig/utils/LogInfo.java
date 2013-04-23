package com.lovepig.utils;

import android.util.Log;

import com.lovepig.main.Configs;

/**
 * 这里记录调试日志
 * 
 * @author Li Hongjun
 */
public class LogInfo {
	public static void LogOut(String info) {

		if (Configs.isDebug && info != null) {
			StackTraceElement stack[] = (new Throwable()).getStackTrace();  
		    if(stack.length > 1){
		        StackTraceElement s = stack[1];  
		        String[] names= s.getClassName().split("\\.");
		        Log.d(names[names.length-1]+":"+s.getLineNumber(), info);
		    }else{
		        Log.d("XHPM", info);
		    }
		}
	}
	public static void LogOut(String tag,String info) {
        if (Configs.isDebug && info != null) {
            Log.d(tag, info);
        }
    }
//	public static void logToFile(String info) {
//		try {
//			String aLogFile = Utils.getSdcardPath()+"/TL_LI.log";
//			File iFile = new File(aLogFile);
//			if (!iFile.exists()) {
//				iFile.createNewFile();
//			}
//			FileWriter aFileStream = new FileWriter(iFile, true);
//			BufferedWriter aWriter = new BufferedWriter(aFileStream);
//			aWriter.write("["+Utils.returnNowTime()+"]"+info);
//			aWriter.write("\r\n");
//			aWriter.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
