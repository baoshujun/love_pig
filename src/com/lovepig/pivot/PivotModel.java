package com.lovepig.pivot;

import com.lovepig.utils.Json;


public class PivotModel{
	
	/**
	 * 消息来源
	 */
	public String from;
	
	/**
	 * 消息类型
	 */
	public String action;
	/**
	 * 附带数据
	 */
	public String extra;
	/**
	 * 回调函数
	 */
	public BaseInvoke invoke;
	
	public String toJsonString(){
		String rs=null;
		try {
			Json json=new Json();
			json.put("from", from);
			json.put("action", action);
			json.put("extra", extra);
			rs=json.toNormalString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
}
