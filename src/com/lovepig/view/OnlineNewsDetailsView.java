package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lovepig.engine.ImageEngine;
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsModel;
import com.lovepig.model.ShareModel;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.OnFlingListener;

public class OnlineNewsDetailsView extends BaseView implements OnFlingListener {
    int pos;
    Context context;
    RelativeLayout headRelativeLayout;
    Button mBackBtn;// shareBtn, rightBtn;// 返回
    TextView mTitle;// 新闻标题
    TextView mTimeProvenance;// 时间和出处
    TextView mDetails;// 新闻详情
//    ImageButton mPreviousNews;// 上一篇新闻
//    ImageButton mNextNews;// 下一篇新闻
    ImageView mNewsImage;// 新闻图片
    OnlineNewsManager mManager;
    // TlcyScrollView scrollView;
    ScrollView scrollView;
    private NewsDetailModel currentModel;
  //  private ShareModel shareModel;
    public boolean isFromDetail = false;

    public OnlineNewsDetailsView(Context context1, int layoutId, OnlineNewsManager manager) {
        super(context1, layoutId, manager);
        this.context = context1;
        this.mManager = manager;
        scrollView = (ScrollView) findViewById(R.id.onlinescrollview);
        scrollView.setOnTouchListener(this);
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setText("返回");
        headRelativeLayout = (RelativeLayout)findViewById(R.id.leftBtnLayout);
        headRelativeLayout.setVisibility(View.VISIBLE);
//        rightBtn = (Button) findViewById(R.id.rightBtn);
//        rightBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//               // mManager.sendMessage(mManager.obtainMessage(OnlineNewsManager.STATE_ENTER_COMMENT_DC, pos, 0));
//
//            }
//        });
        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.sendEmptyMessage(OnlineNewsManager.STATE_DETAILSBACK);
            }
        });
        findViewById(R.id.title).setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.onlinedetailstitle);
        mTimeProvenance = (TextView) findViewById(R.id.onlinedetailstime);
        mDetails = (TextView) findViewById(R.id.onlinedetails);
//        mPreviousNews = (ImageButton) findViewById(R.id.onlinepreviousnews);
//        mPreviousNews.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (Math.abs(System.currentTimeMillis() - l) > t + 300) {
////                    l = System.currentTimeMillis();
////                    pos--;
////                    mManager.sendMessage(mManager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS, pos, 2));
////                }
//            }
//        });
//        mNextNews = (ImageButton) findViewById(R.id.onlinenextnews);
//        mNextNews.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (Math.abs(System.currentTimeMillis() - l) > t) {
////                    l = System.currentTimeMillis();
////                    pos++;
////                    mManager.sendMessage(mManager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS, pos, 1));
////                    LogInfo.LogOut("下一篇被点击");
////                }
//
//            }
//        });
        mNewsImage = (ImageView) findViewById(R.id.onlinedetailsimg);
        /**
         * 新闻图片点击放大
         */
        mNewsImage.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                
            }
        });

