package com.lovepig.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.os.Message;
import android.util.Log;
import android.widget.ViewAnimator;

import com.lovepig.engine.NewsEngine;
import com.lovepig.engine.database.OnlineNewsDBEngine;
import com.lovepig.main.R;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsGalleryModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;
import com.lovepig.view.OnlineNewsDetailsView;
import com.lovepig.view.OnlineNewsView;
/**
 * 
 * 新闻
 *
 */
public class OnlineNewsManager extends BaseManager {
	
    public static final int DEFAULT_NEW_LENGTH = 20;// 默认取20条新闻
    public ArrayList<NewsModel> news = new ArrayList<NewsModel>();
    public ArrayList<NewsGalleryModel> mGallerys = new ArrayList<NewsGalleryModel>();
    public static final int STATE_REFRESH = 1;// 刷新获取最新新闻
    public static final int STATE_LOADMORE = 2;// 加载更多
    public static final int STATE_DETAILSBACK = 3;// 详情页面返回
    public static final int STATE_SHOWNEWS = 4;// 查看新闻详情
    public static final int STATE_GALLERY_CLICKED = 5;// 新闻分类被点击
    public static final int STATE_GALLERY_GETDATA_FIRST = 6;// 从数据库读取新闻数据，并通知更新新闻类型
    public static final int STATE_UPDATE = 7;// 更新界面
    public static final int STATE_UPDATE_AFTER_DB = 8;// 从数据库查询缓存后到网络更新types
    public static final int STATE_UPDATE_AFTER_TYPES = 9;// 从从网络更新types后
    public static final int WHAT_NEWSDETAIL_ENTER_FROM_TOPNEW = 14;// 由top新闻进入新闻详情

    private OnlineNewsView mainDC;
    private OnlineNewsDetailsView detailsDC;
    public NewsEngine engine;
    private int Loading_For_Detail_Flag;// -1 表示获取最新 大于0表示加载更多
    private int typeIndex;// 当前选择的新闻栏目
    public int isTop;// 记录是否有头条新闻
    public ArrayList<NewsModel> topNews = new ArrayList<NewsModel>();
    public boolean isComeFromTop = false;
    public NewsModel headModel = new NewsModel();

    public OnlineNewsDBEngine dbEngine;

