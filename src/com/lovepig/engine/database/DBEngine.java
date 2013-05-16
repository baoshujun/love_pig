package com.lovepig.engine.database;

import java.io.File;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lovepig.main.Configs;
import com.lovepig.utils.LogInfo;

public class DBEngine {
    static {
        close();
        create();
    }
    public static SQLiteDatabase db;
    public static final String TAG = "DBEngine";

    public static void create() {
        try {
            File file = new File("/data/data/com.lovepig.main/databases/");
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            db = SQLiteDatabase.openDatabase("/data/data/com.lovepig.main/databases/" + Configs.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            try {
                LogInfo.LogOut(TAG + " open db error and create db start ");
                db = SQLiteDatabase.openOrCreateDatabase("/data/data/com.lovepig.main/databases/"  + Configs.DATABASE_NAME, null);
                createOnlineNews();
                db.setVersion(1);
                LogInfo.LogOut(TAG + " open db error and create db end");
            } catch (Exception e1) {// 捕获手机sdCard不存在的异常
                e1.printStackTrace();
            }
        }
    }

   

    /**
     * 在线新闻
     * public String title;// 标题
	public String summary;// 简介
	public String order;
	public int id;
	public int top;
	public String iconPath;//新闻图片
     * @return
     */
    private static boolean createOnlineNews() {
        try {
            db.execSQL("CREATE TABLE onlinenews (" + "table_id INTEGER PRIMARY KEY autoincrement, " + //表id
                    "id INTEGER ," + // 新闻序号
                    "title TEXT ," + // 视频名称
                    "newsOrder INTEGER ," + // 新闻发布时间
                    "top INTEGER ," + // 新闻来源
                    "iconPath TEXT ," + // 新闻简介
                    "summary TEXT ," + // 新闻内容
                    "catId INTEGER" + // 新闻来源
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table onlinenews err,table exists." + e.getMessage());
        }
        return false;
    }

    public static void close() {
        try {
            SQLiteDatabase.releaseMemory();
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
