package com.lovepig.manager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.os.Message;
import android.widget.Toast;
import android.widget.ViewAnimator;
import com.lovepig.engine.OnlineNewsEngine;
import com.lovepig.engine.database.OnlineNewsDBEngine;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsGalleryModel;
import com.lovepig.model.NewsCommentModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;
import com.lovepig.view.OnlineNewsView;
import com.lovepig.view.OnlineNewsDetailsView;

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

    public static final int STATE_ENTER_COMMENT_DC = 10;// 进入评论页面
    public static final int STATE_SENT_COMMENT = 11;// 发表评论
    public static final int STATE_SENT_COMMENT_SUC = 12;// 发表评论
    public static final int STATE_SENT_COMMENT_LIST = 13;// 发表评论
    public static final int WHAT_NEWSDETAIL_ENTER_FROM_TOPNEW = 14;// 由top新闻进入新闻详情

    private OnlineNewsView mainDC;
    private OnlineNewsDetailsView detailsDC;
    public OnlineNewsEngine engine;
    private int TypeID = -1;// 新闻栏目的ID
    private int Loading_For_Detail_Flag;// -1 表示获取最新 大于0表示加载更多
    private int typeIndex;// 当前选择的新闻栏目
    private NewsCommentModel sendingModel;
    public int isTop;// 记录是否有头条新闻
    public ArrayList<NewsModel> topNews = new ArrayList<NewsModel>();
    public boolean isComeFromTop = false;
    public NewsModel headModel = new NewsModel();

    public OnlineNewsDBEngine dbEngine;

    public OnlineNewsManager(BaseActivity c) {
        super(c);
        if (engine == null) {
            engine = new OnlineNewsEngine(this);
        }

        if (dbEngine == null) {
            dbEngine = new OnlineNewsDBEngine();
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
                engine.refreshNews(mGallerys.get(typeIndex).id, OnlineNewsEngine.NEWS_LIMIT_LENGTH, news.get(news.size() - 1).id);
            } else {
                engine.refreshNews(mGallerys.get(typeIndex).id, OnlineNewsEngine.NEWS_LIMIT_LENGTH, 0);
            }
            break;
        case STATE_LOADMORE:
            try {
                engine.moreNews(mGallerys.get(typeIndex).id, OnlineNewsEngine.NEWS_LIMIT_LENGTH, news.get(news.size() - 1).id);
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
            // if (msg.arg1 >= news.size()) {
            // showLoading();
            // Loading_For_Detail_Flag = msg.arg1;
            // if(detailsDC!=null){
            // detailsDC.isFromDetail=false;
            // isComeFromTop = true;
            // }
            // sendEmptyMessage(STATE_LOADMORE);
            // } else if (msg.arg1 < 0) {
            // showLoading();
            // Loading_For_Detail_Flag = -1;
            // if(detailsDC!=null){
            // detailsDC.isFromDetail=false;
            // isComeFromTop = true;
            // }
            // sendEmptyMessage(STATE_REFRESH);
            // } else {
            // detailsDC.ShowNews(msg.arg1);
            // }
            showLoading();
            engine.fetchNewsDetail(1, 0);
            break;
        case STATE_UPDATE:
            // 如果msg.arg1为1则需要设置更多按钮
            if (msg.arg1 == 1) {
                mainDC.setLoadMoreButton(true);
            }
            mainDC.UpdataData();
            dismissLoading();
            break;
        case STATE_UPDATE_AFTER_DB:
            mainDC.UpdataData();
            engine.updategrally();
            break;
        case STATE_UPDATE_AFTER_TYPES:
            sendMessage(obtainMessage(STATE_REFRESH, 0, 0));
            break;
        case STATE_GALLERY_GETDATA_FIRST:
            showLoading();
            if (mGallerys != null && mGallerys.size() > 0) {
                int tmpid = mGallerys.get(msg.arg1).id;
                mainDC.onRefreshComplete(mGallerys.get(msg.arg1).mDate);
                mainDC.setLoadMoreButton(false);
                TypeID = tmpid;
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
                engine.refreshNews(mGallerys.get(typeIndex).id, OnlineNewsEngine.NEWS_LIMIT_LENGTH, 0);
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
            }
            engine.fetchNewsDetail(1, 0);
            // detailsDC.ShowTopNewNews((ArrayList<NewsModel>) msg.obj,
            // msg.arg1, msg.arg2);
            break;
        case R.id.rightBtn://个人信息被点击
        	Toast.makeText(context, "hahhah", Toast.LENGTH_SHORT).show();
        	if(!(Application.application.currentManager instanceof UserInfoManager)){
                Application.application.setMainManager(Application.userInfoManager);
                Application.userInfoManager.initData();
//                mainDC.viewReset(R.id.rightBtn);
            }
        	break;
        default:
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
        // if(Configs.isShowNewHelp(context)){
        // Configs.setShowNewHelp(context, false);
        // context.startActivity(new Intent(context,HelpActivity.class));
        // }

    }

    /**
     * 是否有更多按钮
     * 
     * @param hasmore
     */
    public void SetMoreBtn(boolean hasmore) {
        mainDC.setLoadMoreButton(hasmore);
        dismissLoading();
        // if(Configs.isShowNewHelp(context)){
        // Configs.setShowNewHelp(context, false);
        // context.startActivity(new Intent(context,HelpActivity.class));
        // }
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
            // mainDC.onRefreshComplete(mGallerys.get(TypeIndex).mDate);
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
     * 保存最新新闻，更新时间
     */
    public void SaveNews(ArrayList<NewsModel> news) {
        NewsGalleryModel model = mGallerys.get(typeIndex);
        model.mDate = (new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date()));

        // dbEngine.updateRefreshTimeByID(model.typeid, model.mDate);
        // dbEngine.deleteNewsByTypeID(TypeID);
        // dbEngine.saveNews(news, TypeID, news.size());

    }

    // ------------即将删除-----------------
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
     * 通知新闻类型
     */
    public void UpdateGrally() {
        if (mainDC != null) {
            showLoading();
            if (engine != null) {
                engine.updategrally();
            }
        }
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

    /**
     * 获取更新的新闻类型
     * 
     * @param mGallery
     */
    public void getGrally(ArrayList<NewsGalleryModel> mGallery) {
        // if (mGallery != null) {
        // String[] mString = initGrally(mGallery, 1);
        // if (mainDC != null && mString != null) {
        // mainDC.UpdateGallery(mString, TypeIndex);
        //
        // isGetGallry = true;
        // mainDC.getNewsByType(TypeIndex);
        // }
        // } else {
        // engine.refreshNews(0, 0);
        // //context.getString(R.string.netconnecterror)
        // //showAlert("获取新闻栏目失败，只能显示默认的栏目！");
        // // dismissLoading();
        // }
    }

    /**
     * @param flag
     *            0为从本地数据库读取types,1为从网络获取types
     */
    private String[] initGrally(ArrayList<NewsGalleryModel> program, int flag) {
        String[] mString = null;
        // int L = 0;
        // if (program != null && program.size() > 0) {
        // // if (flag == 1) {
        // // for (NewsGalleryModel m : mGallerys) {
        // // for (NewsGalleryModel m1 : program) {
        // // if (m.typeid == m1.typeid) {
        // // m1.mDate = m.mDate;
        // // break;
        // // }
        // // }
        // // }
        // }
        // mGallerys = program;
        // L = mGallerys.size();
        // mString = new String[L];
        // NewsGalleryModel m = null;
        // for (int i = 0; i < L; i++) {
        // m = mGallerys.get(i);
        // mString[i] = m.name;
        // if (m.checked == 1) {// 从数据库中查询数据
        // typeIndex = i;
        // TypeID = m.typeid;
        // }
        // }
        // }
        return mString;
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
        LogInfo.LogOut("11111111111111");
        return super.getMainDC();
    }

    public void getNewsCommentModes(int firstNum, int id) {
        engine.getNewsComments(firstNum, id);
    }

    public void showNewsDetails(NewsDetailModel ndm) {
        dismissLoading();
        if (ndm != null) {
            detailsDC.ShowNewsDetail(ndm);
        } else {
            showAlert("获取新闻详情失败！");
        }
    }
    
    public boolean isHiddenMenu(){
        if (detailsDC!=null) {
            return detailsDC.isBack();
        }
        return false;
    }
}
