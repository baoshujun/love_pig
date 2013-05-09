package com.lovepig.engine.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;

import com.lovepig.model.GalleryModel;
import com.lovepig.model.NewsModel;
import com.lovepig.utils.LogInfo;

public class OnlineNewsDBEngine extends DBEngine {
    /**
     * 保存新闻类型
     * 
     * @param gm
     */
    public void saveType(ArrayList<GalleryModel> l) {
        String sql = "insert into onlinenewstypes(newstype_id,newstype_index,newstype_checked,newstype_name,newstype_date) values(?,?,?,?,?)";
        db.beginTransaction();
        try {
            GalleryModel gm;
            for (int i = 0; i < l.size(); i++) {
                gm = l.get(i);
                db.execSQL(sql, new String[] { String.valueOf(gm.typeid), String.valueOf(gm.indexNum), String.valueOf(gm.checked), gm.name, gm.mDate });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }
    }

    /**
     * 保存新闻
     * 
     * @param list
     * @param typeid
     * @param length
     */
    public void saveNews(ArrayList<NewsModel> list, int typeid, int length) {
        String sql = "insert into onlinenews(news_id,news_pos,news_name,news_time,news_publisher,news_intro,news_details,news_picpath,news_bigpicpath,news_picintro,news_typeid,news_istop,news_commentnum) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        NewsModel nm;
        db.beginTransaction();
        ArrayList<NewsModel> tempList=new ArrayList<NewsModel>();
        
        try {
            for (int i = 0; i < length; i++) {}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }

    }

    /**
     * 删除非新闻栏目的新闻
     * 
     * @param list
     */
    public void Delete_News_Outof_NewsType(ArrayList<GalleryModel> list) {
        StringBuffer sb = new StringBuffer();
        for (GalleryModel m : list) {
            sb.append(m.typeid);
            sb.append(",");
        }
        String typeids = sb.toString().substring(0, sb.toString().lastIndexOf(","));
        String sql = "delete from onlinenews where news_typeid not in (" + typeids + ")";
        db.beginTransaction();
        db.execSQL(sql);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 读取新闻类型
     * 
     * @return
     */
    public ArrayList<GalleryModel> getNewsTypes() {
        ArrayList<GalleryModel> programs = new ArrayList<GalleryModel>();
        try {
            String sql = "select newstype_id,newstype_index,newstype_checked,newstype_name,newstype_date from onlinenewstypes order by newstype_index";
            Cursor cursor = db.rawQuery(sql, null);
            GalleryModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new GalleryModel();
                    lm.typeid = cursor.getInt(cursor.getColumnIndex("newstype_id"));
                    lm.indexNum = cursor.getInt(cursor.getColumnIndex("newstype_index"));
                    lm.checked = cursor.getInt(cursor.getColumnIndex("newstype_checked"));
                    lm.name = cursor.getString(cursor.getColumnIndex("newstype_name"));
                    lm.mDate = cursor.getString(cursor.getColumnIndex("newstype_date"));
                    programs.add(lm);
                    LogInfo.LogOut("getNewsTypes-->name:" + lm.name + " lm.checked:" + lm.checked);
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programs;
    }

    /**
     * 读取新闻
     * 
     * @param newstype
     * @param flag
     * @return
     */
    public ArrayList<NewsModel> getOnlineNews(int newstype, int flag) {
        ArrayList<NewsModel> programs = new ArrayList<NewsModel>();
        try {
            String sql = "select news_id,news_name,news_time,news_publisher,news_intro,news_details,news_picpath,news_bigpicpath,news_picintro,news_istop,news_commentnum from onlinenews where news_typeid=? order by news_pos";
            LogInfo.LogOut("OnlineNewsAdapter", "sql:"+sql+ String.valueOf(newstype));
            Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(newstype) });
            NewsModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new NewsModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("news_id"));
                    lm.title = cursor.getString(cursor.getColumnIndex("news_name"));
                    lm.details = cursor.getString(cursor.getColumnIndex("news_details"));
                }
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programs;
    }

    /**
     * 更新新闻类型选中
     * 
     * @param id
     */
    public void updateTypeCheckedByID(int id1, int id2) {
        db.beginTransaction();
        try {
            String sqlvideo = null;
            sqlvideo = "update onlinenewstypes set newstype_checked=? where newstype_id=? ";
            for (int i = 0; i < 2; i++) {
                if (i == 0) {
                    db.execSQL(sqlvideo, new String[] { String.valueOf(1), String.valueOf(id1) });
                } else {
                    db.execSQL(sqlvideo, new String[] { String.valueOf(0), String.valueOf(id2) });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }

    }

    /**
     * 更新新闻类型选中
     * 
     * @param id
     */
    public void updateRefreshTimeByID(int id, String date) {
        db.beginTransaction();
        try {
            String sqlvideo = null;
            sqlvideo = "update onlinenewstypes set newstype_date=? where newstype_id=? ";
            db.execSQL(sqlvideo, new String[] { date, String.valueOf(id) });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }

    }

    /**
     * 删除新闻类型
     */
    public void deleteNewsType() {
        try {
            String sqlnews = "delete from onlinenewstypes";
            db.beginTransaction();
            db.execSQL(sqlnews);
            db.setTransactionSuccessful();
            db.endTransaction();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据新闻类型删除新闻
     * 
     * @param typeid
     */
    public void deleteNewsByTypeID(int typeid) {
        String sqlnews = "delete from onlinenews where news_typeid = ?";
        db.beginTransaction();
        db.execSQL(sqlnews, new String[] { String.valueOf(typeid) });
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
