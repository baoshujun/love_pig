package com.lovepig.engine.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import com.lovepig.model.PigFactoryModel;
import com.lovepig.utils.LogInfo;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0
 * 创建时间：Jun 15, 2013 9:58:30 AM
 * 
 */
public class PigFactoryDBEngine extends DBEngine {
	
	/**
	 * 保存猪场信息到本地缓存
	 * @param PigFactoryModel pfm
	 */
	public void save(ArrayList<PigFactoryModel> pfm){
        String sql = "insert into pigfactory(id,name,summary,recommendNum,provinceId,imgUrl,scale,type) values(?,?,?,?,?,?,?,?)";
        db.beginTransaction();
        try {
        	PigFactoryModel pigFactoryModel;
            for (int i = 0; i < pfm.size(); i++) {
                pigFactoryModel = pfm.get(i);
                db.execSQL(sql, new String[] { String.valueOf(pigFactoryModel.id), String.valueOf(pigFactoryModel.pigFactoryName), String.valueOf(pigFactoryModel.product), String.valueOf(pigFactoryModel.recommendNum),String.valueOf(pigFactoryModel.provId) 
                ,pigFactoryModel.img,pigFactoryModel.scale,pigFactoryModel.species});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
	}
	/**
	 * 查询猪场列表
	 * @param 猪场名字 name
	 * @param 猪场规模 scale
	 * @param 猪场品类 type
	 * @return
	 */
	public ArrayList<PigFactoryModel> findPigFactory(String name,String scale,String type){
        LogInfo.LogOut("getOnlineNews.................");
        ArrayList<PigFactoryModel> programs = new ArrayList<PigFactoryModel>();
        try {
        	String sql;
        	Cursor cursor = null;
        	if(name != null && scale == null && type == null){
        		sql = "select * from pigfactory where name like '%" + name + "%' order by id desc"; 
        		cursor = db.rawQuery(sql,null);
        	} 
        	
        	if(scale != null){
        		if(name != null){
        			sql = "select * from pigfactory where name like '%" + name + "%' and scale = ? order by id desc";
        		} else {
        			sql = "select * from pigfactory where scale = ? order by id desc";
        		}
        		cursor = db.rawQuery(sql,new String[]{String.valueOf(scale)});
        	}else if(type != null){
        		if (name != null) {
        			sql = "select * from pigfactory where name like '%" + name + "%' and (',' || type || ',') LIKE '%,"+type+ ",%' order by id desc";
        		} else {
        			sql = "select * from pigfactory where  (',' || type || ',') LIKE '%,"+type+ ",%' order by id desc";
        		}
        		Log.d("LKP", "查询类型：" + sql);
        		cursor = db.rawQuery(sql,null);
        	}
            
            PigFactoryModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new PigFactoryModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("id"));
                    lm.pigFactoryName = cursor.getString(cursor.getColumnIndex("name"));
                    lm.product = cursor.getString(cursor.getColumnIndex("summary"));
                    lm.recommendNum = cursor.getFloat(cursor.getColumnIndex("recommendNum"));
//                    lm.scale = cursor.getString(cursor.getColumnIndex("scale"));
                    lm.scale="11111111";
                    lm.species = cursor.getString(cursor.getColumnIndex("type"));
                    lm.img = cursor.getString(cursor.getColumnIndex("imgUrl"));
                    LogInfo.LogOut("getpigfactory................." + lm.pigFactoryName);
                    programs.add(lm);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programs;
	}
	
	

}