    public OnlineNewsManager(BaseActivity c) {
        super(c);
        if (engine == null) {
            engine = new NewsEngine(this);
        }

        if (dbEngine == null) {
            dbEngine = new OnlineNewsDBEngine();
        }
    }
    /**
     * 初始化新闻数据
     */
    @Override
    public void initData() {
        try {
            LogInfo.LogOut("newsManager-->typeIndex:" + typeIndex);
            mGallerys = engine.getNewsGalleryModels();
            mainDC.UpdateGallery(mGallerys, typeIndex);
            sendMessage(obtainMessage(STATE_REFRESH, typeIndex, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressWarnings("unchecked")
    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case STATE_REFRESH:
            if (!Utils.isNetworkValidate(context)) {
                news = dbEngine.getOnlineNews(mGallerys.get(typeIndex).id);
                getNewsComplete(1);
                SetMoreBtn(false);
                return;
            }
            showLoading();
            if (news != null && news.size() > 0) {
                engine.refreshNews(mGallerys.get(typeIndex).id, NewsEngine.NEWS_LIMIT_LENGTH, news.get(news.size() - 1).id);
            } else {
                engine.refreshNews(mGallerys.get(typeIndex).id, NewsEngine.NEWS_LIMIT_LENGTH, 0);
            }
            break;
        case STATE_LOADMORE:
            try {
                engine.moreNews(mGallerys.get(typeIndex).id, NewsEngine.NEWS_LIMIT_LENGTH, news.get(news.size() - 1).id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        case STATE_DETAILSBACK:
            if (getNowShownDC() == detailsDC) {
                Loading_For_Detail_Flag = 0;
                //
                if (isTop == 1) {
                    news.removeAll(topNews);
                    news.add(0, headModel);
                }
                dcEngine.setMainDC(mainDC);
            } else {
                back();
            }
            break;
        case STATE_SHOWNEWS:// 获取新闻详情页面
            if (detailsDC == null) {
                detailsDC = new OnlineNewsDetailsView(context, R.layout.online_news_details, this);
            }
            if (dcEngine.getNowDC() != detailsDC) {
                enterSubDC(detailsDC);
            }
            showLoading();
            int id=msg.arg1;
            Log.d("LKP", "id--->" + id);
            ArrayList<NewsDetailModel> datas=dbEngine.getNewsDetail(id);
            if (datas.size()>0)
            {
            	NewsDetailModel model=datas.get(0);
            	model.isFavorate=true;
            	model.id=id;
				showNewsDetails(model);
			}else{
				engine.fetchNewsDetail(id);
			}
            break;
        case STATE_UPDATE:
            // 如果msg.arg1为1则需要设置更多按钮
            if (msg.arg1 == 1) {
                mainDC.setLoadMoreButton(true);
            }
            mainDC.UpdataData();
            dismissLoading();
            break;
        case STATE_UPDATE_AFTER_TYPES:
            sendMessage(obtainMessage(STATE_REFRESH, 0, 0));
            break;
        case STATE_GALLERY_GETDATA_FIRST:
            showLoading();
            if (mGallerys != null && mGallerys.size() > 0) {
                mainDC.onRefreshComplete(mGallerys.get(msg.arg1).mDate);
                mainDC.setLoadMoreButton(false);
                new Thread() {
                    public void run() {
                        sendEmptyMessage(STATE_UPDATE_AFTER_DB);
                    }
                }.start();
            } else {
                sendEmptyMessage(STATE_UPDATE_AFTER_DB);
            }
            break;
        case STATE_GALLERY_CLICKED:// 新闻分类被点击
            engine.StopTask();
            int position = msg.arg1;
            if (position != typeIndex || news == null || news.size() == 0) {
                if (news != null) {
                    news.clear();
                }

                SetMoreBtn(false);
                mainDC.UpdataData();
                if (!Utils.isNetworkValidate(context)) {
                    news = dbEngine.getOnlineNews(mGallerys.get(typeIndex).id);
                    mainDC.UpdataData();
                    return;
                }
                showLoading();
                typeIndex = position;
                engine.refreshNews(mGallerys.get(typeIndex).id, NewsEngine.NEWS_LIMIT_LENGTH, 0);
                ;
            }
            break;

        case WHAT_NEWSDETAIL_ENTER_FROM_TOPNEW:// 由顶部新闻进入新闻详情
            if (detailsDC == null) {
                detailsDC = new OnlineNewsDetailsView(context, R.layout.online_news_details, this);
            }
            if (dcEngine.getNowDC() != detailsDC) {
                enterSubDC(detailsDC);
            }
            int index = msg.arg1;
            ArrayList<NewsModel> tops = (ArrayList<NewsModel>) msg.obj;
            if (tops != null && index < tops.size()) {
                showLoading();
                 id=tops.get(msg.arg1).id;
                 datas=dbEngine.getNewsDetail(id);
                if (datas.size()>0)
                {
                	NewsDetailModel model=datas.get(0);
                	model.isFavorate=true;
    				showNewsDetails(model);
    			}else{
    				engine.fetchNewsDetail(id);
    			}
            }
            break;
            
        case 1000:
        	exePushNews();
        	break;
        }
    }

    /**
     * 网络错误或无新闻
     * 
     * @param code
     */
    public void ShowNewsError(String code) {
        showAlert(code);
        mainDC.CancelRefresh();
        mainDC.UpdataData();
        ShowDetail();
        dismissLoading();

    }
    /**
     * 网络错误或无新闻
     * 
     * @param code
     */
    public void ShowNewsError2(String code) {
       
        mainDC.CancelRefresh();
        mainDC.UpdataData();
        ShowDetail();
       

    }

    /**
     * 是否有更多按钮
     * 
     * @param hasmore
     */
    public void SetMoreBtn(boolean hasmore) {
        mainDC.setLoadMoreButton(hasmore);
        dismissLoading();
    }

    /**
     * 0为获取最新需要更新时间，1为加载更多
     * 
     * @param flag
     */
    public void getNewsComplete(int flag) {
        if (flag == 0) {
            dismissLoading();
            mainDC.onRefreshComplete("123456");
        } else {
            mainDC.onLoadingComplete();
        }
    }

    /**
     * 设置最新新闻
     * 
     * @param list
     */
    public void SetLatestNews(ArrayList<NewsModel> list) {
        news = list;
    }

    /**
     * 加载更多新闻
     * 
     * @param model
     */
    public void onLoadoldMoreNews(NewsModel model) {
        if (model != null) {
            news.add(model);
        }
    }

    /**
     * 显示新闻详情
     */
    public void ShowDetail() {
        if (getNowShownDC() == detailsDC) {
            if (Loading_For_Detail_Flag < 0) {
                detailsDC.ShowNews(0);
            } else if (Loading_For_Detail_Flag > 0) {
                detailsDC.ShowNews(Loading_For_Detail_Flag);
            }
            Loading_For_Detail_Flag = 0;
        }
    }
    /**
     * 最新新闻
     * 
     * @param laterNews
     */
    public void onLaterNews(ArrayList<NewsModel> laterNews) {
        try {
            if (laterNews != null) {// 下拉获取新闻，新闻列表始终保持20条
                mGallerys.get(typeIndex).mDate = (new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()));
                news = laterNews;
                if (mainDC != null) {
                    mainDC.onRefreshComplete(mGallerys.get(typeIndex).mDate);
                }
                if (news.size() < DEFAULT_NEW_LENGTH) {
                    mainDC.onLoadingNoMore();
                }
            } else {
                // news.clear();
                if (mainDC != null) {
                    mainDC.CancelRefresh();
                    mainDC.UpdataData();
                }
                showToast("没有新闻");
            }
            if (Loading_For_Detail_Flag < 0) {
                detailsDC.ShowNews(0);
            }
            dismissLoading();
            Loading_For_Detail_Flag = 0;
        } catch (Exception e) {
            dismissLoading();
            e.printStackTrace();
        }
    }

    public void onOldNews(ArrayList<NewsModel> laterNews) {
        if (laterNews == null) {
            showToast("没有更多新闻");
            mainDC.onLoadingNoMore();
        }
        if (laterNews != null && laterNews.size() < DEFAULT_NEW_LENGTH) {
            mainDC.onLoadingNoMore();
        }
        if (mainDC != null) {
            mainDC.onLoadingComplete();
        }
        if (Loading_For_Detail_Flag > 0) {
            if (Loading_For_Detail_Flag >= news.size()) {
                detailsDC.ShowNews(news.size() - 1);
            } else {
                detailsDC.ShowNews(Loading_For_Detail_Flag);
            }
        }
        dismissLoading();
        Loading_For_Detail_Flag = 0;
    }

    /**
     * 已经没有更多新闻了
     * 
     * @param laterNews
     */
    public void onOldNewsNoMore(ArrayList<NewsModel> laterNews) {
        showToast("已经没有更多新闻了");
        if (mainDC != null) {
            mainDC.onLoadingNoMore();
            mainDC.onLoadingComplete();
        }
        if (Loading_For_Detail_Flag > 0) {
            detailsDC.ShowNews(Loading_For_Detail_Flag);
        }
        dismissLoading();
        Loading_For_Detail_Flag = 0;
    }

    // ------------即将删除-----------------
    /**
     * 取消刷新
     */
    public void CancelRefresh() {
        mainDC.CancelRefresh();
    }


    /**
     * 新闻类型是否已经更新
     * 
     * @return
     */
    public boolean isGalleryNull() {
        if (mGallerys != null && mGallerys.size() > 0) {
            return false;
        }
        return true;
    }

   

   

    @Override
    public boolean backOnKeyDown() {
        if (getNowShownDC() == detailsDC) {
            Loading_For_Detail_Flag = 0;
            //
            if (isTop == 1) {
                news.removeAll(topNews);
                news.add(0, headModel);
            }
            dcEngine.setMainDC(mainDC);
            return true;
        }
        return super.backOnKeyDown();
    }

   

    @Override
    public void onLoadingCacel() {
        engine.StopTask();
        if (getNowShownDC() == detailsDC) {
            if (Loading_For_Detail_Flag >= news.size()) {
                Loading_For_Detail_Flag = news.size() - 1;
            } else if (Loading_For_Detail_Flag < 0) {
                Loading_For_Detail_Flag = 0;
            }
            detailsDC.ShowNews(Loading_For_Detail_Flag);
            Loading_For_Detail_Flag = 0;
        }
        super.onLoadingCacel();
    }

    @Override
    public ViewAnimator getMainDC() {
        if (mainDC == null) {
            mainDC = new OnlineNewsView(context, R.layout.online_news, this);
        }
        if (detailsDC == null) {
            detailsDC = new OnlineNewsDetailsView(context, R.layout.online_news_details, this);
        }
        dcEngine.setMainDC(mainDC);
        
        return super.getMainDC();
    }
    public void showNewsDetails(NewsDetailModel ndm) {
        dismissLoading();
        if (ndm != null) {
            detailsDC.ShowNewsDetail(ndm);
        } else {
            showAlert("获取新闻详情失败！");
        }
    }

    public boolean isHiddenMenu() {
        if (detailsDC != null) {
            return detailsDC.isBack();
        }
        return false;
    }
    
    public void exePushNews(){
    	engine.pushNews(0, 0, Utils.returnNowTime());
    }
    
    public void updateVersion(){
    	 engine.updateVersion();
    }
}
