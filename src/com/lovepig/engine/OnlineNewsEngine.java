package com.lovepig.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.GalleryModel;
import com.lovepig.model.NewsCommentModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.HttpEngine;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

public class OnlineNewsEngine extends BaseEngine {
	private static String GET_BY_CAT_ID="news/getByCatId?catId=23&userId=897";
	private static String GET_NEWS="news/list?userId=223";
	private static String GET_NEWS_DETAILS="news/detail?newsId=2";
    OnlineNewsManager manager;
    getNewsTask mGetNewsTask;
    getMoreNewsTask mGetMoreNewsTask;
    getNewsDetailTask mGetNewsDetail;
    int RequestDataSize = 20;// 请求的数据条数
    // 获取评论引擎
    private HttpEngine httpEngine = null;

    public OnlineNewsEngine(OnlineNewsManager manager) {
        super(manager);
        this.manager = manager;
    }

    /**
     * 更新（获取）新闻类型
     */
    public void updategrally() {
        Json j = new Json(0);
        new UpdateGalleryTask().execute(j.toString());
    }

    /**
     * 获取最新新闻
     * 
     * @param id
     * @param type
     */
    public void refreshNews(int id, int type) {
        Json j = new Json(0);
        j.put("typeId", type);
        j.put("flag", "0");
        j.put("id", id);
        j.put("size", RequestDataSize);
        mGetNewsTask = new getNewsTask();
        mGetNewsTask.execute(j.toString());
    }
    
    /**
     * 获取新闻详情
     * 
     * @param id
     * @param type
     */
    public void fetchNewsDetail(int id, int type) {
//        Json j = new Json(0);
//        j.put("typeId", type);
//        j.put("flag", "0");
//        j.put("id", id);
//        j.put("size", RequestDataSize);
    	mGetNewsDetail = new getNewsDetailTask();
        mGetNewsDetail.execute();
    }
    /**
     * 获取最新详情
     * 
     * @author DCH
     * 
     */
    class getNewsDetailTask extends AsyncTask<String, Void, NewsState> {
        boolean isStop;

