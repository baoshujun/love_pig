package com.lovepig.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.lovepig.main.R;
import com.lovepig.utils.LogInfo;

/**
 * 此layout 只能添加一个listview
 * 
 * @author DCH
 * 
 */
public class ListLayout extends FrameLayout implements GestureDetector.OnGestureListener {
    private static final int STATE_CLOSE = -1;
    private static final int STATE_OPEN = 1;
    private static final int STATE_REFRESH = 2;// 正在更新或者加载更多
    private static final int STATE_COMPLETE = 3;//
    private static final int DIRECTION_OF_RATATION = 180;// 旋转方向
    private static final int DURATION_OF_RATATION = 250;// 动画持续时间
    private static final int CLOSEDELAY = 300;
    private static final int REFRESHDELAY = 300;
    private static int HEADPADDING_MAX_LENGHT = 0;
    private static final double SCALE = 0.4D;// 下拉灵敏度
    private boolean hasMore = true;
    private boolean isAutoLoadMore = false;
    private boolean isEnd = true;
    private boolean listviewDoScroll = false;
    private boolean isNoMore = false;// 已经没有更多了
    private String loading;
    private String mDate;
    private String pulldowntorefresh;
    private String releasetorefresh;
    private String update_time;
    private int mDestPading;
    private int mLastPading;
    private int mPading;
    private int mState = STATE_CLOSE;// 状态
    private GestureDetector mDetector;
    private Flinger mFlinger;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private ImageView mArrow;
    private ListView mListView;
    private TextView mTitle;
    private TextView more;
    private FrameLayout mUpdateContent;
    private LinearLayout ll;
    private LinearLayout foot;// 底部更多按钮
    private RotateAnimation mAnimationUp;// 向上动画
    private RotateAnimation mAnimationDown;// 向下动画
    private OnRefreshListener mRefreshListener;
    private OnRefreshScrollListener mRefreshScrollListener;// 滚动条事件
    private long ActionDownTime = 0;
    private boolean isSelectFirst = false;
    private int timeDelay = 220;
    private long l = 0;

    public ListLayout(Context paramContext) {
        super(paramContext);
        mDetector = new GestureDetector(paramContext, this);
        mFlinger = new Flinger();
        init();
        addUpdateBar();
    }

