package com.lovepig.engine.database;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.SQLException;

import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsGalleryModel;
import com.lovepig.model.NewsModel;
import com.lovepig.utils.LogInfo;

public class OnlineNewsDBEngine extends DBEngine {
    /**
     * 保存新闻类型
     * @param gm
     */
    public void saveType(ArrayList<NewsGalleryModel> l) {
        String sql = "insert into onlinenewstypes(newstype_id,newstype_index,newstype_checked,newstype_name,newstype_date) values(?,?,?,?,?)";
        db.beginTransaction();
        try {
            NewsGalleryModel gm;
            for (int i = 0; i < l.size(); i++) {
                gm = l.get(i);
                db.execSQL(sql, new String[] { String.valueOf(gm.id), String.valueOf(gm.indexNum), String.valueOf(gm.checked), gm.name, gm.mDate });
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

    public void saveNews(NewsModel m, int catId) {
        LogInfo.LogOut("saveNews.................");
        String sql = "insert into onlinenews(id,title,newsOrder,top,iconPath,summary,catId) values(?,?,?,?,?,?,?)";
        db.beginTransaction();
        try {
            db.execSQL(sql, new String[] { String.valueOf(m.id), m.title, String.valueOf(m.order), String.valueOf(m.top), m.iconPath, m.summary, String.valueOf(catId) });

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
    public void Delete_News_Outof_NewsType(ArrayList<NewsGalleryModel> list) {
        StringBuffer sb = new StringBuffer();
        for (NewsGalleryModel m : list) {
            sb.append(m.id);
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
     * 读取新闻
     * 
     * @param newstype
     * @param flag
     * @return
     */
    public ArrayList<NewsModel> getOnlineNews(int catId) {
        LogInfo.LogOut("getOnlineNews.................");
        ArrayList<NewsModel> programs = new ArrayList<NewsModel>();
        ArrayList<NewsModel> tops = new ArrayList<NewsModel>();
        try {
            String sql = "select id,title,newsOrder,top,iconPath,summary from onlinenews where catId=? order by id desc";

            Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(catId)});
            NewsModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new NewsModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("id"));
                    lm.title = cursor.getString(cursor.getColumnIndex("title"));
                    lm.order = cursor.getInt(cursor.getColumnIndex("newsOrder"));
                    lm.iconPath = cursor.getString(cursor.getColumnIndex("iconPath"));
                    if(cursor.getInt(cursor.getColumnIndex("top")) == 1){
                    	lm.top = true;
                    } else {
                    	lm.top = false;
                    }
                    lm.summary = cursor.getString(cursor.getColumnIndex("summary"));

                    LogInfo.LogOut("getOnlineNews................." + lm.title);
                    if (lm.top) {
                        tops.add(lm);
                    } else {
                        programs.add(lm);
                    }
                }
                programs.addAll(0, tops);
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
     * @param fontSize
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
        String sqlnews = "delete from onlinenews where catId = ?";
        db.beginTransaction();
        try {
        	db.execSQL(sqlnews, new String[] { String.valueOf(typeid) });
		} catch (Exception e) {
			// TODO: handle exception
		}
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 保存新闻详情
     * 
     */

    public void saveNewsDetail(NewsDetailModel m, int newsId) {
        LogInfo.LogOut("saveNewsDetails.................");
        String sql = "insert into newsdetail(news_id,title,newsOrder,summary,iconUri,content,cTime,imgUri,newsfrom,subTitle) values(?,?,?,?,?,?,?,?,?,?)";
        db.beginTransaction();
        try {

            db.execSQL(sql, new String[] { String.valueOf(newsId), m.title, m.order, m.summary, m.iconPath, m.content, m.cTime, m.imgUrl, m.cFrom, m.subTitle });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            db.setTransactionSuccessful();
            db.endTransaction();
        }

    }

    /**
     * 读取新闻详情
     * 
     */
    public ArrayList<NewsDetailModel> getNewsDetail(int newsId) {
        LogInfo.LogOut("getOnlineNews.................");
        ArrayList<NewsDetailModel> programs = new ArrayList<NewsDetailModel>();
        try {
            String sql = "select news_id,title,newsOrder,summary,iconUri,content,cTime,imgUri,newsfrom,subTitle from newsdetail where news_id=? order by news_id desc";

            Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(newsId) });
            NewsDetailModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new NewsDetailModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("news_id"));
                    lm.title = cursor.getString(cursor.getColumnIndex("title"));
                    lm.order = cursor.getString(cursor.getColumnIndex("newsOrder"));
                    lm.iconPath = cursor.getString(cursor.getColumnIndex("iconUri"));
                    lm.summary = cursor.getString(cursor.getColumnIndex("summary"));
                    lm.content = cursor.getString(cursor.getColumnIndex("content"));
                    lm.cTime = cursor.getString(cursor.getColumnIndex("cTime"));
                    lm.imgUrl = cursor.getString(cursor.getColumnIndex("imgUri"));
                    lm.cFrom = cursor.getString(cursor.getColumnIndex("newsfrom"));
                    lm.subTitle = cursor.getString(cursor.getColumnIndex("subTitle"));

                    LogInfo.LogOut("getOnlineNews................." + lm.title);
                    programs.add(lm);
                }

            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programs;
    }
    
    /**
     * 读取新闻详情
     * 
     */
    public ArrayList<NewsDetailModel> getNewsDetail() {
        LogInfo.LogOut("getOnlineNews.................");
        ArrayList<NewsDetailModel> programs = new ArrayList<NewsDetailModel>();
        try {
            String sql = "select news_id,title,newsOrder,summary,iconUri,content,cTime,imgUri,newsfrom,subTitle from newsdetail ";

            Cursor cursor = db.rawQuery(sql, null);
            NewsDetailModel lm;
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    lm = new NewsDetailModel();
                    lm.id = cursor.getInt(cursor.getColumnIndex("news_id"));
                    lm.title = cursor.getString(cursor.getColumnIndex("title"));
                    lm.order = cursor.getString(cursor.getColumnIndex("newsOrder"));
                    lm.iconPath = cursor.getString(cursor.getColumnIndex("iconUri"));
                    lm.summary = cursor.getString(cursor.getColumnIndex("summary"));
                    lm.content = cursor.getString(cursor.getColumnIndex("content"));
                    lm.cTime = cursor.getString(cursor.getColumnIndex("cTime"));
                    lm.imgUrl = cursor.getString(cursor.getColumnIndex("imgUri"));
                    lm.cFrom = cursor.getString(cursor.getColumnIndex("newsfrom"));
                    lm.subTitle = cursor.getString(cursor.getColumnIndex("subTitle"));
                    LogInfo.LogOut("getOnlineNews................." + lm.title);
                    programs.add(lm);
                }

            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return programs;
    }
    /**
     * 根据新闻id删除新闻详情
     * 
     * @param typeid
     */
    public void deleteNewsDetailsByID(int newsId) {
        String sqlnews = "delete from newsdetail where news_id = ?";
        db.beginTransaction();
        db.execSQL(sqlnews, new String[] { String.valueOf(newsId) });
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
