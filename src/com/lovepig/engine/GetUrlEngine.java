package com.lovepig.engine;

import java.io.File;

import android.os.AsyncTask;
import android.os.Handler;

import com.lovepig.main.Application;
import com.lovepig.main.Configs;
import com.lovepig.utils.HttpUtils;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;


public class GetUrlEngine {
	Handler handler;
	public GetUrlEngine(Handler h){
		handler=h;
	}
	getUrlTask urlTask;
	public void getUrl(String id,int type) {
		try {
			if(urlTask!=null && !urlTask.isFinished()){
				urlTask.cancel(true);
			}
			urlTask=new getUrlTask();
			urlTask.execute(id,String.valueOf(type));
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.LogOut("getUrlException:" + e.getMessage());
			handler.sendEmptyMessage(23);
		}
	}

	public void cacel(){
		if(urlTask!=null && !urlTask.isFinished()){
			urlTask.cancel(true);
		}
	}

	class getUrlTask extends AsyncTask<String, String, String> {
		int resultType=0;//0Ϊ���粻ͨ,1Ϊ����,2Ϊ�ɹ����url,3Ϊ����lrcurl
		String auido[],lrc;
		String key;
		@Override
		protected String doInBackground(String... params) {
			key= params[0]+"#"+ params[1];
//			if (!Configs.isCheckin && Application.userManager!=null) {
//				Application.userManager.checkUser();
//				if (!Configs.isCheckin) {
//					return null;
//				} else if (Configs.isBill) {
//					return "0";
//				}
//			}
			int type=Integer.parseInt(params[1]);
			auido=new String[3];
			String rsString = null;
			Json json=new Json(0);
			json.put("id", params[0]);
			json.put("programtype",type);
			//url = Configs.HostName1[8] + "/tlcy/content/program.action" + json.toString() ;
			LogInfo.LogOut("getUrl:"+json.toNormalString());
			rsString = HttpUtils.getServerString(Application.application, 10, "/tlcy/content/program.action" + json.toString());
			if (rsString == null || rsString.equals("-1")) {// ���粻ͨ
				resultType=0;
				return null;
			}
			Json rs=new Json(rsString);
			if(rs.getInt("result")==0){
				resultType=1;
				return rs.getString("text");
			}else{
				int limit = rs.getInt("limit");
				if(limit!=0){
					return "0";
				}
				String promUrl=rs.getString("programurl");
				if(!promUrl.toLowerCase().startsWith("http://")){
					promUrl="http://"+promUrl;
				}
				auido[0]=promUrl;
				resultType=2;
				if(type==3){//mv
					promUrl=rs.getString("mv1");
					if(promUrl != null) {
						if(!promUrl.toLowerCase().startsWith("http://")){
							promUrl="http://"+promUrl;
						}
						auido[1]=promUrl;
						promUrl=rs.getString("mv2");
						if(!promUrl.toLowerCase().startsWith("http://")){
							promUrl="http://"+promUrl;
						}
						auido[2]=promUrl;
					}
					
				}
				String lrcUrl=rs.getString("lrc");
				if(lrcUrl!=null && lrcUrl.length()>5){
					if(!lrcUrl.toLowerCase().startsWith("http://")){
						lrcUrl="http://"+lrcUrl;
					}
					lrc=Configs.xhpmLrcPath+params[0]+"."+Utils.buildFileType(lrcUrl);
					if(!new File(lrc).exists()){
						rsString = HttpUtils.getServerString(lrcUrl,lrc);
						if(rsString!=null && rsString.length()>0){
							resultType=3;
						}
					}else{
						resultType=3;
					}
				}
				return promUrl;
			}
		}
		@Override
		protected void onPostExecute(String result) {
			if(result!=null && result.equals("0")){
				handler.sendEmptyMessage(100);//收费
			}else if(resultType==0){//网络错误
				handler.sendEmptyMessage(3);
			}else if(resultType==1){// 错误提示
				String[] url=new String[3];
				url[0]=result;
				url[1]=null;
				url[2]=key;
				handler.sendMessage(handler.obtainMessage(4, url));
			}else if(resultType==2){//渠道url
				String[] url=new String[5];
				url[0]=auido[0];
				url[1]=null;
				url[2]=key;
				url[3]=auido[1];
				url[4]=auido[2];
				handler.sendMessage(handler.obtainMessage(1, url));
			}else if(resultType==3){//渠道url和lrc
				String[] url=new String[5];
				url[0]=auido[0];
				url[1]=lrc;
				url[2]=key;
				url[3]=auido[1];
				url[4]=auido[2];
				handler.sendMessage(handler.obtainMessage(2, url));
			}
		}
		public boolean isFinished(){
			return getStatus()==Status.FINISHED;
		}
	}
}



