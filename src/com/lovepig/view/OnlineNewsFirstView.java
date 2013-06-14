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
import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseManager;

public class OnlineNewsFirstView   implements OnPageChangeListener {
    private OnlineNewsManager newsManager;
    private ViewPager viewPager;
    List<View> listview = new ArrayList<View>();
    MyPagerAdapter adapter;

//    private ViewFlipper flipper;
//    private LinearLayout dianLayout;
//    private GestureDetector detector;
//    // private NewsModel currentNews;
//    private int currentViewID;
//    private int COUNT;
//    private long lastFing;
//    private ImageView[] dianView = new ImageView[4];
//    // private ImageView[] newsView = new ImageView[4];
//    private TextView textView;

    public OnlineNewsFirstView(Context context, View view, BaseManager manager) {
        newsManager = (OnlineNewsManager) manager;
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        viewPager.setOnPageChangeListener(this);
        ImageView iv1 = new ImageView(context);
        listview.add(iv1);
        ImageView iv2 = new ImageView(context);
        listview.add(iv2);
        ImageView iv3 = new ImageView(context);
        listview.add(iv3);
        ImageView iv4 = new ImageView(context);
        listview.add(iv4);
        adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
//        detector = new GestureDetector(context, gestureListenerProcess);
//        // flipper = (ViewFlipper) this.findViewById(R.id.ViewFlipper01);
//        // flipper.setOnTouchListener(this);
//        dianLayout = (LinearLayout) findViewById(R.id.dianLayout);
//        dianView[0] = (ImageView) findViewById(R.id.image1);
//        dianView[1] = (ImageView) findViewById(R.id.image2);
//        dianView[2] = (ImageView) findViewById(R.id.image3);
//        dianView[3] = (ImageView) findViewById(R.id.image4);
//        // newsView[0] = (ImageView) findViewById(R.id.onlinepic1);
//        // newsView[1] = (ImageView) findViewById(R.id.onlinepic2);
//        // newsView[2] = (ImageView) findViewById(R.id.onlinepic3);
//        // newsView[3] = (ImageView) findViewById(R.id.onlinepic4);
//        textView = (TextView) findViewById(R.id.onlinetitle);
        // lastFing = System.currentTimeMillis();
        // timePolling();
    }

