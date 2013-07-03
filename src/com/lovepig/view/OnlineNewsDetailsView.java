package com.lovepig.view;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lovepig.engine.ImageEngine;
import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsFontsModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.FontSizeSelectedDialog;
import com.lovepig.widget.FontSizeSelectedDialog.DialogListener;
import com.lovepig.widget.OnFlingListener;

public class OnlineNewsDetailsView extends BaseView implements OnFlingListener {
    private ArrayList<NewsFontsModel> fontModels = new ArrayList<NewsFontsModel>();
    int pos;
    Context context;
    RelativeLayout headRelativeLayout;
    Button mBackBtn;
    TextView mTitle,title;
    TextView mTimeProvenance;
    TextView mDetails;
    ImageView mNewsImage;
    OnlineNewsManager mManager;
    ScrollView scrollView;
    private TextView settings, favoritBtn, fontSizeBtn,shareBtn;
    public boolean isFromDetail = false;
    RelativeLayout menuBgLayout;
    LinearLayout menuLayout;
    public NewsDetailModel model;

    public OnlineNewsDetailsView(Context context1, int layoutId, OnlineNewsManager manager) {
        super(context1, layoutId, manager);
        this.context = context1;
        this.mManager = manager;
        scrollView = (ScrollView) findViewById(R.id.onlinescrollview);
        scrollView.setOnTouchListener(this);
        mBackBtn = (Button) findViewById(R.id.leftBtn);
        mBackBtn.setVisibility(View.VISIBLE);
        headRelativeLayout = (RelativeLayout) findViewById(R.id.leftBtnLayout);
        headRelativeLayout.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mManager.sendEmptyMessage(OnlineNewsManager.STATE_DETAILSBACK);
            }
        });
//        new OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                /EmptyMessage(OnlineNewsManager.STATE_DETAILSBACK);
//            	Application.application.currentManager.back();
//            }
//        });
        findViewById(R.id.title).setVisibility(View.GONE);
        mTitle = (TextView) findViewById(R.id.onlinedetailstitle);
        title = (TextView) findViewById(R.id.title);
        mTimeProvenance = (TextView) findViewById(R.id.onlinedetailstime);
        mDetails = (TextView) findViewById(R.id.onlinedetails);
        mNewsImage = (ImageView) findViewById(R.id.onlinedetailsimg);
        title.setText("新闻详情");
        title.setVisibility(View.VISIBLE);
        settings = (Button) this.findViewById(R.id.rightBtn);
        settings.setVisibility(VISIBLE);
        
        settings.setText("");
        settings.setBackgroundResource(R.drawable.night_base_action_bar_main_more);
        settings.setOnClickListener(this);
        favoritBtn = (TextView) findViewById(R.id.favoritBtn);
        favoritBtn.setOnClickListener(this);
        fontSizeBtn = (TextView) findViewById(R.id.fontSizeBtn);
        fontSizeBtn.setOnClickListener(this);
        shareBtn = (TextView) findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(this);

        menuBgLayout = (RelativeLayout) findViewById(R.id.menuBgLayout);
        menuBgLayout.setOnClickListener(this);
        menuLayout = (LinearLayout) findViewById(R.id.menuLayout);

        fontModels.add(new NewsFontsModel("特大号字", 30, false));
        fontModels.add(new NewsFontsModel("大号字", 24, false));
        fontModels.add(new NewsFontsModel("中号字", 18, true));
        fontModels.add(new NewsFontsModel("小号字", 12, false));
    }

   

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 50);
    }
    
    private void shareInfo(){
    	  Intent intent=new Intent(Intent.ACTION_SEND);
          intent.setType("text/plain");
          intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
          intent.putExtra(Intent.EXTRA_TEXT, "I would like to share this with you...");
          context.startActivity(Intent.createChooser(intent, "猪讯分享"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.shareBtn:
        	hiddenMenuBg();
        	shareInfo();
        	break;
        case R.id.favoritBtn:
            hiddenMenuBg();
            if (model.isFavorate) {
            	showToast("取消收藏成功！");
				model.isFavorate=false;
				favoritBtn.setText("收藏");
				mManager.dbEngine.deleteNewsDetailsByID(model.id);
			}else {
				showToast("收藏成功！");
				model.isFavorate=true;
				favoritBtn.setText("取消收藏");
				mManager.dbEngine.saveNewsDetail(model, model.id);
			}
            break;
        case R.id.fontSizeBtn:
            hiddenMenuBg();
            FontSizeSelectedDialog dialog = new FontSizeSelectedDialog(context);
            dialog.setListData(fontModels);
            dialog.setDialogListener(new DialogListener() {

                @Override
                public void onClick(NewsFontsModel currentModel) {
                    mDetails.setTextSize(currentModel.fontSize);
                    mDetails.invalidate();
                }
            }, null);
            dialog.show();
            break;
        case R.id.rightBtn:
            showMenuBg();
            break;
        case R.id.menuBgLayout:
            hiddenMenuBg();
            shareInfo();
            break;
        default:
            super.onClick(v);
            break;
        }
    }

    private void showMenuBg() {
        menuBgLayout.setVisibility(View.VISIBLE);
        menuLayout.setVisibility(View.VISIBLE);
    }

    private void hiddenMenuBg() {
        menuBgLayout.setVisibility(View.GONE);
        menuLayout.setVisibility(View.GONE);
    }

    /**
     * 设置新闻
     * 
     * @param index
     */
    public void ShowNews(int index) {
    }

    private void setChangeLine(String str) {

        TextPaint paint = mDetails.getPaint();
        StringBuffer tmpbuffer = new StringBuffer();
        StringBuffer buffer = new StringBuffer();
        int detailwidth = mDetails.getWidth();
        LogInfo.LogOut("mDetails.getWidth()=" + mDetails.getWidth());
        if (str == null) {
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
    }

    @Override
    public void onShow() {
        super.onShow();
        if (pos < mManager.news.size()) {
            NewsModel news = mManager.news.get(pos);
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
    	this.model=model;
    	if (model.isFavorate) {
			favoritBtn.setText("取消收藏");
		}else {
			favoritBtn.setText("收藏");
		}
        scrollView.scrollTo(0, 0);
        scrollView.smoothScrollTo(0, 0);
        LogInfo.LogOut("OnlineNewsAdapter", "ShowNews-->pos:" + pos);
        final NewsDetailModel news = model;
        mTitle.setText(news.title);
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mManager.sendEmptyMessage(OnlineNewsManager.STATE_DETAILSBACK);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean isBack() {
        if (menuBgLayout.getVisibility() == View.VISIBLE) {
            hiddenMenuBg();
            return true;
        }
        return false;
    }
}