//        shareBtn = (Button) findViewById(R.id.shareBtn);
//        shareBtn.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                currentModel = mManager.news.get(pos);
//                shareModel = new ShareModel(currentModel.title, "http://www.baidu.com", currentModel.iconPath);
//                constructNewsUrl(shareModel);
//               
//            }
//        });

    }

    private void constructNewsUrl(ShareModel shareModel) {
        Json json = new Json();
        json.put("id", currentModel.id);
//        shareModel.newsUrl = Configs.HostName1[1] + Configs.getNewsShareContent + json.toString();

    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        
        postDelayed(new Runnable() {
            @Override
            public void run() {
               // setChangeLine(mManager.news.get(pos).details);
            }
        }, 50);
    }

    /**
     * 设置新闻
     * 
     * @param index
     */
    public void ShowNews(int index) {
//        if (isFromDetail) {
//            isFromDetail=false;
//        } else {
//            // 把头条新闻加入到新闻list中
//            if (mManager.isTop == 1) {// 又头条新闻
//                int addIndex = mManager.topNews.size();
//                for (int j = 0; j < mManager.topNews.size(); j++) {
//                    if (mManager.news.contains(mManager.topNews.get(j))) {
//                        LogInfo.LogOut("OnlineNewsAdapter", "mManager.topNews.get(i))" + mManager.topNews.get(j).title);
//                        mManager.news.remove(mManager.topNews.get(j));
//                        addIndex--;
//                    }
//                }
//                for (int i = mManager.topNews.size() - 1; i >= 0; i--) {
//                    mManager.news.add(0, mManager.topNews.get(i));
//                }
//                // 判断是否是从头条中进入
//                if (mManager.isComeFromTop) {
//                    mManager.isComeFromTop = false;
//                    LogInfo.LogOut("OnlineNewsAdapter", "mManager.isComeFromTop = false " + pos);
//                } else {
//                    index += addIndex;
//                }
//            }
//        }
//
//        if (index >= mManager.news.size()) {
//            index = mManager.news.size() - 1;
//        }
//
//        scrollView.scrollTo(0, 0);
//        scrollView.smoothScrollTo(0, 0);
//        pos = index;
//        LogInfo.LogOut("OnlineNewsAdapter", "ShowNews-->pos:" + pos);
//        if (pos < mManager.news.size()) {
//            final NewsModel news = mManager.news.get(pos);
//            mTitle.setText(news.title);
//            mTimeProvenance.setText(news.createTime + (TextUtils.isEmpty(news.editor) ? "" : "    来源: " + news.editor));
//            LogInfo.LogOut("字数:" + (news.details == null ? 0 : news.details.length()));
//            mDetails.setText("");
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    setChangeLine(news.details);
//                }
//            }, 50);
//            if (TextUtils.isEmpty(news.imgPath)) {
//                mNewsImage.setVisibility(GONE);
//            } else {
//                mNewsImage.setVisibility(VISIBLE);
//                ImageEngine.setImageBitmap(news.imgPath, mNewsImage, R.drawable.ic_launcher, -1);
//            }
//            setCommentBtnText((news.commentNum > 0 ? news.commentNum : "") + "评论");
//        } else {
//            mManager.back();
//        }
    }

    private void setChangeLine(String str) {

        TextPaint paint = mDetails.getPaint();
        StringBuffer tmpbuffer = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        int detailwidth = mDetails.getWidth();
        LogInfo.LogOut("mDetails.getWidth()=" + mDetails.getWidth());
        if (str==null) {
			return;
		}
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            tmpbuffer.append(chars[i]);
            if (chars[i] == '\n') {
                buffer.append(tmpbuffer.toString());
                tmpbuffer = new StringBuffer();
            } else if (paint.measureText(tmpbuffer.toString()) > detailwidth) {
                int tmp = i + 1;
                tmpbuffer.deleteCharAt(tmpbuffer.length() - 1);
                i--;
                while (tmp < chars.length) {
                    if (chars[tmp] == ' ') {
                        tmp++;
                    } else if (chars[tmp] == '\n') {
                        i = tmp;
                        break;
                    } else {
                        break;
                    }
                }
                buffer.append(tmpbuffer.toString() + "\n");
                tmpbuffer = new StringBuffer();
            } else if (i == chars.length - 1) {
                buffer.append(tmpbuffer.toString());
            }
        }
        mDetails.setText(buffer.toString());
    }

    float eventStartX, eventStartY;

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        // && (Math.abs(System.currentTimeMillis() - l) > t)
//        LogInfo.LogOut("OnlineNewsAdapter", "onTouch..............x:" + getResources().getDimension(R.dimen.fling_x) + "y:" + getResources().getDimension(R.dimen.fling_y));
//        if (v == scrollView) {
//            l = System.currentTimeMillis();
//            if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                eventStartX = event.getX();
//                eventStartY = event.getY();
//            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
//                LogInfo.LogOut("OnlineNewsAdapter",
//                        "onTouch.........eventStartX - event.getX():" + (eventStartX - event.getX()) + "event.getY() - eventStartY:" + Math.abs(event.getY() - eventStartY));
//                if (eventStartX - event.getX() > getResources().getDimension(R.dimen.fling_x)
//                        && Math.abs(event.getY() - eventStartY) < getResources().getDimension(R.dimen.fling_y)) {
//                    onFling(1);
//                } else if (event.getX() - eventStartX > getResources().getDimension(R.dimen.fling_x)
//                        && Math.abs(event.getY() - eventStartY) < getResources().getDimension(R.dimen.fling_y)) {
//                    onFling(2);
//                } else if (Math.abs(event.getX() - eventStartX) < getResources().getDimension(R.dimen.fling_x)
//                        && event.getY() - eventStartY > getResources().getDimension(R.dimen.fling_y)) {
//                    onFling(3);
//                } else if (Math.abs(event.getX() - eventStartX) < getResources().getDimension(R.dimen.fling_x)
//                        && eventStartY - event.getY() > getResources().getDimension(R.dimen.fling_y)) {
//                    onFling(4);
//                }
//            }
//        }
//        return super.onTouch(v, event);
//
//    }

    @Override
    public void onFling(int to) {
        if (to == 1) {
            pos++;
            isFromDetail = true;
            mManager.sendMessage(mManager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS, pos, 1));
        } else if (to == 2) {
            pos--;
            isFromDetail = true;
            mManager.sendMessage(mManager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS, pos, 2));
        }
    }

    public void setCommentBtnText(String text) {
//        rightBtn.setVisibility(VISIBLE);
//        rightBtn.setText(text);
    }

    @Override
    public void onShow() {
        super.onShow();
        // setCommentBtnText("评论"+mManager.news.get(pos).commentSize);
        if (pos < mManager.news.size()) {
            NewsModel news = mManager.news.get(pos);
//            setCommentBtnText((news.commentNum > 0 ? news.commentNum : "") + "评论");
        }
    }

    public void ShowTopNewNews(ArrayList<NewsModel> newsList, int currentId, int isTop) {
        ShowNews(currentId);

    }
    /**
     * 设置新闻详情
     * 
     * @param index
     */
    public void ShowNewsDetail(NewsDetailModel model) {
        scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0, 0);
        LogInfo.LogOut("OnlineNewsAdapter", "ShowNews-->pos:" + pos);
        final NewsDetailModel news = model;
//        mTitle.setText(news.title);
        mTitle.setText("新闻标题");
        mTimeProvenance.setText(news.cTime + (TextUtils.isEmpty(news.cFrom) ? "" : "    来源: " + news.cFrom));
        LogInfo.LogOut("字数:" + (news.content == null ? 0 : news.content.length()));
        mDetails.setText("");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setChangeLine(news.content);
            }
        }, 50);
        if (TextUtils.isEmpty(news.imgUrl)) {
            mNewsImage.setVisibility(GONE);
        } else {
            mNewsImage.setVisibility(VISIBLE);
            ImageEngine.setImageBitmap(news.imgUrl, mNewsImage, R.drawable.ic_launcher, -1);
        }
//        setCommentBtnText((news.commentNum > 0 ? news.commentNum : "") + "评论");
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			mManager.sendEmptyMessage(OnlineNewsManager.STATE_DETAILSBACK);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
    
    
    
}