    /**
     * 初始化数据
     */
    public void initData(NewsModel news) {
//        boolean isUpdate = false;
//        newsManager.isTop = 0;
//        if (newsManager.topNews.size() > 0) {
//            if (news.topNews == null) {// 无头条,将第一条当做头条
//                if (newsManager.topNews.size() > 1 || newsManager.topNews.get(0).id != news.id) {
//                    isUpdate = true;
//                }
//            } else {
//                newsManager.isTop = 1;
//                if (newsManager.topNews.size() != news.topNews.size()) {
//                    isUpdate = true;
//                } else {
//                    for (int i = 0; i < news.topNews.size() && i < 4; i++) {
//                        if (newsManager.topNews.get(i).id != news.topNews.get(i).id) {
//                            isUpdate = true;
//                        }
//                    }
//                }
//            }
//        } else {
//            isUpdate = true;
//        }
//
//        if (isUpdate) {
//            LogInfo.LogOut("OnlineNewsAdapter", "OnlineNewsFirstDC-->initData-->有头条新闻->isUpdate=true");
//            newsManager.topNews.clear();
//            if (news.topNews == null) {// 无头条,将第一条当做头条,第一条从newslist中删除
//                newsManager.topNews.add(news);
//                // if(newsManager.news!=null&&newsManager.news.contains(news)){
//                // newsManager.news.remove(news);
//                // }
//            } else {
//                newsManager.isTop = 1;
//                newsManager.headModel = news;
//                for (int i = 0; i < news.topNews.size() && i < 4; i++) {
//                    newsManager.topNews.add(news.topNews.get(i));
//
//                }
//            }
//            COUNT = newsManager.topNews.size();
//            // LogInfo.LogOut("OnlineNewsFirstView", "cout:" + COUNT);
//            // for (int i = 0; i < 4; i++) {
//            // dianLayout.removeView(dianView[i]);
//            // flipper.removeView(newsView[i]);
//            // if (i < COUNT) {
//            // if (COUNT > 1) {
//            // dianLayout.addView(dianView[i]);
//            // }
//            // flipper.addView(newsView[i], newsView[i].getLayoutParams());
//            // ImageEngine.setImageBitmap(newsManager.topNews.get(i).iconPath,
//            // newsView[i], R.drawable.ic_launcher, -1);
//            // }
//            // }
//            // flipper.setDisplayedChild(0);
//            // currentViewID = 0;
//            // textView.setText(newsManager.topNews.get(currentViewID).title);
//            textView.setText(newsManager.topNews.get(0).title);
//        }
    }
    /**
     * whatID 1=向右滑动 2=向左滑动
     */
    public void setImageViewBackground(int whatID) {
//        try {
//            if (whatID == 1) {
//                flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.shoppingpush_right_in));
//                flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.shoppingpush_right_out));
//                flipper.showPrevious();
//                currentViewID -= 1;
//                if (currentViewID < 0) {
//                    currentViewID = COUNT - 1;
//                }
//            } else if (whatID == 2) {
//                flipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.shoppingpush_left_in));
//                flipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.shoppingpush_left_out));
//                flipper.showNext();
//                currentViewID += 1;
//                if (currentViewID >= COUNT) {
//                    currentViewID = 0;
//                }
//            }
//            for (int i = 0; i < COUNT; i++) {
//                dianView[i].setBackgroundResource(R.drawable.dian_bg);
//            }
//            dianView[currentViewID].setBackgroundResource(R.drawable.dian);
//
//            textView.setText(newsManager.topNews.get(currentViewID).title);
//            lastFing = System.currentTimeMillis();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * 定时10秒滑动新闻
     */
    void timePolling() {
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (Math.abs(System.currentTimeMillis() - lastFing) > 10000) {
//                    if (isShown() && COUNT > 1) {
//                        setImageViewBackground(2);
//                    }
//                }
//                timePolling();
//            }
//        }, 1000);
    }

//    public boolean onTouchEvent(MotionEvent event) {
////        LogInfo.LogOut("OnlineNewsAdapter", "-----------------onTouch:x=" + event.getX() + "  y=" + event.getY());
////        detector.onTouchEvent(event);
//        return false;
//    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogInfo.LogOut("OnlineNewsAdapter", "-----------------dispatchTouchEvent");
//        detector.onTouchEvent(ev);
//        return true;
//    }
//
//    private GestureDetector.SimpleOnGestureListener gestureListenerProcess = new GestureDetector.SimpleOnGestureListener() {
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            LogInfo.LogOut("-------------------------------------onScroll");
//            if (Math.abs(distanceX) > 0) {
//                return true;
//            }
//            return false;
//        }
//
//        @Override
//        public void onShowPress(MotionEvent e) {
//        }
//
//        @Override
//        public boolean onSingleTapUp(MotionEvent e) {
//            LogInfo.LogOut("OnlineNewsAdapter", "-------------------------------------onSingleTapUp");
//            newsManager.isComeFromTop = true;
//            newsManager.sendMessage(newsManager.obtainMessage(OnlineNewsManager.WHAT_NEWSDETAIL_ENTER_FROM_TOPNEW, currentViewID, newsManager.isTop, newsManager.topNews));
//            return false;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//            LogInfo.LogOut("OnlineNewsAdapter", "-------------------------------------onFling");
//            if (COUNT <= 1) {
//                return false;
//            }
//            int dx = (int) (e2.getX() - e1.getX()); // 计算滑动的距离
//            int flipperHeight = (int) flipper.getHeight();
//            int flipperTop = flipper.getTop();
//            if ((e1.getY() <= 1.5 * flipperHeight && e1.getY() >= flipperTop) || (e2.getY() <= 1.5 * flipperHeight && e2.getY() >= flipperTop)) {
//                if (Math.abs(dx) > 50 && Math.abs(velocityX) > Math.abs(velocityY)) {
//                    int whatId = 1;
//                    if ((velocityX > 0)) {
//                        whatId = 1;
//                        LogInfo.LogOut("-------right--------");
//                    } else {
//                        whatId = 2;
//                        LogInfo.LogOut("-------left--------");
//                    }
//                    setImageViewBackground(whatId);
//                }
//            }
//            return true;
//        }
//
//        @Override
//        public boolean onDown(MotionEvent e) {
//            return true;
//        }
//
//    };

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
            // ImageEngine.setImageBitmap(newsManager.topNews.get(position).iconPath,
            // v, R.drawable.ic_launcher, -1);
            v.setBackgroundResource(R.drawable.advers);
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
    public void onPageSelected(int arg0) {

    }

}