        @Override
        protected NewsState doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_NEWS_DETAILS);
            if (isStop) {
                return null;
            } else {
            	LogInfo.LogOut("result:"+result);
                NewsState rs = ParseHttp1(result, 0);
//                if (rs.code.equals("hasnews") && !isStop) {
//                    manager.SaveNews(rs.newslist);
//                }
                if (rs.code.equals("hasnews") && !isStop) {
                    // manager.SetLatestNews(rs.newslist);
                }
                return rs;
            }
        }

        @Override
        protected void onPostExecute(NewsState result) {
            if (!isStop && result != null) {
                if (result.code.equals("hasnews")) {
//                    manager.SetLatestNews(result.newslist);
//                    // manager.SaveNews();
//                    manager.getNewsComplete(0);
//                    manager.ShowDetail();
//                    if (result.hasBtn != null) {
//                        // 有更多按钮
//                        manager.SetMoreBtn(true);
//                    } else {
//                        // 没有更多按钮
//                        manager.SetMoreBtn(false);
//                    }
                   manager.showNewsDetails(result.newslist);
                } else if (result.code.equals("neterror")) {
                    // 网络错误
                    manager.ShowNewsError("网络不可用,请检查您的网络！");
                } else {
                    // 服务器正常返回但没内容
                    manager.ShowNewsError(result.code);
                }
            } else {
            }
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    /**
     * 加载更多新闻
     * 
     * @param id
     * @param type
     */
    public void moreNews(int id, int type) {
        Json j = new Json(0);
        j.put("typeId", type);
        j.put("flag", "1");
        j.put("id", id);
        j.put("size", RequestDataSize);
        mGetMoreNewsTask = new getMoreNewsTask();
        mGetMoreNewsTask.execute(j.toString());
    }

    /**
     * 停止网络
     */
    public void StopTask() {
        if (mGetNewsTask != null) {
            mGetNewsTask.stop();
            mGetNewsTask = null;
        }
        if (mGetMoreNewsTask != null) {
            mGetMoreNewsTask.stop();
            mGetMoreNewsTask = null;
        }
        manager.CancelRefresh();
    }

    /**
     * 更新新闻类型
     * 
     * @author DCH
     * 
     */
    class UpdateGalleryTask extends AsyncTask<String, Void, ArrayList<GalleryModel>> {

        @Override
        protected ArrayList<GalleryModel> doInBackground(String... params) {
            return getGalleryItem(httpRequestThisThread(1, GET_BY_CAT_ID));
        }

        @Override
        protected void onPostExecute(ArrayList<GalleryModel> result) {
            manager.getGrally(result);
        }
    }

    /**
     * 解析新闻类型列表
     */
    private ArrayList<GalleryModel> getGalleryItem(String result) {
        ArrayList<GalleryModel> gallery = null;
        int count=0;
        if (result != null) {
            Json json = new Json(result);
            if (json.getString("status").equals("1")) {
                GalleryModel gModel;
                Json[] galleryarray = json.getJsonArray("news");
                if (galleryarray==null||galleryarray.length==0) {
					return gallery;
				}
                gallery = new ArrayList<GalleryModel>();
                for (int i = 0; i < galleryarray.length; i++) {
                    gModel = new GalleryModel();
                    gModel.typeid = galleryarray[i].getInt("id");
                    gModel.checked = galleryarray[i].getInt("checked");
                    gModel.indexNum = galleryarray[i].getInt("indexNum");
                    gModel.name = galleryarray[i].getString("name");
                    gallery.add(gModel);
                    
                }
                Collections.sort(gallery, new Comparator<GalleryModel>() {
                    @Override
                    public int compare(GalleryModel object1, GalleryModel object2) {
                        if (object1.indexNum > object2.indexNum) {
                            return 1;
                        }
                        return -1;
                    }
                });
            }
        }
        return gallery;
    }

    /**
     * 获取最新新闻
     * 
     * @author DCH
     * 
     */
    class getNewsTask extends AsyncTask<String, Void, NewsState> {
        boolean isStop;

        @Override
        protected NewsState doInBackground(String... params) {
            String result = httpRequestThisThread(1, GET_NEWS);
            if (isStop) {
                return null;
            } else {
            	LogInfo.LogOut("result:"+result);
                NewsState rs = ParseHttp1(result, 0);
//                if (rs.code.equals("hasnews") && !isStop) {
//                    manager.SaveNews(rs.newslist);
//                }
                if (rs.code.equals("hasnews") && !isStop) {
                    // manager.SetLatestNews(rs.newslist);
                }
                return rs;
            }
        }

        @Override
        protected void onPostExecute(NewsState result) {
            if (!isStop && result != null) {
                if (result.code.equals("hasnews")) {
                    manager.SetLatestNews(result.newslist);
                    // manager.SaveNews();
                    manager.getNewsComplete(0);
                    manager.ShowDetail();
                    if (result.hasBtn != null) {
                        // 有更多按钮
                        manager.SetMoreBtn(true);
                    } else {
                        // 没有更多按钮
                        manager.SetMoreBtn(false);
                    }
                } else if (result.code.equals("neterror")) {
                    // 网络错误
                    manager.ShowNewsError("网络不可用,请检查您的网络！");
                } else {
                    // 服务器正常返回但没内容
                    manager.ShowNewsError(result.code);
                }
            } else {
            }
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    /**
     * 加载更多
     * 
     * @author DCH
     * 
     */
    class getMoreNewsTask extends AsyncTask<String, Void, NewsState> {
        boolean isStop;

        @Override
        protected NewsState doInBackground(String... params) {
            String result = httpRequestThisThread(1, Configs.getNewsAction + params[0]);
            if (isStop) {
                return null;
            } else {
                return ParseHttp1(result, 1);
            }
        }

        @Override
        protected void onPostExecute(NewsState result) {
            if (!isStop) {
                if (result.code.equals("hasnews")) {
                    for (NewsModel news : result.newslist) {
                        manager.onLoadoldMoreNews(news);
                    }
                    manager.getNewsComplete(1);
                    manager.ShowDetail();
                    if (result.hasBtn != null) {
                        // 有更多按钮
                        manager.SetMoreBtn(true);
                    } else {
                        // 没有更多按钮
                        manager.SetMoreBtn(false);
                    }
                } else if (result.code.equals("neterror")) {
                    // 网络错误
                    manager.ShowNewsError("网络不可用,请检查您的网络！");
                } else {
                    // 服务器正常返回但没内容
                    manager.ShowNewsError(result.code);
                    manager.SetMoreBtn(false);
                }

            }
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }

    }

    /**
     * 解析新闻数据
     * 
     * @param result
     * @param flag
     * @return
     */
    private NewsState ParseHttp1(String result, int flag) {
        Json[] newsarray = null;
        NewsState newsState = new NewsState();
        if (result != null) {
            Json json = new Json(result);
            if (json.getString("status").equals("1")) {
                NewsModel news;
                newsarray = json.getJsonArray("news");
                int L = newsarray.length;
                if (L > 0) {
                    newsState.code = "hasnews";// 有新闻
                }
                if (L == OnlineNewsManager.DEFAULT_NEW_LENGTH) {
                    newsState.hasBtn = "hasmorebtn";// 有更多按钮
                }
                for (int i = 0; i < newsarray.length; i++) {
                    LogInfo.LogOut("news = " + newsarray.length);
                    news = new NewsModel();
                    news.paserJson(newsarray[i]);
//                    news.id = newsarray[i].getInt("id");
//                    news.createTime = newsarray[i].getString("createTime");
//                    news.title = newsarray[i].getString("title").replace("\r", "").replace("\n", "");
//                    news.summary = newsarray[i].getString("intro");
//                    news.details = newsarray[i].getString("content").replace("\r", "");
//                    news.commentNum = newsarray[i].getInt("commentSize");
//                    news.isTop = newsarray[i].getInt("isTop") == 1;
//                    if (news.summary != null) {// 简介段首自动缩进
//                        news.summary = "        " + news.summary;
//                        news.summary = news.summary.replaceAll("\n", "\n        ");
//                    }
//                    // if (news.details != null) {// 所有段首自动缩进
//                    // news.details = "        " + news.details;
//                    // news.details = news.details.replaceAll("\n",
//                    // "\n        ");
//                    // }
//                    if (newsarray[i].has("publisher")) {
//                        news.editor = newsarray[i].getString("publisher");
//                    }
//                    if (newsarray[i].has("picUrl")) {
//                        news.iconPath = newsarray[i].getString("picUrl");
//                    }
//                    if (newsarray[i].has("picInfo")) {
//                        news.picintro = newsarray[i].getString("picInfo");
//                    }
//                    if (newsarray[i].has("bigPic")) {
//                        news.imgPath = newsarray[i].getString("bigPic");
//                    }
//                    if (newsarray[i].has("commentSize")) {
//                        news.commentSize = newsarray[i].getInt("commentSize");
//                    }
//                    if (news.isTop) {
//                        if (newsState.newslist.size() > 0) {
//                            if (newsState.newslist.get(0).topNews == null) {
//                                news.topNews = new ArrayList<NewsModel>();
//                                news.topNews.add(news);
//                                newsState.newslist.add(0, news);
//                            } else {
//                                newsState.newslist.get(0).topNews.add(news);
//                            }
//                        } else {
//                            news.topNews = new ArrayList<NewsModel>();
//                            news.topNews.add(news);
//                            newsState.newslist.add(news);
//                        }
//                    } else {
                        newsState.newslist.add(news);
//                    }
//                    LogInfo.LogOut("OnlineNewsEngine", "name:" + news.id);
                }
            } else {
                newsState.code = json.getString("msg");// 服务器正常返回，但没数据
            }
        } else {
            newsState.code = "neterror";// 网络异常
        }
        return newsState;
    }

    class getNewsTask1 extends AsyncTask<String, Void, ArrayList<NewsModel>> {
        boolean isStop = false;

        @Override
        protected ArrayList<NewsModel> doInBackground(String... params) {
            String result = httpRequestThisThread(1, Configs.getNewsAction + params[0]);
            if (isStop) {
                return null;
            } else {
                return ParseHttp(result, 0);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NewsModel> lastnews) {
            if (!isStop) {
                manager.onLaterNews(lastnews);
            }
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    private ArrayList<NewsModel> ParseHttp(String response, int type) {
        ArrayList<NewsModel> lastnews = null;
        Json[] newsarray = null;
        if (response != null) {
            Json json = new Json(response);
            if (json.getString("status").equals("1")) {
                lastnews = new ArrayList<NewsModel>();
                NewsModel news;
                newsarray = json.getJsonArray("news");
                for (int i = 0; i < newsarray.length; i++) {
                    LogInfo.LogOut("news = " + newsarray.length);
                    news = new NewsModel();
                    news.id = newsarray[i].getInt("id");
                    news.createTime = newsarray[i].getString("createTime");
                    news.title = newsarray[i].getString("title").replace("\r", "").replace("\n", "");
                    news.summary = newsarray[i].getString("intro");
                    news.details = newsarray[i].getString("content");
                    news.commentNum = newsarray[i].getInt("commentSize");
                    news.isTop = newsarray[i].getInt("isTop") == 1;
                    if (news.summary != null) {// 简介段首自动缩进
                        news.summary = "        " + news.summary;
                        news.summary = news.summary.replaceAll("\n", "\n        ");
                    }
                    // if (news.details != null) {// 所有段首自动缩进
                    // news.details = "        " + news.details;
                    // news.details = news.details.replaceAll("\n",
                    // "\n        ");
                    // }
                    if (newsarray[i].has("publisher")) {
                        news.editor = newsarray[i].getString("publisher");
                    }
                    if (newsarray[i].has("picUrl")) {
                        news.iconPath = newsarray[i].getString("picUrl");
                    }
                    if (newsarray[i].has("picInfo")) {
                        news.picintro = newsarray[i].getString("picInfo");
                    }
                    if (newsarray[i].has("bigPic")) {
                        news.imgPath = newsarray[i].getString("bigPic");
                    }
                    if (newsarray[i].has("commentSize")) {
                        news.commentSize = newsarray[i].getInt("commentSize");
                    }
                    lastnews.add(news);
                    if (type == 1) {// 更多
                        manager.onLoadoldMoreNews(news);
                    }
                    LogInfo.LogOut("OnlineNewsEngine", "name:" + news.id);
                }
            } else {

            }
        }
        return lastnews;
    }

    class getMoreNewsTask1 extends AsyncTask<String, Void, ArrayList<NewsModel>> {
        boolean isStop = false;

        @Override
        protected ArrayList<NewsModel> doInBackground(String... params) {
            String result = httpRequestThisThread(1, Configs.getNewsAction + params[0]);
            if (isStop) {
                return null;
            } else {
                return ParseHttp(result, 1);
            }
        }

        @Override
        protected void onPostExecute(ArrayList<NewsModel> lastnews) {
            if (!isStop) {
                if (lastnews != null && lastnews.size() != RequestDataSize) {
                    manager.onOldNewsNoMore(lastnews);
                } else {
                    manager.onOldNews(lastnews);
                }
            }
        }

        public void stop() {
            isStop = true;
            cancel(isStop);
        }
    }

    class NewsState {
        public ArrayList<NewsModel> newslist = new ArrayList<NewsModel>();// 新闻数据
        public String code;// hasnews 表示有新闻 neterror 表示网络异常
        public String hasBtn;// hasmore 表示有更多按钮
    }

    public void sentNewsComments(NewsCommentModel newsCommentModel) {
        if (httpEngine != null) {
            httpEngine.cancelRequest();
        }
        // 判断一下网络是否可用

        httpEngine = new HttpEngine(manager);
        String params = newsCommentModel.updateCommentParams(0).toString();
        httpEngine.setListener(new HttpEngine.HttpEngineListener() {

            @Override
            public void onPreHttp() {
                manager.showLoading(manager.context.getString(R.string.loading), new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        httpEngine.cancelRequest();
                    }
                });
            }

            @Override
            public void onPostHttp(String result) {
                manager.dismissLoading();
                if (!result.equals("ok")) {
                    if (result.equals("nologin")) {
                    } else {
                        manager.showAlert(result);
                    }

                }
            }

            @Override
            public String onParseHttp(String response) {
                String msg = null;
                Json json = new Json(response);
                switch (json.getInt("result")) {
                case 0:// 失败

                    return "发表评论失败";
                case 1:// 成功
                    manager.sendMessage(manager.obtainMessage(OnlineNewsManager.STATE_SENT_COMMENT_SUC, json.getString("time")));
                    return "ok";

                case 2:// 审核中
                    return "发表评论成功,审核中.....";
                default:
                    break;
                }
                return msg;
            }

        });
        httpEngine.httpRequestNewThread(1, Configs.sentNewsCommentsAction + params);

    }

    public void getNewsComments(int firstNum, int newSId) {
        if (httpEngine != null) {
            httpEngine.cancelRequest();
        }
        // 判断一下网络是否可用

        httpEngine = new HttpEngine(manager);
        Json json = new Json(0);
        json.put("newsId", newSId);
        json.put("firstSize", firstNum);
        json.put("maxSize", 20);
        String params = json.toString();
        httpEngine.setListener(new HttpEngine.HttpEngineListener() {

            @Override
            public void onPreHttp() {
                manager.showLoading(manager.context.getString(R.string.loading), new OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        httpEngine.cancelRequest();
                    }
                });
            }

            @Override
            public void onPostHttp(String result) {
                manager.dismissLoading();
                if (!result.equals("ok")) {
                    if (result.equals("nologin")) {
                    } else {
                        manager.showAlert(result);
                    }

                }
            }

            @Override
            public String onParseHttp(String response) {
                String msg = null;
                Json json = new Json(response);
                ArrayList<NewsCommentModel> datas = new ArrayList<NewsCommentModel>();
                switch (json.getInt("result")) {
                case 0:// 失败
                    return "获取评论失败";
                case 1:// 成功
                    int count = json.getInt("count");
                    Json[] jsonArray = json.getJsonArray("comments");
                    if (jsonArray.length > 0) {
                        for (int i = 0; i < jsonArray.length; i++) {
                            datas.add(new NewsCommentModel(count).parserJsonForComments(jsonArray[i]));

                        }
                    }
                    manager.sendMessage(manager.obtainMessage(OnlineNewsManager.STATE_SENT_COMMENT_LIST, count, 0, datas));
                    return "ok";
                default:
                    break;
                }
                return msg;
            }

        });
        httpEngine.httpRequestNewThread(1, Configs.getNewsComment + params);

    }

    /**
     * [{"id":86905,"title":"别尔哥罗德市枪击案嫌犯被捕",
     * "content":"　　4月24日，俄罗斯警务部门经过两昼夜的全力搜捕",
     * "createTime":"2013-04-24 16:30","publisher":"新华社",
     * "intro":"4月24日，俄罗斯警务嫌犯", "picUrl":
     * "http://59.151.123.134:9999/NewsPicture/SmallPicture/7aaac2b2-3532-42c5-a87e-1c3265e585d7.jpg"
     * ,"picInfo":"
     * ","bigPic":"http://59.151.123.134:9999/NewsPicture/BigPicture
     * /c4943cde-0e56-4376-b676-a5b6c1883971.jpg","commentSize":0,"isTop":0},
     * {"id":86901,"title":"孟加拉国首都一大楼倒塌",
     * "content":"　　新华社达卡4月24日电\n　　新华社/法新 \r\n","createTime":"2013-04-24 16:22",
     * "publisher":"新华社","intro":"孟加拉国卫生部长哈克24日说，首都达卡郊区当 。 \r\n", "picUrl":
     * "http://59.151.123.134:9999/NewsPicture/SmallPicture/156fbd87-1843-4e08-8c94-56c6d85d7e67.jpg"
     * ,"picInfo":"", "bigPic":
     * "http://59.151.123.134:9999/NewsPicture/BigPicture/dfdb98ce-e300-4eab-bbbb-ddcb8ca7899f.jpg"
     * ,"commentSize":0,"isTop":0}, {"id":86899,"title":"法国通过同性恋婚姻法案",
     * "content":"　　新华社巴黎4月23日专电（记者郑斌）法　　新华社/美联 ",
     * "createTime":"2013-04-24 15:57"
     * ,"publisher":"新华社","intro":"法国国民议会（议会下院）23日投票通过了同性恋婚姻及收养子女法案。", "picUrl":
     * "http://59.151.123.134:9999/NewsPicture/SmallPicture/47dd3e57-8955-4299-a8e4-f9a8e75a37e8.jpg"
     * ,"picInfo":"", "bigPic":
     * "http://59.151.123.134:9999/NewsPicture/BigPicture/0501288b-9431-43e9-bcbf-cf382ad6fe98.jpg"
     * ,"commentSize":0,"isTop":0}]
     * 
     * @return
     */
    public ArrayList<NewsModel> getData() {
        ArrayList<NewsModel> datas=new ArrayList<NewsModel>();
        
        NewsModel news = new NewsModel();
        news.id = 86905;
        news.createTime = "2013-04-24 16:30";
        news.title = "别尔哥罗德市枪击案嫌犯被捕";
        news.summary ="别尔哥罗德市枪击案嫌犯被捕";
        news.details = "别尔哥罗德市枪击案嫌犯被捕";
        news.commentNum = 21;
        news.isTop =true;
        news.summary = "别尔哥罗德市枪击案嫌犯被捕"; 
        
        news.editor ="别尔哥罗德市枪击案嫌犯被捕"; ;
        news.iconPath ="http://59.151.123.134:9999/NewsPicture/SmallPicture/7aaac2b2-3532-42c5-a87e-1c3265e585d7.jpg";
        news.picintro =  "别尔哥罗德市枪击案嫌犯被捕";
        news.imgPath = "http://59.151.123.134:9999/NewsPicture/SmallPicture/7aaac2b2-3532-42c5-a87e-1c3265e585d7.jpg";
        news.commentSize = 4;
        datas.add(news);
        NewsModel news1 = new NewsModel();
        news1.id = 86906;
        news1.createTime = "2013-04-24 16:30";
        news1.title = "别尔哥罗德市枪击案嫌犯被捕";
        news1.summary ="别尔哥罗德市枪击案嫌犯被捕";
        news1.details = "别尔哥罗德市枪击案嫌犯被捕";
        news1.commentNum = 21;
        news1.isTop =false;
        news1.summary = "别尔哥罗德市枪击案嫌犯被捕"; 
        
        news1.editor ="别尔哥罗德市枪击案嫌犯被捕"; ;
        news1.iconPath ="http://59.151.123.134:9999/NewsPicture/SmallPicture/7aaac2b2-3532-42c5-a87e-1c3265e585d7.jpg";
        news1.picintro =  "别尔哥罗德市枪击案嫌犯被捕";
        news1.imgPath = "http://59.151.123.134:9999/NewsPicture/SmallPicture/7aaac2b2-3532-42c5-a87e-1c3265e585d7.jpg";
        news1.commentSize = 4;
        NewsModel news2 =news1;
        datas.add(news2);
        return datas;
    }

}