    public ListLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mDetector = new GestureDetector(this);
        mFlinger = new Flinger();
        init();
        addUpdateBar();
    }

    private void addUpdateBar() {
        initAnimation();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_refresh_header, null);
        addView(view);
        view.setFocusable(false);
        mUpdateContent = (FrameLayout) getChildAt(0).findViewById(R.id.iv_content);
        mArrow = new ImageView(getContext());
        mArrow.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mArrow.setLayoutParams(layoutParams);
        mArrow.setImageResource(R.drawable.arrow_down);
        mUpdateContent.addView(mArrow);
        layoutParams.gravity = 17;
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        int iPadding = getResources().getDimensionPixelSize(R.dimen.updatebar_padding);
        mProgressBar.setPadding(iPadding, iPadding, iPadding, iPadding);
        mProgressBar.setLayoutParams(layoutParams);
        mUpdateContent.addView(mProgressBar);
        mTitle = (TextView) findViewById(R.id.tv_title);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        mAnimationUp = new RotateAnimation(0, DIRECTION_OF_RATATION, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mAnimationUp.setInterpolator(new LinearInterpolator());
        mAnimationUp.setDuration(DURATION_OF_RATATION);
        mAnimationUp.setFillAfter(true);
        mAnimationDown = new RotateAnimation(DIRECTION_OF_RATATION, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mAnimationDown.setInterpolator(new LinearInterpolator());
        mAnimationDown.setDuration(DURATION_OF_RATATION);
        mAnimationDown.setFillAfter(true);
    }

    private void init() {
        HEADPADDING_MAX_LENGHT = getResources().getDimensionPixelSize(R.dimen.updatebar_height);
        setDrawingCacheEnabled(false);
        setBackgroundDrawable(null);
        setClipChildren(false);
        setFocusable(false);
        mDetector.setIsLongpressEnabled(false);
        mPading = -HEADPADDING_MAX_LENGHT;
        mLastPading = -HEADPADDING_MAX_LENGHT;
        pulldowntorefresh = getContext().getText(R.string.drop_dowm).toString();
        releasetorefresh = getContext().getText(R.string.release_update).toString();
        loading = getContext().getText(R.string.doing_update).toString();
        update_time = getContext().getText(R.string.update_time).toString();
    }

    /**
     * 是否需要更多按钮
     * 
     * @param paramBoolean
     */

    public void setLoadMoreButton(boolean paramBoolean) {
        hasMore = paramBoolean;
        ll.removeAllViews();
        if (paramBoolean) {
            ll.addView(foot);
        }
        invalidate();
        // updateLayout();
    }

    /**
     * 如果设置自动加载更多当没有更多时调用该方法
     * 
     * @param paramBoolean
     */
    public void setNoMore() {
        if (isAutoLoadMore) {
            isNoMore = true;
        }
    }

    /**
     * 是否有更多按钮
     * 
     * @return
     */
    public boolean isHasLoadMoreButton() {
        return hasMore;
    }

    /**
     * 设置自动加载更多
     * 
     * @param paramBoolean
     */
    public void setAutoLoadMore(boolean paramBoolean) {
        isAutoLoadMore = paramBoolean;
        if (paramBoolean) {
            if (isAutoLoadMore) {
                // foot.setVisibility(View.INVISIBLE);
            }
            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
                public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
                    LogInfo.LogOut("mListView.setOnScrollListener  1");
                    if (mRefreshScrollListener != null) {
                        mRefreshScrollListener.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
                    }
                    if (paramInt1 + paramInt2 < paramInt3 - 1) {
                        isEnd = false;
                    } else {
                        isEnd = true;
                    }
                }

                public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
                    LogInfo.LogOut("mListView.onScrollStateChanged  1");
                    if (mRefreshScrollListener != null) {
                        mRefreshScrollListener.onScrollStateChanged(paramAbsListView, paramInt);
                    }
                    if (!isEnd || paramInt != 0 || !hasMore || isNoMore || mState == STATE_REFRESH) {
                        return;
                    }
                    onLoadMore();
                }
            });
            foot.setOnClickListener(null);
        } else {
            foot.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramView) {
                    if(Math.abs(System.currentTimeMillis() - l) > 500){
                        l=System.currentTimeMillis();
                        onLoadMore();
                    }
                }
            });
            mListView.setOnScrollListener(null);
        }
    }

    /**
     * 当前是否为自动加载更多
     * 
     * @return
     */
    public boolean isAutoLoadMore() {
        return isAutoLoadMore;
    }

    public void setRefreshListener(OnRefreshListener paramOnRefreshListener) {
        mRefreshListener = paramOnRefreshListener;
    }

    public void setRefreshScrollListener(OnRefreshScrollListener mScrollListener) {
        mRefreshScrollListener = mScrollListener;
    }

    private void onLoadMore() {
        mState = STATE_REFRESH;
        mProgressBar2.setVisibility(View.VISIBLE);
        String str;
        if (mDate != null) {
            str = update_time + mDate;
        } else {
            str = "";
        }
        if (isAutoLoadMore) {
            foot.setVisibility(View.VISIBLE);
        }
        mTitle.setText(loading + "\n" + str);
        mProgressBar.setVisibility(View.VISIBLE);
        mArrow.clearAnimation();
        mArrow.setVisibility(View.INVISIBLE);
        more.setText(loading);
        if (mRefreshListener == null) {
            return;
        }
        mRefreshListener.onLoadMore();
    }

    private void onRefresh() {
        if (isNoMore && isAutoLoadMore) {
            isNoMore = false;
            // foot.setVisibility(View.INVISIBLE);
        }
        mState = STATE_REFRESH;
        String str;
        if (mDate != null) {
            str = update_time + mDate;
        } else {
            str = "";
        }
        mTitle.setText(loading + "\n" + str);
        mProgressBar.setVisibility(View.VISIBLE);
        mArrow.clearAnimation();
        mArrow.setVisibility(View.INVISIBLE);
        if (mRefreshListener == null) {
            return;
        }
        mRefreshListener.onRefresh();
    }

    /**
     * 刷新完成带时间
     * 
     * @param paramString
     */
    public void onRefreshComplete(String paramString) {
        mDate = paramString;
        onRefreshComplete();
    }

    /**
     * 刷新完成
     */
    public void onRefreshComplete() {
        mState = STATE_COMPLETE;
        mArrow.setImageResource(R.drawable.arrow_down);
        ll.removeAllViews();
        if (hasMore) {
            ll.addView(foot);
        }
        scrollToClose();
        mDestPading = -HEADPADDING_MAX_LENGHT;
        mPading = -HEADPADDING_MAX_LENGHT;
        mLastPading = -HEADPADDING_MAX_LENGHT;
        updateLayout();
    }

    /**
     * 加载更多完成带时间
     * 
     * @param paramString
     */
    public void onLoadMoreComplete(String paramString) {
        mDate = paramString;
        onLoadMoreComplete();
    }

    /**
     * 加载更多完成
     */
    public void onLoadMoreComplete() {
        onRefreshComplete();
        if (isAutoLoadMore) {
            // foot.setVisibility(View.INVISIBLE);
        }
        more.setText(R.string.More);
        mState = STATE_CLOSE;
        mProgressBar2.setVisibility(View.GONE);
        mPading = (-HEADPADDING_MAX_LENGHT);
        mLastPading = (-HEADPADDING_MAX_LENGHT);
        if (isNoMore && isAutoLoadMore) {
            foot.setVisibility(View.GONE);
        }
    }

    private boolean release() {
        boolean release;
        if (!listviewDoScroll) {
            if (mPading <= 0) {
                scrollToClose();
                mDestPading = -HEADPADDING_MAX_LENGHT;
            } else {
                scrollToUpdate();
                mDestPading = 0;
            }
            updateLayout();
            release = false;
        } else {
            listviewDoScroll = false;
            release = false;
        }
        return release;
    }

    private void scrollToClose() {
        mFlinger.startUsingDistance(HEADPADDING_MAX_LENGHT, CLOSEDELAY);
    }

    private void scrollToUpdate() {
        mFlinger.startUsingDistance(mPading, REFRESHDELAY);
    }

    private void updateView() {
        String str;
        if (mDate != null) {
            str = update_time + mDate;
        } else {
            str = "";
        }
        if (mPading > 0) {
            mTitle.setText(releasetorefresh + "\n" + str);
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrow.setVisibility(View.VISIBLE);
            if (mLastPading <= 0) {
                mArrow.clearAnimation();
                mArrow.startAnimation(mAnimationUp);
            }
        } else if (mPading < 0) {
            mArrow.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mTitle.setText(pulldowntorefresh + "\n" + str);
            if (mLastPading >= 0) {
                mArrow.clearAnimation();
                mArrow.startAnimation(mAnimationDown);
            }
        }
        mLastPading = mPading;
    }

    int y = 0;

    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent) {
        int mMotionEventAction = paramMotionEvent.getAction();
        boolean mTouchEvent = mDetector.onTouchEvent(paramMotionEvent);
        switch (mMotionEventAction) {
        case MotionEvent.ACTION_DOWN:
            if (paramMotionEvent.getPointerCount() >= 2) {
                paramMotionEvent = MotionEvent.obtain(paramMotionEvent.getDownTime(), paramMotionEvent.getEventTime(), MotionEvent.ACTION_CANCEL, paramMotionEvent.getX(0),
                        paramMotionEvent.getY(0), paramMotionEvent.getMetaState());
                paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
            } else {
                y = (int) paramMotionEvent.getY();
            }
            updateLayout();
            try {
                super.dispatchTouchEvent(paramMotionEvent);
            } catch (Exception e) {
            }
            break;
        case MotionEvent.ACTION_UP:
            if (getChildAt(1).getTop() > paramMotionEvent.getX() || paramMotionEvent.getX() > getChildAt(1).getBottom()) {
                mMotionEventAction = 0;
            }
            // if (mTouchEvent || (mPading != -HEADPADDING_MAX_LENGHT && mPading
            // != 0)) {
            if (mTouchEvent || mPading != -HEADPADDING_MAX_LENGHT || mMotionEventAction == 0) {
                LogInfo.LogOut("mTouchEvent=" + mTouchEvent + "   mPading=" + mPading + "   MAX_LENGHT=" + HEADPADDING_MAX_LENGHT + "   mMotionEventAction" + mMotionEventAction);
                if (mState != STATE_CLOSE) {
                    release();
                }
            }
            updateLayout();
            super.dispatchTouchEvent(paramMotionEvent);
            break;
        case MotionEvent.ACTION_MOVE:
            updateLayout();
            int mMove = y;
            if (paramMotionEvent.getPointerCount() >= 2) {
                paramMotionEvent = MotionEvent.obtain(paramMotionEvent.getDownTime(), paramMotionEvent.getEventTime(), MotionEvent.ACTION_CANCEL, paramMotionEvent.getX(0),
                        paramMotionEvent.getY(0), paramMotionEvent.getMetaState());
                paramMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
            } else {
                mMove = (int) Math.abs(y - paramMotionEvent.getY());
            }
            if (!mTouchEvent && mPading == -HEADPADDING_MAX_LENGHT) {
                try {
                    super.dispatchTouchEvent(paramMotionEvent);
                } catch (Exception e) {
                }
            } else if (mMove > 50.0) {
                MotionEvent mCurrentMotionEvent = paramMotionEvent;
                mCurrentMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
                super.dispatchTouchEvent(paramMotionEvent);
            }
            break;
        case MotionEvent.ACTION_CANCEL:
            updateLayout();
            super.dispatchTouchEvent(paramMotionEvent);
            break;
        }
        return true;
    }

    int acY = 0;
    int acX = 0;

    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        boolean mScrollStatus = false;
        if (isSelectFirst && (Math.abs(paramFloat1) > 0 || (paramMotionEvent2.getY() >= acY && Math.abs(ActionDownTime - System.currentTimeMillis()) > timeDelay))) {
            
        } else {
            if ((int) Math.abs(ActionDownTime - System.currentTimeMillis()) <= timeDelay || paramMotionEvent2.getY() < acY) {
                isSelectFirst = false;
            }
            LogInfo.LogOut("onScroll");
            mScrollStatus = true;
            ListView listview = (ListView) getChildAt(1);
            float f = (float) (SCALE * paramFloat2);
            if (listview.getCount() != 0) {
                if ((listview.getFirstVisiblePosition() == 0) && (listview!=null&&listview.getChildAt(0)!=null&&listview.getChildAt(0).getTop() == 0)) {
                    mScrollStatus = true;
                } else {
                    mScrollStatus = false;
                }
            }
            if ((f >= 0.0F || !mScrollStatus) && (mPading <= -HEADPADDING_MAX_LENGHT)) {
                mScrollStatus = false;
            } else {
                mScrollStatus = move(f, false);
            }
        }
        return mScrollStatus;
    }

    private boolean move(float paramFloat, boolean paramBoolean) {
        // LogInfo.LogOut("move=" + paramFloat + "   state=" + mState +
        // "  paramBoolean" + paramBoolean);
        boolean mStateMove = false;
        if (paramFloat <= 0.0F || mPading > -HEADPADDING_MAX_LENGHT) {
            mPading = (int) (mPading - paramFloat);
            if (mPading < -HEADPADDING_MAX_LENGHT) {
                mPading = -HEADPADDING_MAX_LENGHT;
            }
            updateLayout();
            if (mState == STATE_REFRESH && mPading > 0) {
                mPading = 0;
            }
            if (paramBoolean) {
                if ((mDestPading != 0) || (mPading != 0)) {
                    if (mDestPading != -HEADPADDING_MAX_LENGHT) {
                    }
                } else {
                    if (mState != STATE_REFRESH) {
                        onRefresh();
                    }
                    mStateMove = true;
                }
            } else {
                if (mPading > -HEADPADDING_MAX_LENGHT && mState != STATE_REFRESH) {
                    mState = STATE_OPEN;
                    // LogInfo.LogOut("STATE_OPEN  mState=" + mState);
                } else if (mState != STATE_REFRESH) {
                    mState = STATE_CLOSE;
                    // LogInfo.LogOut("STATE_CLOSE   mState=" + mState);
                }
            }
            if (mState != STATE_REFRESH) {
                updateView();
            }
        } else {
            mPading = -HEADPADDING_MAX_LENGHT;
            mStateMove = false;
        }
        updateLayout();
        return mStateMove;
    }

    private void updateLayout() {
        View head = getChildAt(0);
        View list = getChildAt(1);
        int j = head.getTop();
        int i = list.getTop();
        head.offsetTopAndBottom(mPading - j);
        list.offsetTopAndBottom(HEADPADDING_MAX_LENGHT + mPading - i);
        invalidate();
    }

    protected void onFinishInflate() {
        LogInfo.LogOut("onFinishInflate");
        super.onFinishInflate();
        mListView = (ListView) getChildAt(1);
        ll = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.empty_main, null);
        foot = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_load_more, null);
        ll.addView(foot);
        more = (TextView) foot.findViewById(R.id.loadmoretextview);
        mProgressBar2 = (ProgressBar) foot.findViewById(R.id.loadmorebar);
        if (mListView == null) {
            LogInfo.LogOut("out------------------>");
        }
        if (isAutoLoadMore) {
            // foot.setVisibility(View.INVISIBLE);
        }
        mListView.addFooterView(ll);
        foot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                if (isAutoLoadMore || mState == STATE_REFRESH) {
                    return;
                }
                if(Math.abs(System.currentTimeMillis() - l) > 500){
                    l=System.currentTimeMillis();
                    onLoadMore();
                }
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
                LogInfo.LogOut("mListView.setOnScrollListener");
                if (mRefreshScrollListener != null) {
                    mRefreshScrollListener.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
                }
                if (isAutoLoadMore) {
                    if (paramInt1 + paramInt2 < paramInt3 - 1) {
                        isEnd = false;
                    } else {
                        isEnd = true;
                    }
                }
            }

            public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
                LogInfo.LogOut("mListView.onScrollStateChanged");
                if (mRefreshScrollListener != null) {
                    mRefreshScrollListener.onScrollStateChanged(paramAbsListView, paramInt);
                }
                if (!isEnd || paramInt != 0 || !hasMore || !isAutoLoadMore || isNoMore || mState == STATE_REFRESH) {
                    return;
                }
                onLoadMore();
            }
        });
    }

    public boolean onFling(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        LogInfo.LogOut("onFling");
        return false;
    }

    public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
        LogInfo.LogOut("onSingleTapUp");
        return false;
    }

    public boolean onDown(MotionEvent paramMotionEvent) {
        LogInfo.LogOut("onDown");
        acY = (int) paramMotionEvent.getY();
        acX = (int) paramMotionEvent.getX();
        if (acY < 255 &&mListView!=null&& mListView.getChildAt(0)!=null&&mListView.getChildAt(0).getTop() >= 0) {
            isSelectFirst = true;
            ActionDownTime = System.currentTimeMillis();
        } else {
            isSelectFirst = false;
        }
        return false;
    }

    public void onLongPress(MotionEvent paramMotionEvent) {

    }

    public void onShowPress(MotionEvent paramMotionEvent) {

    }

    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        int k = mPading;
        int j = getMeasuredWidth();
        int i = getMeasuredHeight();
        if (getChildCount() > 1) {
            getChildAt(0).layout(0, k, j, k + HEADPADDING_MAX_LENGHT);
            View list = getChildAt(1);
            if (list != null) {
                try {
                    list.layout(0, k + HEADPADDING_MAX_LENGHT, j, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        invalidate();
        LogInfo.LogOut("updateLayout", "   paramBoolean=" + paramBoolean);
    }

    class Flinger implements Runnable {
        private int mLastFlingX;
        private Scroller mScroller = new Scroller(getContext());

        public Flinger() {
        }

        private void startCommon() {
            removeCallbacks(this);
        }

        public void run() {
            LogInfo.LogOut("run");
            boolean bool = mScroller.computeScrollOffset();
            int j = mScroller.getCurrY();
            int i = j - mLastFlingX;
            if (mState == STATE_CLOSE) {
                bool = false;
            } else if (mState == STATE_COMPLETE) {
                move(i, false);
            } else {
                move(i, true);
            }
            if (!bool) {
                if (mState == STATE_COMPLETE || mState == STATE_OPEN) {
                    mState = STATE_CLOSE;
                }
                // mIsAutoScroller = false;
                removeCallbacks(this);
            } else {
                mLastFlingX = j;
                post(this);
            }
        }

        public void startUsingDistance(int paramInt1, int paramInt2) {
            if (mState == STATE_REFRESH && mPading > -HEADPADDING_MAX_LENGHT) {
                return;
            }
            if (paramInt1 == 0)
                --paramInt1;
            startCommon();
            mLastFlingX = 0;
            mScroller.startScroll(0, 0, 0, paramInt1, paramInt2);
            // mIsAutoScroller = true;
            post(this);
        }
    }

    public static abstract interface OnRefreshScrollListener {
        public abstract void onScrollStateChanged(AbsListView paramAbsListView, int paramInt);

        public abstract void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3);
    }

    public static abstract interface OnRefreshListener {
        public abstract void onLoadMore();

        public abstract void onRefresh();
    }
}