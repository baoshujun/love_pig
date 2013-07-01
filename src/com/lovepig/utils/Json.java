package com.lovepig.utils;

import org.json.lovepig.JSONArray;
import org.json.lovepig.JSONObject;

public class Json {
	JSONObject root=null;
	/**
	 * 构建带默认参数的json
	 * @param apiVersion
	 */
	public Json(int apiVersion){
		try {
			
			root=new JSONObject();
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 构建指定json
	 * @param jsonString
	 */
	public Json(String jsonString){
		try {
			root=new JSONObject(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析json时,构建指定json
	 * @param jsonObject
	 */
	public Json(JSONObject jsonObject){
		root=jsonObject;
	}
	
	/**
	 * 构建无数据的json
	 */
	public Json(){
		root=new JSONObject();
	}
	public boolean put(String key,int value){
		try {
			root.put(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean put(String key,Object value){
		try {
			root.put(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public String toString() {
		if(root==null){
			return null;
		}
		return root.toString();
	};

	public String toNormalString(){
		if(root==null){
			return null;
		}
		return root.toString();
	}
	public String getString(String key){
		try {
			return root.get(key).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public int getInt(String key){
		try {
			return root.getInt(key);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public boolean getBoolean(String key){
		try {
			return root.getBoolean(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public Json getJson(String key){
		try {
			return new Json(root.getJSONObject(key));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Json[] getJsonArray(String key){
		Json[] jsons;
		try {
			JSONArray a = root.getJSONArray(key);
			jsons=new Json[a.length()];
			for(int i=0;i<jsons.length;i++){
				jsons[i]=new Json(a.getJSONObject(i));
			}
			return jsons;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 判断此json是否包含key
	 * @param key
	 * @return
	 */
	public boolean has(String key){
	    try {
	        if(root == null){
	            return false;
	        }else{
	            return root.has(key);
	        }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	    
	}
}
