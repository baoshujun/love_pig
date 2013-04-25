package com.lovepig.main;

import java.io.File;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lovepig.utils.LogInfo;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 接收广播：设备上新安装了一个应用程序包。
		if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
			String packageName = intent.getDataString().substring(8);
			LogInfo.LogOut("---------" + packageName);
			if (packageName.equals(Configs.readerpackageName)) {
				File f = new File(Configs.tlcyReaderApkPath);
				if (f.exists()) {
					f.delete();
				}
				f = null;
			}
		}
	}
}