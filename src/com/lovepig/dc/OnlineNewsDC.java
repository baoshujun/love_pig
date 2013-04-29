package com.lovepig.dc;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.pivot.BaseDC;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.MyGallery;
import com.lovepig.widget.MyGallery.TlcyGalleryListener;
import com.lovepig.widget.TlcyListLayout.OnRefreshListener;
import com.lovepig.widget.TlcyListLayout;

public class OnlineNewsDC extends BaseDC implements OnItemClickListener, OnRefreshListener,  TlcyGalleryListener {
    MyGallery myGallery;
    ImageView  qian, hou;
    TextView title;
    String[] indexString;
    private int index;
    OnlineNewsAdapter adapter;
    
    ListView listView;
    OnlineNewsManager manager;
    TextView timeText;
    private TlcyListLayout pulldownview;
    private Button backBtn;

    public OnlineNewsDC(Context context, int layoutId, OnlineNewsManager manager) {
        super(context, layoutId, manager);
        this.manager = manager;
        title = (TextView) findViewById(R.id.title);
        title.setText(context.getString(R.string.News));
        timeText = (TextView) findViewById(R.id.timeText);
        timeText.setVisibility(VISIBLE);

        myGallery = (MyGallery) findViewById(R.id.gallery1);
        hou = (ImageView) findViewById(R.id.galleryLeft);
        qian = (ImageView) findViewById(R.id.galleryRight);
        hou.setOnClickListener(this);
        qian.setOnClickListener(this);
        
        listView = (ListView) findViewById(R.id.listView1);
        backBtn=(Button) findViewById(R.id.leftBtn);
        backBtn.setVisibility(VISIBLE);
        backBtn.setOnClickListener(this);

        indexString = new String[] { "综合新闻", "行业新闻", "访谈板块", "产品信息", "法律法规"};
        myGallery.setAdapter(R.layout.item, R.drawable.button_1, R.dimen.fenlei_item_width, R.dimen.fenlei_item_height, indexString);
        if (!myGallery.isScroll()) {
            qian.setVisibility(GONE);
            hou.setVisibility(GONE);
        } else {
            qian.setVisibility(VISIBLE);
            hou.setVisibility(INVISIBLE);
        }
        myGallery.setOnItemClickListener(this);
        pulldownview = (TlcyListLayout) findViewById(R.id.pulldownview);
        pulldownview.setRefreshListener(this);
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
            myGallery.setAdapter(R.layout.item, R.drawable.button_1, R.dimen.fenlei_item_width, R.dimen.fenlei_item_height, mGallery);
            LogInfo.LogOut("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx---tlcyGallery.isScroll() =" + myGallery.isScroll());
            if (!myGallery.isScroll()) {
                hou.setVisibility(GONE);
                qian.setVisibility(GONE);
            } else {
                hou.setVisibility(INVISIBLE);
                qian.setVisibility(VISIBLE);
            }
            myGallery.setSelected(index);
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
        adapter.notifyDataSetChanged();
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
        pulldownview.setLoadMoreButton(hasMoreBtn);
    }

    /**
     * 获取最新数据
     */
    public void onRefreshComplete(String mDate) {
        setUpdateTime(mDate);
        UpdataData();
        setLoadMoreButton(true);
        pulldownview.onRefreshComplete(mDate);
    }

    /**
     * 加载更多
     */
    public void onLoadingComplete() {
        UpdataData();
        pulldownview.onLoadMoreComplete();

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
        pulldownview.onLoadMoreComplete();
    }
   //新闻列表被点击
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
   //新闻分类被点击
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
        adapter.notifyDataSetChanged();
    }
	@Override
	public void onLoadMore() {
		
		
	}
	@Override
	public void onRefresh() {
		
		
	}
    
}
