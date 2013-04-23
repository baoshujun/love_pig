package com.lovepig.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 用于下拉回弹
 * 
 * @author DCH
 * 
 */
public class TlcyScrollView extends ScrollView {
    private View inner;
    private float y;
    private float SCALE = 0.3F;// 灵敏度
    private Rect normal = new Rect();;

    public TlcyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner == null) {
            return super.onTouchEvent(ev);
        } else {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
            y = ev.getY() * SCALE;
            break;
        case MotionEvent.ACTION_UP:
            if (isNeedAnimation()) {
                animation();
            }
            break;
        case MotionEvent.ACTION_MOVE:
            final float preY = y;
            float nowY = ev.getY() * SCALE;
            int deltaY = (int) (preY - nowY);
            // 滚动
            scrollBy(0, deltaY);
            y = nowY;
            // 当滚动到最上或者最下时就不会再滚动，这时移动布局
            if (isNeedMove()) {
                if (normal.isEmpty()) {
                    // 保存正常的布局位置
                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                }
                // 移动布局
                inner.layout(inner.getLeft(), inner.getTop() - deltaY, inner.getRight(), inner.getBottom() - deltaY);
            }
            break;
        default:
            break;
        }
    }

    /**
     * 开启动画移动
     */
    public void animation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(), normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    /**
     * 是否需要开启动画
     * 
     * @return
     */
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    /**
     * 是否需要移动布局
     * 
     * @return
     */
    public boolean isNeedMove() {
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;
    }

}
