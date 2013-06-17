package com.lovepig.engine.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;

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
        String sql = "insert into pigfactory(id,title,summary,recommendNum,provinceId) values(?,?,?,?,?)";
        db.beginTransaction();
        try {
        	PigFactoryModel pigFactoryModel;
            for (int i = 0; i < pfm.size(); i++) {
                pigFactoryModel = pfm.get(i);
                db.execSQL(sql, new String[] { String.valueOf(pigFactoryModel.id), String.valueOf(pigFactoryModel.pigFactoryName), String.valueOf(pigFactoryModel.pigFactoryDesc), String.valueOf(pigFactoryModel.pigFactoryGradebarNum),String.valueOf(pigFactoryModel.provId) });
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
	public ArrayList<PigFactoryModel> findPigFactory(String name,int scale,String type){
        LogInfo.LogOut("getOnlineNews.................");
        ArrayList<PigFactoryModel> programs = new ArrayList<PigFactoryModel>();
        try {
        	String sql;
        	Cursor cursor = null;
        	if(name != null){
        		sql = "select * from pigfactory where name like %" + name + "% order by id desc"; 
        		cursor = db.rawQuery(sql,null);
        	} 
        	
        	if(scale != 0){
        		sql = "select * from pigfactory where name like %" + name + "% and scale = ? order by id desc";
        		cursor = db.rawQuery(sql,new String[]{String.valueOf(scale)});
        	}else if(type != null){
        		sql = "select * from pigfactory where name like %" + name + "% and type = ? order by id desc";
        		cursor = db.rawQuery(sql,new String[]{type});
        	}
            
            PigFactoryModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new PigFactoryModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("id"));
                    lm.pigFactoryName = cursor.getString(cursor.getColumnIndex("title"));
                    lm.pigFactoryDesc = cursor.getString(cursor.getColumnIndex("summary"));
                    lm.pigFactoryGradebarNum = cursor.getFloat(cursor.getColumnIndex("recommendNum"));
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
