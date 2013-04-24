package com.lovepig.dc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.pivot.BaseDC;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.TlcyGallery;
import com.lovepig.widget.TlcyGallery.TlcyGalleryListener;
import com.lovepig.widget.TlcyListLayout;

public class OnlineNewsDC extends BaseDC implements OnItemClickListener,  TlcyGalleryListener {
    TlcyGallery tlcyGallery;
    ImageView mainImage, qian, hou;
    TextView title;
    String[] indexString;
    private int index;
    OnlineNewsAdapter adapter;
    
    ListView listView;
    OnlineNewsManager manager;
    TextView timeText;
    private TlcyListLayout pulldownview;

    public OnlineNewsDC(Context context, int layoutId, OnlineNewsManager manager) {
        super(context, layoutId, manager);
        this.manager = manager;
        title = (TextView) findViewById(R.id.title);
        title.setText(context.getString(R.string.News));
        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setVisibility(VISIBLE);

        tlcyGallery = (TlcyGallery) findViewById(R.id.gallery1);
        mainImage = (ImageView) findViewById(R.id.onlinepic);
        hou = (ImageView) findViewById(R.id.galleryLeft);
        qian = (ImageView) findViewById(R.id.galleryRight);
        hou.setOnClickListener(this);
        qian.setOnClickListener(this);
        
        listView = (ListView) findViewById(R.id.listView1);
        findViewById(R.id.logoImg).setVisibility(VISIBLE);

        indexString = new String[] { "即时", "时政", "国际", "文娱", "体育", "房屋" };
        tlcyGallery.setAdapter(R.layout.item, R.drawable.ic_launcher, R.dimen.fenlei_item_width, R.dimen.fenlei_item_height, indexString);
        if (!tlcyGallery.isScroll()) {
            qian.setVisibility(GONE);
            hou.setVisibility(GONE);
        } else {
            qian.setVisibility(VISIBLE);
            hou.setVisibility(INVISIBLE);
        }
        tlcyGallery.setOnItemClickListener(this);
        pulldownview = (TlcyListLayout) findViewById(R.id.pulldownview);
//        pulldownview.setRefreshListener(this);
        adapter = new OnlineNewsAdapter(manager, listView, pulldownview);
        listView.setAdapter(adapter);
        listView.setFocusable(false);
           listView.setOnItemClickListener(this);
        setLoadMoreButton(false);
    }

    

    /**
     * 更新新闻分类
     * 
     * @param mGallery
     * @param index
     */
    public void UpdateGallery(String[] mGallery, int index) {
        if (mGallery != null) {
            LogInfo.LogOut("index:" + index);
            indexString = mGallery;
            this.index = index;
            tlcyGallery.setAdapter(R.layout.item, R.drawable.ic_launcher, R.dimen.fenlei_item_width, R.dimen.fenlei_item_height, mGallery);
            LogInfo.LogOut("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx---tlcyGallery.isScroll() =" + tlcyGallery.isScroll());
            if (!tlcyGallery.isScroll()) {
                hou.setVisibility(GONE);
                qian.setVisibility(GONE);
            } else {
                hou.setVisibility(INVISIBLE);
                qian.setVisibility(VISIBLE);
            }
            tlcyGallery.setSelected(index);
        }
    }

    /**
     * 根据新闻分类获取新闻
     * 
     * @param index
     */
    public void getNewsByType(int index) {
        manager.sendMessage(manager.obtainMessage(OnlineNewsManager.STATE_REFRESH, index, 0));
    }

    /**
     * 更新界面数据
     */
    public void UpdataData() {
//        adapter.notifyDataSetChanged();
    }

    /**
     * 设置最后更新时间
     */
    public void setUpdateTime(String time) {
        if (time != null && time.length() > 5) {
            String nowDateString = new SimpleDateFormat("MM-dd HH:mm:ss").format(new Date());
            nowDateString = nowDateString.substring(0, 5);
            if (nowDateString.equals(time.substring(0, 5))) {
                timeText.setText("更新于" + time.substring(5));
            } else {
                timeText.setText("更新于 " + time.substring(0, 5));
            }
        }
    }

    /**
     * 设置是否需要更多按钮
     * 
     * @param hasMoreBtn
     */
    public void setLoadMoreButton(boolean hasMoreBtn) {
//        pulldownview.setLoadMoreButton(hasMoreBtn);
    }

    /**
     * 获取最新数据
     */
    public void onRefreshComplete(String mDate) {
        setUpdateTime(mDate);
        UpdataData();
        //setLoadMoreButton(true);
//        pulldownview.onRefreshComplete(mDate);
    }

    /**
     * 加载更多
     */
    public void onLoadingComplete() {
        UpdataData();
//        pulldownview.onLoadMoreComplete();

    }

    /**
     * 已经没有更多了
     */
    public void onLoadingNoMore() {
        setLoadMoreButton(false);
    }

    /**
     * 取消刷新
     */
    public void CancelRefresh() {
//        pulldownview.onLoadMoreComplete();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (Math.abs(System.currentTimeMillis() - l) > t + 300) {
            l = System.currentTimeMillis();
            manager.sendMessage(manager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS, position, 0));
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        UpdateGallery(indexString, index);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onClicked(View v) {
        switch (v.getId()) {
        case R.id.galleryLeft:
            // tlcyGallery.scrollLeft();
            break;
        case R.id.galleryRight:
            // tlcyGallery.scrollRight();
            break;
        default:
            super.onClicked(v);
            break;
        }
    }

    @Override
    public boolean onItemClick(int position) {
        if (Math.abs(System.currentTimeMillis() - l) > t + 300) {
            l = System.currentTimeMillis();
            manager.showLoading();
            manager.sendMessageDelayed(manager.obtainMessage(OnlineNewsManager.STATE_GALLERY_CLICKED, position, 0), 300);
            return !manager.isGalleryNull();
        } else {
            return false;
        }
    }

    @Override
    public void onState(int state) {
        if (state == 1) {
            hou.setVisibility(INVISIBLE);
            qian.setVisibility(VISIBLE);
        } else if (state == 2) {
            hou.setVisibility(VISIBLE);
            qian.setVisibility(INVISIBLE);
        } else if (state == 3) {
            hou.setVisibility(VISIBLE);
            qian.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onShow() {
        super.onShow();
//        adapter.notifyDataSetChanged();
    }
    
}
