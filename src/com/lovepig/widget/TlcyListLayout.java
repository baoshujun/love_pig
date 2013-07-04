package com.lovepig.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
public class TlcyListLayout extends FrameLayout implements GestureDetector.OnGestureListener {
    private static final int STATE_CLOSE = -1;
    private static final int STATE_OPEN = 1;
    private static final int STATE_UP_REFRESH = 2;// 正在更新或者加载更多
    private static final int STATE_DOWN_LOAD = 3;// 正在更新或者加载更多
    private static final int STATE_COMPLETE = 4;//
    private static final int DIRECTION_OF_RATATION = 180;// 旋转方向
    private static final int DURATION_OF_RATATION = 250;// 动画持续时间
    private static final int CLOSEDELAY = 300;
    private static final int REFRESHDELAY = 300;
    private static int HEADPADDING_MAX_LENGHT = 0;
    private static final double SCALE = 0.4D;// 下拉灵敏度
    private String loading;
    private String mDate;
    private String pulldowntorefresh;
    private String releasetorefresh;
    private String PushUpToLoadMore = "上拉可以加载更多";
    private String ReleaseToLoadingMore = "松开可以加载更多";
    private String update_time;
    private int mDestPading;
    private int mLastPading;
    private int mLastFooterPading;
    private int mHeaderPadding;
    private int mFooterPadding;
    private int mState = STATE_CLOSE;// 状态
    private GestureDetector mDetector;
    private Flinger mFlinger;
    private FlingerFooter mFlingerFooter;
    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private ImageView mArrow;
    private ImageView mFootArrow;
    private ListView mListView;
    private View mHeaderView;
    private View mFooterView;

    private TextView mTitle;
    private TextView more;
    private FrameLayout mUpdateContent;
    private RotateAnimation mAnimationUp;// 向上动画
    private RotateAnimation mAnimationDown;// 向下动画
    private OnRefreshListener mRefreshListener;
    private OnRefreshScrollListener mRefreshScrollListener;// 滚动条事件
    private boolean UnPull = false;// 为true的时候不允许顶部拖拽
    private boolean isHasMore = true;

    public TlcyListLayout(Context paramContext) {
        super(paramContext);
        mDetector = new GestureDetector(paramContext, this);
        mFlinger = new Flinger();
        mFlingerFooter = new FlingerFooter();
        init();
        addUpdateBar();
    }

