package com.lovepig.widget;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.lovepig.main.R;
import com.lovepig.widget.TlcyListVerticalLayout.OnTlcyListVerticalListener;

public class BaseVerticalListView {
    private TlcyListVerticalLayout mPullLayout;
    private RelativeLayout vertical_layout;
    private ListView mListView;
    private Activity activity;
    private View mView;
    private onVerticalListViewListener mListener;

    /**
     * 单个ListView
     * 
     * @param activity
     */
    public BaseVerticalListView(Activity activity) {
        this.activity = activity;
        initActivity();
        setVericalLayoutVisibility(View.VISIBLE);
    }

    /**
     * 多个ListView
     * 
     * @param mView
     */
    public BaseVerticalListView(View mView) {
        this.mView = mView;
        initView();
        setVericalLayoutVisibility(View.VISIBLE);
    }

    private void initActivity() {
        if (activity != null) {
            vertical_layout = (RelativeLayout) activity.findViewById(R.id.vertical_layout);
            mPullLayout = (TlcyListVerticalLayout) activity.findViewById(R.id.vertical_list_layout);
            mListView = (ListView) activity.findViewById(R.id.vertical_list);
            initListener();
        }
    }

    private void initView() {
        if (mView != null) {
            vertical_layout = (RelativeLayout) mView.findViewById(R.id.vertical_layout);
            mPullLayout = (TlcyListVerticalLayout) mView.findViewById(R.id.vertical_list_layout);
            mListView = (ListView) mView.findViewById(R.id.vertical_list);
            initListener();
        }
    }

    private void initListener() {
        if (mPullLayout != null) {
            mPullLayout.setOnTlcyListVerticalListener(new OnTlcyListVerticalListener() {
                @Override
                public void onRefresh() {
                    if (mListener == null) {
                        throw new NullPointerException("please set listener");
                    } else {
                        mListener.onRefresh();
                    }
                }

                @Override
                public void onLoadMore() {
                    if (mListener == null) {
                        throw new NullPointerException("please set listener");
                    } else {
                        mListener.onLoadMore();
                    }
                }
            });
        }
    }

    public ListView getListView() {
        if (mListView == null) {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
        return mListView;
    }

    /**
     * 设置是否可见
     * 
     * @param visible
     */
    public void setVericalLayoutVisibility(int visible) {
        if (vertical_layout != null) {
            vertical_layout.setVisibility(visible);
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
    }

    /**
     * 数据获取完成后调用
     */
    public void onComplete() {
        if (mPullLayout != null) {
            mPullLayout.onComplete();
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
    }

    /**
     * 设置拉动监听
     * 
     * @param mListener
     */
    public void setOnVertivalListViewListener(onVerticalListViewListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 0上下都无法拉动，1上下都可以拉动，2仅可以下拉刷新，3仅可以上拉加载更多
     * 
     * @param type
     */
    public void setPullType(int type) {
        if (mPullLayout != null) {
            mPullLayout.setUnPull(type);
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
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
        if (mPullLayout != null) {
            mPullLayout.setHeaderText(defaulttip, canrefresh, refreshing);
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
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
        if (mPullLayout != null) {
            mPullLayout.setFooterText(defaulttip, canloadmore, loadingmore);
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
    }

    /**
     * 设置字体颜色
     * 
     * @param colors
     */
    public void setTextColor(int colors) {
        if (mPullLayout != null) {
            mPullLayout.setTextColor(colors);
        } else {
            throw new NullPointerException("Not Initialization Layout, Please includ res/layout/verticallistview.xml");
        }
    }

    /**
     * 拉动监听接口
     * 
     * @author DCH
     * 
     */
    public interface onVerticalListViewListener {
        /**
         * 顶部刷新
         */
        public void onRefresh();

        /**
         * 底部加载
         */
        public void onLoadMore();
    }
}
