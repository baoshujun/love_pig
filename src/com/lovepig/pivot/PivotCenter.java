package com.lovepig.pivot;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.widget.Toast;

import com.lovepig.utils.Json;
/**
 * 各种模块间交互请求都定义在此(无论本程序可否处理)
 */
public class PivotCenter {
	
	public static final String ACTION = "ACTION";
	public static final String FROM = "FROM";
	public static final String TODO = "TODO";
	
	public static final String action_Down = "action_Down";
	public static final String action_Play = "action_Play";
	public static final String action_Local = "action_Local";
	
	public static final String from_Main = "from_main";

	public static String getTopActivityName(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> list = am.getRunningTasks(1);
		return list == null || list.size() == 0 ? null : list.get(0).topActivity.getClassName();
	}

	public static boolean isInstall(Context context, String pkgName) {
		List<ApplicationInfo> pkgs = context.getPackageManager().getInstalledApplications(0);
		for (ApplicationInfo info : pkgs) {
			if (info.packageName.equals(pkgName)) {
				return true;
			}
		}
		return false;
	}

	public static void doAction(Activity context, PivotModel model) {
		if(model.action==null){
			
		}else if(model.action.equals(action_Down)){
			/*Intent intent=new Intent(context,DownActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			context.startActivity(intent);
			context.overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);*/
		}else if(model.action.equals(action_Play)){
			if(isInstall(context, "com.audiocn.rdplayer")){
				Intent intent=new Intent();
				intent.setClassName("com.audiocn.rdplayer", "com.audiocn.rdplayer.LocalPalyerActivity");
				intent.putExtra("appkey", "319a1930-91d7-43f2-99ec-7825c60d8f34");
				intent.putExtra("extra", model.extra);
				intent.setAction("android.intent.action.VIEW");
				context.startActivity(intent);
			}else{
				Toast.makeText(context, "您没有安装播放模块", Toast.LENGTH_SHORT).show();
			}
		}else if(model.action.equals(action_Local)){
			Json extra=new Json(model.extra);
			Json extras[]=extra.getJsonArray("rs");
			String[] list=new String[extras.length];
			for(int i=0;i<list.length;i++){
				list[i]=extras[i].getString("name");
			}
			/*Intent intent=new Intent(context,LocalActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
			intent.putExtra("list", list);
			context.startActivity(intent);
			context.overridePendingTransition(R.anim.slideinright, R.anim.slideoutleft);*/
		}
		
	}
}
