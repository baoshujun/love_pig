package com.lovepig.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lovepig.engine.ImageEngine;
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.LogInfo;

public class OnlineNewsFirstView extends BaseView implements OnPageChangeListener {
    private OnlineNewsManager newsManager;
    private ViewPager viewPager;
    List<View> listview = new ArrayList<View>();
    MyPagerAdapter adapter;

    private int currentViewID;
    private ImageView[] dianView = new ImageView[4];
    private TextView textView;
    private LinearLayout dianLayout;

    public OnlineNewsFirstView(Context context, int layoutId, BaseManager manager) {
        super(context, layoutId, manager);
        newsManager = (OnlineNewsManager) manager;
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        dianLayout = (LinearLayout) findViewById(R.id.dianLayout);
        dianLayout.setVisibility(View.GONE);
        dianView[0] = (ImageView) findViewById(R.id.image1);
        dianView[1] = (ImageView) findViewById(R.id.image2);
        dianView[2] = (ImageView) findViewById(R.id.image3);
        dianView[3] = (ImageView) findViewById(R.id.image4);
        textView = (TextView) findViewById(R.id.onlinetitle);
    }

    /**
     * 初始化数据
     */
    public void initData(NewsModel news) {
        newsManager.topNews.clear();
        if (news.topNews == null) {// 无头条,将第一条当做头条,第一条从newslist中删除
            newsManager.topNews.add(news);
        } else {
            newsManager.headModel = news;
            for (int i = 0; i < news.topNews.size() && i < 4; i++) {
                newsManager.topNews.add(news.topNews.get(i));
            }
        }
        listview.clear();
        ImageView iv1 = new ImageView(context);
        iv1.setScaleType(ScaleType.FIT_XY);
        listview.add(iv1);
        ImageView iv2 = new ImageView(context);
        iv2.setScaleType(ScaleType.FIT_XY);
        listview.add(iv2);
        ImageView iv3 = new ImageView(context);
        iv3.setScaleType(ScaleType.FIT_XY);
        listview.add(iv3);
        ImageView iv4 = new ImageView(context);
        iv4.setScaleType(ScaleType.FIT_XY);
        listview.add(iv4);
        adapter.notifyDataSetChanged();
        viewPager.setCurrentItem(0);
        dianLayout.setVisibility(View.VISIBLE);

        currentViewID = 0;
        dianView[0].setBackgroundResource(R.drawable.dian_bg);
        dianView[1].setBackgroundResource(R.drawable.dian_bg);
        dianView[2].setBackgroundResource(R.drawable.dian_bg);
        dianView[3].setBackgroundResource(R.drawable.dian_bg);
        dianView[currentViewID].setBackgroundResource(R.drawable.dian);
    }

    public void clearData() {
        listview.clear();
        adapter.notifyDataSetChanged();
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return listview.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView v = (ImageView) listview.get(position);
            if (position <= newsManager.topNews.size() - 1) {
                ImageEngine.setImageBitmap(newsManager.topNews.get(position).iconPath, v, R.drawable.ic_launcher, -1);
            } else {
                v.setImageResource(R.drawable.ic_launcher);
            }
            // v.setOnClickListener(OnlineNewsFirstView.this);
            ((ViewPager) container).addView(v, 0);
            return listview.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(listview.get(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position >= 4) {
            currentViewID = 0;
            return;
        }
        currentViewID = position;
        dianView[0].setBackgroundResource(R.drawable.dian_bg);
        dianView[1].setBackgroundResource(R.drawable.dian_bg);
        dianView[2].setBackgroundResource(R.drawable.dian_bg);
        dianView[3].setBackgroundResource(R.drawable.dian_bg);
        dianView[position].setBackgroundResource(R.drawable.dian);

    }

    @Override
    public void onClick(View v) {
        // if (Math.abs(System.currentTimeMillis() - l) > t + 300) {
        // l = System.currentTimeMillis();
        // manager.sendMessage(manager.obtainMessage(OnlineNewsManager.STATE_SHOWNEWS,
        // newsManager.topNews.get(currentViewID).id, 0));
        // }
    }
}
