package com.lovepig.engine.database;

import java.io.File;

import android.database.sqlite.SQLiteDatabase;

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
            File file = new File(Configs.lovePigPath);
            if (!file.isDirectory()) {
                file.mkdirs();
            }
            db = SQLiteDatabase.openDatabase(Configs.lovePigPath + Configs.DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (Exception e) {
            try {
                LogInfo.LogOut(TAG + " open db error and create db start ");
                db = SQLiteDatabase.openOrCreateDatabase(Configs.lovePigPath + Configs.DATABASE_NAME, null);
                createOnlineNewsTypes();
                createOnlineNews();
                db.setVersion(1);
                LogInfo.LogOut(TAG + " open db error and create db end");
            } catch (Exception e1) {// 捕获手机sdCard不存在的异常
                e1.printStackTrace();
            }
        }
    }

    /**
     * 在线新闻类型
     * 
     * @return
     */
    private static boolean createOnlineNewsTypes() {
        try {
            db.execSQL("CREATE TABLE onlinenewstypes (" + "newstype_id INTEGER PRIMARY KEY, " + // 新闻类型ID
                    "newstype_index INTEGER, " + // 新闻类型排序
                    "newstype_checked INTEGER, " + // 新闻类型是否被选中
                    "newstype_name TEXT, " + // 新闻类型名称
                    "newstype_date TEXT" + // 新闻类型最后更新时间
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
        }
        return false;
    }

    /**
     * 在线新闻
     * 
     * @return
     */
    private static boolean createOnlineNews() {
        try {
            db.execSQL("CREATE TABLE onlinenews (" + "news_id INTEGER, " + // 新闻ID
                    "news_pos INTEGER ," + // 新闻序号
                    "news_name TEXT ," + // 视频名称
                    "news_time TEXT ," + // 新闻发布时间
                    "news_publisher TEXT ," + // 新闻来源
                    "news_intro TEXT ," + // 新闻简介
                    "news_details TEXT ," + // 新闻内容
                    "news_picpath TEXT ," + // 新闻图片地址
                    "news_bigpicpath TEXT ," + // 新闻图片地址
                    "news_picintro TEXT ," + // 新闻图片简介
                    "news_typeid INTEGER," + // 新闻类型
                    "news_istop INTEGER," + // 新闻头条
                    "news_commentnum INTEGER," + // 新闻评论
                    "primary key(news_id,news_typeid)" + // 新闻类型
                    ");");
            LogInfo.LogOut(TAG + " Create Table audios ok");
            return true;
        } catch (Exception e) {
            LogInfo.LogOut(TAG + " Create Table audios err,table exists." + e.getMessage());
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
