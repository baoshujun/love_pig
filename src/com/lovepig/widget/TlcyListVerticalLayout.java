package com.lovepig.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.utils.LogInfo;

/**
 * 此layout 只能添加一个listview,竖直方向的下拉刷新
 * 
 * 
 */
public class TlcyListVerticalLayout extends FrameLayout implements GestureDetector.OnGestureListener {
    private static final int STATE_CLOSE = -1;
    private static final int STATE_HEADER_OPEN = 0;
    private static final int STATE_FOOTER_OPEN = 1;
    private static final int STATE_COMPLETE = 2;//
    private static final int STATE_HEADER_REFRESH = 3;// 正在更新或者加载更多
    private static final int STATE_FOOTER_LOADMORE = 4;// 正在更新或者加载更多
    private static final int DIRECTION_OF_RATATION = 180;// 旋转方向
    private static final int DURATION_OF_RATATION = 250;// 动画持续时间
    private static final int AUTO_MOVE_DURATION = 300;
    private static int HEADPADDING_MAX_LENGHT = 0;
    private static final float SCALE = 0.4f;// 下拉灵敏度
    private int mState = STATE_CLOSE;// 状态
    private GestureDetector mDetector;
    private FrameLayout mUpdateContent;
    private RotateAnimation mAnimationUp;// 向上动画
    private RotateAnimation mAnimationDown;// 向下动画
    private OnTlcyListVerticalListener mListener;
    private ListView mListView;
    /**
     * 是否禁止顶部下拉刷新 可以联网的时候 0 表示都无法拉动 1 表示都可以联网 2表示只能向下刷新 3表示只能加载更多
     */
    private int UnPull = 1;
    private int MinMoreSize = 2;// 最小条数，小于该值无法加载更多
    // private int MinNewSize = 1;// 最小条数，小于该值无法加载更多
    /**
     * 获取最新
     */
    private View mHeaderView;
    private ProgressBar mProgressBar;
    private String mHeader_Refreshing;
    private String mHeader_Default;
    private String mHeader_CanRefresh;
    private TextView mRefreshText;
    private Flinger mFlinger;
    private int mLastPading;
    private int mDestPading;
    private int mHeaderPadding;
    private ImageView mArrowHeader;
    /**
     * 加载更多
     */
    private String mFooter_LoadingMore = "正在加载•••";
    private String mFooter_DefaultString = "向上拖动加载•••";
    private String mFooter_CanLoadmore = "释放立即加载•••";
    private View mFooterView;
    private ImageView mArrowFooter;
    private TextView mLoadmoreText;
    private ProgressBar mProgressLoadmore;
    private int mFooterPadding;
    private int mLastFooterPading;
    private FlingerFooter mFlingerFooter;

    public TlcyListVerticalLayout(Context context) {
        super(context);
        mDetector = new GestureDetector(context, this);
        mFlinger = new Flinger();
        mFlingerFooter = new FlingerFooter();
        init();
        initAnimation();
        addUpdateBar();
    }

    public TlcyListVerticalLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        mDetector = new GestureDetector(context, this);
        mFlinger = new Flinger();
        mFlingerFooter = new FlingerFooter();
        init();
        initAnimation();
        addUpdateBar();
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
        mFooterPadding = 0;
        mHeader_Default = getContext().getText(R.string.drop_dowm).toString();
        mHeader_CanRefresh = getContext().getText(R.string.release_update).toString();
        mHeader_Refreshing = getContext().getText(R.string.doing_update).toString();
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

