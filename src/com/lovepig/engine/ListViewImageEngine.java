package com.lovepig.engine;

import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.lovepig.utils.LogInfo;
import com.lovepig.widget.TlcyListLayout;
import com.lovepig.widget.TlcyListLayout.OnRefreshScrollListener;
/**
 * 为了略过一闪而过的图片,listview使用此类获取图片
 *
 */
public class ListViewImageEngine implements OnScrollListener,OnRefreshScrollListener{
    TlcyListLayout listLayout;
    ListView listview;
    WeakReference<Bitmap> bitmapReference = null;
    public ListViewImageEngine(ListView listview,TlcyListLayout layout) {
        this.listLayout = layout;
        this.listview = listview;
        this.listLayout.setRefreshScrollListener(this);
    }
    public ListViewImageEngine(ListView listview) {
        this.listview = listview;
        this.listview.setOnScrollListener(this);
    }
    /**
     * 显示原图
     */
    public void imageLoader(ImageView v, String imageUrl, int id,int pos) {
        ImageEngine.setImageBitmap(imageUrl, v, id,pos);
    }
    /**
     * 显示缩略图
     */
    public void imageLoaderScale(ImageView v, String imageUrl, int id,int pos) {
        ImageEngine.setImageBitmapScale(imageUrl, v, id,pos);
    }

    // ---------------------------------------------------------------

    private void loadImage() {
        int start = listview.getFirstVisiblePosition();
        int end = listview.getLastVisiblePosition();
        if (end >= listview.getCount()) {
            end = listview.getCount() - 1;
        }
        ImageEngine.setLoadLimit(start, end);
        ImageEngine.unlock();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
        case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
            LogInfo.LogOut("list", "-------------SCROLL_STATE_FLING");
            ImageEngine.lock();
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
            LogInfo.LogOut("list", "-------------SCROLL_STATE_IDLE");
            loadImage();
            break;
        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:
            LogInfo.LogOut("list", "--------------SCROLL_STATE_TOUCH_SCROLL");
            ImageEngine.lock();
            break;

        default:
            break;
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