    public TlcyListLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        mDetector = new GestureDetector(this);
        mFlinger = new Flinger();
        mFlingerFooter = new FlingerFooter();
        init();
        addUpdateBar();
    }

    private void addUpdateBar() {
        initAnimation();
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.list_refresh_header, null);
        addView(mHeaderView);
        mHeaderView.setFocusable(false);
        mUpdateContent = (FrameLayout) mHeaderView.findViewById(R.id.iv_content);
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

    private void addLoadMoreBar() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.list_refresh_header, null);
        addView(mFooterView);
        mFooterView.setFocusable(false);
        FrameLayout mUpdateContent = (FrameLayout) mFooterView.findViewById(R.id.iv_content);
        mFootArrow = new ImageView(getContext());
        mFootArrow.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mFootArrow.setLayoutParams(layoutParams);
        mFootArrow.setImageResource(R.drawable.arrow_up);
        mUpdateContent.addView(mFootArrow);
        layoutParams.gravity = 17;
        mProgressBar2 = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        int iPadding = getResources().getDimensionPixelSize(R.dimen.updatebar_padding);
        mProgressBar2.setPadding(iPadding, iPadding, iPadding, iPadding);
        mProgressBar2.setLayoutParams(layoutParams);
        mUpdateContent.addView(mProgressBar2);
        more = (TextView) mFooterView.findViewById(R.id.tv_title);
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
        mHeaderPadding = -HEADPADDING_MAX_LENGHT;
        mLastPading = -HEADPADDING_MAX_LENGHT;
        pulldowntorefresh = getContext().getText(R.string.drop_dowm).toString();
        releasetorefresh = getContext().getText(R.string.release_update).toString();
        loading = getContext().getText(R.string.doing_update).toString();
        update_time = getContext().getText(R.string.update_time).toString();
    }

    @Override
    protected void onFinishInflate() {
        LogInfo.LogOut("onFinishInflate");
        super.onFinishInflate();
        addLoadMoreBar();
        initContentView();
        if (mListView == null) {
            LogInfo.LogOut("out------------------>");
        }
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
                if (mRefreshScrollListener != null) {
                    mRefreshScrollListener.onScroll(paramAbsListView, paramInt1, paramInt2, paramInt3);
                }
            }

            public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
                if (mRefreshScrollListener != null) {
                    mRefreshScrollListener.onScrollStateChanged(paramAbsListView, paramInt);
                }
            }
        });
    }

    private void initContentView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException("this layout must contain 3 child views,and AdapterView  must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mListView = (ListView) view;
            }
        }
    }

    /**
     * 是否禁止顶部下拉刷新
     */
    public void setUnPull(boolean unPull) {
        UnPull = unPull;
    }

    public void setRefreshListener(OnRefreshListener paramOnRefreshListener) {
        mRefreshListener = paramOnRefreshListener;
    }

    public void setRefreshScrollListener(OnRefreshScrollListener mScrollListener) {
        mRefreshScrollListener = mScrollListener;
    }

    private void onLoadMore() {
        mState = STATE_DOWN_LOAD;
        mProgressBar2.setVisibility(View.VISIBLE);
        mFootArrow.clearAnimation();
        mFootArrow.setVisibility(View.INVISIBLE);
        more.setText(loading);
        if (mRefreshListener == null) {
            return;
        }
        mRefreshListener.onLoadMore();
    }

    private void onRefresh() {
        mState = STATE_UP_REFRESH;
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
        scrollToClose();
        mDestPading = -HEADPADDING_MAX_LENGHT;
        mHeaderPadding = -HEADPADDING_MAX_LENGHT;
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
        // onRefreshComplete();
        mFooterPadding = 0;
        mLastFooterPading = 0;
        // more.setText(R.string.More);
        mState = STATE_CLOSE;
        mProgressBar2.setVisibility(View.GONE);
        invalidate();
    }

    public void setLoadMoreButton(boolean isHasMore) {
        this.isHasMore = isHasMore;
    }

    /**
     * 重置加载更多的位置
     */
    public void onReSetLoadMorePix() {
        mFooterPadding = 0;
        mLastFooterPading = 0;
        invalidate();
        updateLayout();
    }

    private boolean release() {
        if (mHeaderPadding <= 0) {
            scrollToClose();
            mDestPading = -HEADPADDING_MAX_LENGHT;
        } else {
            scrollToUpdate();
            mDestPading = 0;
        }
        updateLayout();
        return false;
    }

    private boolean releaseFooter() {
        if (mFooterPadding <= HEADPADDING_MAX_LENGHT) {
            mFlingerFooter.startUsingDistance(HEADPADDING_MAX_LENGHT, CLOSEDELAY);
        } else {
            mFlingerFooter.startUsingDistance(mFooterPadding, REFRESHDELAY);
        }
        updateLayout();
        return false;
    }

    private void scrollToClose() {
        mFlinger.startUsingDistance(HEADPADDING_MAX_LENGHT, CLOSEDELAY);
    }

    private void scrollToUpdate() {
        mFlinger.startUsingDistance(mHeaderPadding, REFRESHDELAY);
    }

    private void updateView() {
        String str;
        if (mDate != null) {
            str = update_time + mDate;
        } else {
            str = "";
        }
        if (mHeaderPadding > 0) {
            mTitle.setText(releasetorefresh + "\n" + str);
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrow.setVisibility(View.VISIBLE);
            if (mLastPading <= 0) {
                mArrow.clearAnimation();
                mArrow.startAnimation(mAnimationUp);
            }
        } else if (mHeaderPadding < 0) {
            mArrow.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mTitle.setText(pulldowntorefresh + "\n" + str);
            if (mLastPading >= 0) {
                mArrow.clearAnimation();
                mArrow.startAnimation(mAnimationDown);
            }
        }
        mLastPading = mHeaderPadding;
    }

    private void updateFootView() {
        if (mFooterPadding > HEADPADDING_MAX_LENGHT) {
            more.setText(ReleaseToLoadingMore);
            mProgressBar2.setVisibility(View.INVISIBLE);
            mFootArrow.setVisibility(View.VISIBLE);
            if (mLastFooterPading <= HEADPADDING_MAX_LENGHT) {
                mFootArrow.clearAnimation();
                mFootArrow.startAnimation(mAnimationUp);
            }
        } else if (mFooterPadding < HEADPADDING_MAX_LENGHT) {
            more.setText(PushUpToLoadMore);
            mFootArrow.setVisibility(View.VISIBLE);
            mProgressBar2.setVisibility(View.INVISIBLE);
            if (mLastFooterPading >= HEADPADDING_MAX_LENGHT) {
                mFootArrow.clearAnimation();
                mFootArrow.startAnimation(mAnimationDown);
            }
        }
        mLastFooterPading = mFooterPadding;
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
            if (mHeaderPadding != -HEADPADDING_MAX_LENGHT && mState != STATE_CLOSE) {
                release();
            } else if (mFooterPadding != 0 && mState != STATE_CLOSE) {
                releaseFooter();
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
            if (!mTouchEvent && mHeaderPadding == -HEADPADDING_MAX_LENGHT && mFooterPadding == 0) {
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

    public boolean onScroll(MotionEvent paramMotionEvent1, MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
        if (UnPull) {
            return false;
        }
        LogInfo.LogOut("onScroll");
        float f = (float) (SCALE * paramFloat2);
        if (mListView.getCount() != 0) {
            LogInfo.LogOut("" + getMeasuredHeight());
            View lastView = mListView.getChildAt(mListView.getChildCount() - 1);
            if (mListView.getLastVisiblePosition() == mListView.getCount() - 1 && isHasMore) {
                if (lastView == null) {
                    return false;
                }
                if (lastView.getBottom() <= getHeight()) {
                    moveFooter(f, false);
                }
            } else if ((mListView.getFirstVisiblePosition() == 0) && (mListView != null && mListView.getChildAt(0) != null && mListView.getChildAt(0).getTop() == 0)) {
                moveHeader(f, false);
            }
        }
        return false;
    }

    private boolean moveFooter(float deltaY, boolean isAuto) {
        boolean mStateMove = false;
        if (mState == STATE_UP_REFRESH) {
            return false;
        }
        mFooterPadding = (int) (mFooterPadding + deltaY);
        if (mFooterPadding <= 0) {
            mFooterPadding = 0;
        }
        if (mFooterPadding > HEADPADDING_MAX_LENGHT && mState == STATE_DOWN_LOAD) {
            mFooterPadding = HEADPADDING_MAX_LENGHT;
        }
        if (isAuto) {
            if (mFooterPadding == HEADPADDING_MAX_LENGHT && mState != STATE_DOWN_LOAD) {
                onLoadMore();
            }
        } else if (mState != STATE_DOWN_LOAD) {
            if (mFooterPadding > 0) {
                mState = STATE_OPEN;
            } else {
                mState = STATE_CLOSE;
            }
        }
        if (mState != STATE_DOWN_LOAD) {
            updateFootView();
        }
        updateLayout();
        return mStateMove;
    }

    private boolean moveHeader(float deltaY, boolean isAuto) {
        if (mState == STATE_DOWN_LOAD) {
            return false;
        }
        if (deltaY <= 0.0F || mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
            mHeaderPadding = (int) (mHeaderPadding - deltaY);
            if (mHeaderPadding < -HEADPADDING_MAX_LENGHT) {
                mHeaderPadding = -HEADPADDING_MAX_LENGHT;
            }
            if (mState == STATE_UP_REFRESH && mHeaderPadding > 0) {
                mHeaderPadding = 0;
            }
            updateLayout();
            if (isAuto) {
                if (mDestPading == 0 && mHeaderPadding == 0 && mState != STATE_UP_REFRESH) {
                    onRefresh();
                }
                // if (mDestPading != 0 || mHeaderPadding != 0) {
                // } else {
                // if (mState != STATE_REFRESH) {
                // onRefresh();
                // }
                // mStateMove = true;
                // }
            } else if (mState != STATE_UP_REFRESH) {
                if (mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
                    mState = STATE_OPEN;
                } else {
                    mState = STATE_CLOSE;
                }
            }
            if (mState != STATE_UP_REFRESH) {
                updateView();
            }
        } else {
            mHeaderPadding = -HEADPADDING_MAX_LENGHT;
        }
        updateLayout();
        return false;
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mFooterPadding = 0;
    }

    private void updateLayout() {
        int j = mHeaderView.getTop();
        int i = mListView.getTop();
        int f = mFooterView.getTop();
        mHeaderView.offsetTopAndBottom(mHeaderPadding - j);
        mListView.offsetTopAndBottom(HEADPADDING_MAX_LENGHT + mHeaderPadding - i - mFooterPadding);
        mFooterView.offsetTopAndBottom(getHeight() - mFooterPadding - f + HEADPADDING_MAX_LENGHT + mHeaderPadding);
        LogInfo.LogOut("updateFooterLayout", "height = " + getHeight() + "f=" + mFooterView.getTop() + " mHeaderPadding=" + mHeaderPadding + " mFooterPadding=" + mFooterPadding
                + " listtop=" + mListView.getTop());
        invalidate();
        // requestLayout();
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
        return false;
    }

    public void onLongPress(MotionEvent paramMotionEvent) {

    }

    public void onShowPress(MotionEvent paramMotionEvent) {

    }

    @Override
    protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        int Width = getMeasuredWidth();
        int Height = getMeasuredHeight();
        if (getChildCount() > 1) {
            mHeaderView.layout(0, mHeaderPadding, Width, mHeaderPadding + HEADPADDING_MAX_LENGHT);
            mListView.layout(0, mHeaderPadding + HEADPADDING_MAX_LENGHT - mFooterPadding, Width, Height + mHeaderPadding + HEADPADDING_MAX_LENGHT - mFooterPadding);
            mFooterView.layout(0, Height - mFooterPadding, Width, HEADPADDING_MAX_LENGHT + Height - mFooterPadding);
        }
        invalidate();
        LogInfo.LogOut("updateLayout", "   paramBoolean=" + paramBoolean + "   mFooterPadding=" + mFooterPadding + ",Height=" + Height);
    }

    class FlingerFooter implements Runnable {
        private int mLastFlingY;
        private Scroller mScrollFooter = new Scroller(getContext());

        private void startCommon() {
            removeCallbacks(this);
        }

        public void run() {
            boolean mScroll = mScrollFooter.computeScrollOffset();
            int mCurrY = mScrollFooter.getCurrY();
            int velocityY = mCurrY - mLastFlingY;
            if (mState == STATE_CLOSE) {
                mScroll = false;
            } else if (mState == STATE_COMPLETE) {
                moveFooter(velocityY, false);
            } else {
                moveFooter(velocityY, true);
            }
            if (!mScroll) {
                if (mState == STATE_COMPLETE || mState == STATE_OPEN) {
                    mState = STATE_CLOSE;
                }
                removeCallbacks(this);
            } else {
                mLastFlingY = mCurrY;
                post(this);
            }
        }

        public void startUsingDistance(int deltaY, int duration) {
            if (mState == STATE_DOWN_LOAD && mFooterPadding > 0) {
                return;
            }
            if (deltaY == 0)
                --deltaY;
            startCommon();
            mLastFlingY = 0;
            if (deltaY > HEADPADDING_MAX_LENGHT) {
                deltaY = deltaY - HEADPADDING_MAX_LENGHT;
            }
            mScrollFooter.startScroll(0, 0, 0, -deltaY, duration);
            post(this);
        }
    }

    class Flinger implements Runnable {
        private int mLastFlingY;

        private Scroller mScroller = new Scroller(getContext());

        private void startCommon() {
            removeCallbacks(this);
        }

        public void run() {
            LogInfo.LogOut("run");
            boolean bool = mScroller.computeScrollOffset();
            int mCurrY = mScroller.getCurrY();
            int velocityY = mCurrY - mLastFlingY;
            if (mState == STATE_CLOSE) {
                bool = false;
            } else if (mState == STATE_COMPLETE) {
                moveHeader(velocityY, false);
            } else {
                moveHeader(velocityY, true);
            }
            if (!bool) {
                if (mState == STATE_COMPLETE || mState == STATE_OPEN) {
                    mState = STATE_CLOSE;
                }
                removeCallbacks(this);
            } else {
                mLastFlingY = mCurrY;
                post(this);
            }
        }

        public void startUsingDistance(int deltaY, int duration) {
            if (mState == STATE_UP_REFRESH && mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
                return;
            }
            if (deltaY == 0)
                --deltaY;
            startCommon();
            mLastFlingY = 0;
            mScroller.startScroll(0, 0, 0, deltaY, duration);
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