    private void addUpdateBar() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        mHeaderView = LayoutInflater.from(getContext()).inflate(R.layout.widget_list_refresh_header, null);
        addView(mHeaderView);
        mHeaderView.setFocusable(false);
        mUpdateContent = (FrameLayout) mHeaderView.findViewById(R.id.iv_content);
        mArrowHeader = new ImageView(getContext());
        mArrowHeader.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mArrowHeader.setLayoutParams(layoutParams);
        mArrowHeader.setImageResource(R.drawable.arrow_down);
        mUpdateContent.addView(mArrowHeader);
        layoutParams.gravity = Gravity.CENTER;
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        int iPadding = getResources().getDimensionPixelSize(R.dimen.updatebar_padding);
        mProgressBar.setPadding(iPadding, iPadding, iPadding, iPadding);
        mProgressBar.setLayoutParams(layoutParams);
        mUpdateContent.addView(mProgressBar);
        mRefreshText = (TextView) findViewById(R.id.tv_title);
    }

    private void addLoadMoreBar() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
        mFooterView = LayoutInflater.from(getContext()).inflate(R.layout.widget_list_refresh_header, null);
        addView(mFooterView);
        mFooterView.setFocusable(false);
        FrameLayout mUpdateContent = (FrameLayout) mFooterView.findViewById(R.id.iv_content);
        mArrowFooter = new ImageView(getContext());
        mArrowFooter.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mArrowFooter.setLayoutParams(layoutParams);
        mArrowFooter.setImageResource(R.drawable.arrow_up);
        mUpdateContent.addView(mArrowFooter);
        layoutParams.gravity = 17;
        mProgressLoadmore = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleSmall);
        int iPadding = getResources().getDimensionPixelSize(R.dimen.updatebar_padding);
        mProgressLoadmore.setPadding(iPadding, iPadding, iPadding, iPadding);
        mProgressLoadmore.setLayoutParams(layoutParams);
        mUpdateContent.addView(mProgressLoadmore);
        mLoadmoreText = (TextView) mFooterView.findViewById(R.id.tv_title);
    }

    /**
     * 初始化当前ListView
     */
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

    @Override
    protected void onFinishInflate() {
        if (Configs.isDebug) {
            LogInfo.LogOut("onFinishInflate");
        }
        super.onFinishInflate();
        addLoadMoreBar();
        initContentView();
    }

    /**
     * 改变文字 向上拉刷新，向下更多
     */
    public void changeText() {
        MinMoreSize = 0;
        mHeader_Default = "向下拖动加载•••";
        mHeader_CanRefresh = mFooter_CanLoadmore;
        mHeader_Refreshing = mFooter_LoadingMore;

        mFooter_LoadingMore = getContext().getText(R.string.doing_update).toString();
        mFooter_CanLoadmore = getContext().getText(R.string.release_update).toString();
        mFooter_DefaultString = "向上拖动刷新•••";
    }

    /**
     * 
     * @param defaulttip
     *            初始状态文字
     * @param canrefresh
     *            顶部释放后可以刷新的提示文字
     * @param refreshing
     *            顶部正在刷新的文字
     */
    public void setHeaderText(String defaulttip, String canrefresh, String refreshing) {
        if (!TextUtils.isEmpty(defaulttip)) {
            mHeader_Default = defaulttip;
        }
        if (!TextUtils.isEmpty(canrefresh)) {
            mHeader_CanRefresh = canrefresh;
        }
        if (!TextUtils.isEmpty(refreshing)) {
            mHeader_Refreshing = refreshing;
        }
    }

    /**
     * 
     * @param defaulttip
     *            底部初始文字
     * @param canloadmore
     *            底部释放后可以加载的提示文字
     * @param loadingmore
     *            底部正在加载的文字
     */
    public void setFooterText(String defaulttip, String canloadmore, String loadingmore) {
        if (!TextUtils.isEmpty(defaulttip)) {
            mFooter_DefaultString = defaulttip;
        }
        if (!TextUtils.isEmpty(canloadmore)) {
            mFooter_CanLoadmore = canloadmore;
        }
        if (!TextUtils.isEmpty(loadingmore)) {
            mFooter_LoadingMore = loadingmore;
        }
    }

    /**
     * 设置监听
     * 
     * @param onTlcyListVerticalListener
     */
    public void setOnTlcyListVerticalListener(OnTlcyListVerticalListener onTlcyListVerticalListener) {
        mListener = onTlcyListVerticalListener;
    }

    /**
     * 设置文字颜色
     * 
     * @param colors
     */
    public void setTextColor(int colors) {
        mRefreshText.setTextColor(colors);
        mLoadmoreText.setTextColor(colors);
    }

    /**
     * 触发刷新
     */
    private void onRefresh() {
        mState = STATE_HEADER_REFRESH;
        mRefreshText.setText(mHeader_Refreshing);
        mProgressBar.setVisibility(View.VISIBLE);
        mArrowHeader.clearAnimation();
        mArrowHeader.setVisibility(View.INVISIBLE);
        if (mListener == null) {
            return;
        }
        mListener.onRefresh();
    }

    /**
     * 触发加载更多
     */
    private void onLoadMore() {
        mState = STATE_FOOTER_LOADMORE;
        mProgressLoadmore.setVisibility(View.VISIBLE);
        mArrowFooter.clearAnimation();
        mArrowFooter.setVisibility(View.INVISIBLE);
        mLoadmoreText.setText(mFooter_LoadingMore);
        if (mListener == null) {
            return;
        }
        mListener.onLoadMore();
    }

    /**
     * 获取最新/加载更多完成
     */
    public void onComplete() {
        if (mState == STATE_FOOTER_LOADMORE) {
            onLoadMoreComplete();
        } else if (mState == STATE_HEADER_REFRESH) {
            onRefreshComplete();
        }
    }

    /**
     * 刷新完成
     */
    private void onRefreshComplete() {
        mState = STATE_COMPLETE;
        mArrowHeader.setImageResource(R.drawable.arrow_down);
        scrollToClose();
        mDestPading = -HEADPADDING_MAX_LENGHT;
        mHeaderPadding = -HEADPADDING_MAX_LENGHT;
        mLastPading = -HEADPADDING_MAX_LENGHT;
        updateLayout();
    }

    /**
     * 加载更多完成
     */
    private void onLoadMoreComplete() {
        mState = STATE_COMPLETE;
        mFooterPadding = 0;
        mLastFooterPading = 0;
        mProgressLoadmore.setVisibility(View.GONE);
        mFlingerFooter.startUsingDistance(HEADPADDING_MAX_LENGHT, AUTO_MOVE_DURATION);
        invalidate();
    }

    private boolean releaseHeader() {
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
            mFlingerFooter.startUsingDistance(HEADPADDING_MAX_LENGHT, AUTO_MOVE_DURATION * 2);
        } else {
            mFlingerFooter.startUsingDistance(mFooterPadding, AUTO_MOVE_DURATION + (int) (mFooterPadding * 3.2));
        }
        updateLayout();
        return false;
    }

    /**
     * 是否禁止顶部下拉刷新:0表示都无法拉动 ,1 表示都可以拉动, 2表示只能向下刷新, 3表示只能加载更多
     */
    public void setUnPull(int unPull) {
        if (unPull <= 0) {
            unPull = 0;
        }
        this.UnPull = unPull;
    }

    /**
     * 更新完成自动复位
     */
    private void scrollToClose() {
        mFlinger.startUsingDistance(HEADPADDING_MAX_LENGHT, AUTO_MOVE_DURATION);
    }

    /**
     * 拉动复位
     */
    private void scrollToUpdate() {
        mFlinger.startUsingDistance(mHeaderPadding, AUTO_MOVE_DURATION + (int) (mHeaderPadding * 3.2));
    }

    /**
     * 上部动画以及更新文字
     */
    private void updateHeaderView() {
        if (mHeaderPadding > 0) {
            mRefreshText.setText(mHeader_CanRefresh);
            mProgressBar.setVisibility(View.INVISIBLE);
            mArrowHeader.setVisibility(View.VISIBLE);
            if (mLastPading <= 0) {
                mArrowHeader.clearAnimation();
                mArrowHeader.startAnimation(mAnimationUp);
            }
        } else if (mHeaderPadding < 0) {
            mArrowHeader.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshText.setText(mHeader_Default);
            if (mLastPading >= 0) {
                mArrowHeader.clearAnimation();
                mArrowHeader.startAnimation(mAnimationDown);
            }
        }
        mLastPading = mHeaderPadding;
    }

    /**
     * 下部动画以及更新文字
     */
    private void updateFootView() {
        if (mFooterPadding > HEADPADDING_MAX_LENGHT) {
            mLoadmoreText.setText(mFooter_CanLoadmore);
            mProgressLoadmore.setVisibility(View.INVISIBLE);
            mArrowFooter.setVisibility(View.VISIBLE);
            if (mLastFooterPading <= HEADPADDING_MAX_LENGHT) {
                mArrowFooter.clearAnimation();
                mArrowFooter.startAnimation(mAnimationUp);
            }
        } else if (mFooterPadding < HEADPADDING_MAX_LENGHT) {
            mLoadmoreText.setText(mFooter_DefaultString);
            mArrowFooter.setVisibility(View.VISIBLE);
            mProgressLoadmore.setVisibility(View.INVISIBLE);
            if (mLastFooterPading >= HEADPADDING_MAX_LENGHT) {
                mArrowFooter.clearAnimation();
                mArrowFooter.startAnimation(mAnimationDown);
            }
        }
        mLastFooterPading = mFooterPadding;
    }

    int y = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int mMotionEventAction = event.getAction();
        boolean mTouchEvent = mDetector.onTouchEvent(event);
        switch (mMotionEventAction) {
        case MotionEvent.ACTION_DOWN:
            if (event.getPointerCount() >= 2) {
                event = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_CANCEL, event.getX(0), event.getY(0), event.getMetaState());
                event.setAction(MotionEvent.ACTION_CANCEL);
            } else {
                y = (int) event.getY();
            }
            updateLayout();
            try {
                super.dispatchTouchEvent(event);
            } catch (Exception e) {
            }
            break;
        case MotionEvent.ACTION_UP:
            if (mHeaderPadding != -HEADPADDING_MAX_LENGHT && mState != STATE_CLOSE) {
                releaseHeader();
            } else if (mFooterPadding != 0 && mState != STATE_CLOSE) {
                releaseFooter();
            }
            updateLayout();
            super.dispatchTouchEvent(event);
            break;
        case MotionEvent.ACTION_MOVE:
            updateLayout();
            int mMove = y;
            if (event.getPointerCount() >= 2) {
                event = MotionEvent.obtain(event.getDownTime(), event.getEventTime(), MotionEvent.ACTION_CANCEL, event.getX(0), event.getY(0), event.getMetaState());
                event.setAction(MotionEvent.ACTION_CANCEL);
            } else {
                mMove = (int) Math.abs(y - event.getY());
            }
            if (!mTouchEvent && mHeaderPadding == -HEADPADDING_MAX_LENGHT && mFooterPadding == 0) {
                try {
                    super.dispatchTouchEvent(event);
                } catch (Exception e) {
                }
            } else if (mMove > 50.0) {
                MotionEvent mCurrentMotionEvent = event;
                mCurrentMotionEvent.setAction(MotionEvent.ACTION_CANCEL);
                super.dispatchTouchEvent(event);
            }
            break;
        case MotionEvent.ACTION_CANCEL:
            updateLayout();
            super.dispatchTouchEvent(event);
            break;
        }
        return true;
    }

    public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Configs.isDebug) {
            LogInfo.LogOut("onScroll");
        }
        // if (UnPull&&!isHasMore) {
        // return false;
        // }
        float f = SCALE * velocityY;
        if (mListView != null && mListView.getCount() != 0) {
            if (Configs.isDebug) {
                LogInfo.LogOut("" + getMeasuredHeight());
            }
            if (f <= 0 && mState == STATE_CLOSE && (UnPull == 1 || UnPull == 2) && mListView != null && mListView.getFirstVisiblePosition() == 0 && mListView.getChildAt(0) != null
                    && mListView.getChildAt(0).getTop() == 0) {
                moveHeader(f, false);
            } else if (mState == STATE_HEADER_OPEN || mState == STATE_HEADER_REFRESH) {
                moveHeader(f, false);
            }
            if (f >= 0 && mState == STATE_CLOSE && (UnPull == 1 || UnPull == 3) && mListView != null && mListView.getLastVisiblePosition() == mListView.getCount() - 1
                    && mListView.getCount() >= MinMoreSize) {
                View lastView = mListView.getChildAt(mListView.getChildCount() - 1);
                if (lastView == null) {
                    return false;
                }
                if (lastView.getBottom() <= getHeight()) {
                    moveFooter(f, false);
                }
            } else if (mState == STATE_FOOTER_OPEN || mState == STATE_FOOTER_LOADMORE) {
                moveFooter(f, false);
            }
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Configs.isDebug) {
            LogInfo.LogOut("onFling");
        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent paramMotionEvent) {
        if (Configs.isDebug) {
            LogInfo.LogOut("onSingleTapUp");
        }
        return false;
    }

    @Override
    public boolean onDown(MotionEvent paramMotionEvent) {
        if (Configs.isDebug) {
            LogInfo.LogOut("onDown");
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    private boolean moveHeader(float deltaY, boolean isAuto) {
        if (mState == STATE_FOOTER_LOADMORE) {
            return false;
        }
        if (deltaY <= 0.0F || mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
            mHeaderPadding = (int) (mHeaderPadding - deltaY);
            if (mHeaderPadding < -HEADPADDING_MAX_LENGHT) {
                mHeaderPadding = -HEADPADDING_MAX_LENGHT;
            }
            if (mState == STATE_HEADER_REFRESH && mHeaderPadding > 0) {
                mHeaderPadding = 0;
            }
            updateLayout();
            if (isAuto) {
                if (mDestPading == 0 && mHeaderPadding == 0 && mState != STATE_HEADER_REFRESH) {
                    onRefresh();
                }
                // if (mDestPading != 0 || mHeaderPadding != 0) {
                // } else {
                // if (mState != STATE_REFRESH) {
                // onRefresh();
                // }
                // mStateMove = true;
                // }
            } else if (mState != STATE_HEADER_REFRESH) {
                if (mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
                    mState = STATE_HEADER_OPEN;
                } else {
                    mState = STATE_CLOSE;
                }
            }
            if (mState != STATE_HEADER_REFRESH) {
                updateHeaderView();
            }
        } else {
            mHeaderPadding = -HEADPADDING_MAX_LENGHT;
        }
        updateLayout();
        return false;
    }

    private boolean moveFooter(float deltaY, boolean isAuto) {
        boolean mStateMove = false;
        if (mState == STATE_HEADER_REFRESH) {
            return false;
        }
        mFooterPadding = (int) (mFooterPadding + deltaY);
        if (mFooterPadding <= 0) {
            mFooterPadding = 0;
        }
        if (mFooterPadding > HEADPADDING_MAX_LENGHT && mState == STATE_FOOTER_LOADMORE) {
            mFooterPadding = HEADPADDING_MAX_LENGHT;
        }
        if (isAuto) {
            if (mFooterPadding == HEADPADDING_MAX_LENGHT && mState != STATE_FOOTER_LOADMORE) {
                onLoadMore();
            }
        } else if (mState != STATE_FOOTER_LOADMORE) {
            if (mFooterPadding > 0) {
                mState = STATE_FOOTER_OPEN;
            } else {
                mState = STATE_CLOSE;
            }
        }
        if (mState != STATE_FOOTER_LOADMORE) {
            updateFootView();
        }
        updateLayout();
        return mStateMove;
    }

    private void updateLayout() {
        int j = mHeaderView.getTop();
        int i = mListView.getTop();
        int f = mFooterView.getTop();
        mHeaderView.offsetTopAndBottom(mHeaderPadding - j);
        mListView.offsetTopAndBottom(HEADPADDING_MAX_LENGHT + mHeaderPadding - i - mFooterPadding);
        mFooterView.offsetTopAndBottom(getHeight() - mFooterPadding - f + HEADPADDING_MAX_LENGHT + mHeaderPadding);
        if (Configs.isDebug) {
            LogInfo.LogOut("updateFooterLayout", "height = " + getHeight() + "f=" + mFooterView.getTop() + " mHeaderPadding=" + mHeaderPadding + " mFooterPadding="
                    + mFooterPadding + " listtop=" + mListView.getTop());
        }
        invalidate();
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
        if (Configs.isDebug) {
            LogInfo.LogOut("updateLayout", "   paramBoolean=" + paramBoolean);
        }
    }

    class Flinger implements Runnable {
        private int mLastFlingY;

        private Scroller mScroller = new Scroller(getContext());

        private void startCommon() {
            removeCallbacks(this);
        }

        public void run() {
            if (Configs.isDebug) {
                LogInfo.LogOut("run");
            }
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
                if (mState == STATE_COMPLETE || mState == STATE_HEADER_OPEN) {
                    mState = STATE_CLOSE;
                }
                removeCallbacks(this);
            } else {
                mLastFlingY = mCurrY;
                post(this);
            }
        }

        private void startUsingDistance(int deltaY, int duration) {
            if (mState == STATE_HEADER_REFRESH && mHeaderPadding > -HEADPADDING_MAX_LENGHT) {
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
                if (mState == STATE_COMPLETE || mState == STATE_FOOTER_OPEN) {
                    mState = STATE_CLOSE;
                }
                removeCallbacks(this);
            } else {
                mLastFlingY = mCurrY;
                post(this);
            }
        }

        private void startUsingDistance(int deltaY, int duration) {
            if (mState == STATE_FOOTER_LOADMORE && mFooterPadding > 0) {
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

    public abstract interface OnTlcyListVerticalListener {
        public void onRefresh();

        public void onLoadMore();
    }
    
    public int getState(){
        return mState;
    }
}