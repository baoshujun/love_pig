package com.lovepig.widget;

import com.lovepig.utils.LogInfo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/**
 * 继承ImageView 实现了多点触碰的拖动和缩放
 * 
 * @author Administrator
 * 
 */
public class TouchView extends ImageView {
    static final int NONE = 0;
    static final int DRAG = 1; // 拖动中
    static final int ZOOM = 2; // 缩放中
    static final int BIGGER = 3; // 放大ing
    static final int SMALLER = 4; // 缩小ing
    private int mode = NONE; // 当前的事件

    private float beforeLenght; // 两触点距离
    private float afterLenght; // 两触点距离
    private float scale = 0.04f; // 缩放的比例 X Y方向都是这个值 越大缩放的越快

    private int screenW;
    private int screenH;
    
    private int maxW = 2048;//图片最大宽度
    private int maxH = 2048;//图片最大高度
    private int minW = 204;//图片最小宽度
    private int minH = 204;//图片最小高度

    /* 处理拖动 变量 */
    private int start_rawx;
    private int start_rawy;
    private int start_x;
    private int start_y;
    private int stop_x;
    private int stop_y;
    
    private int moveX = 0;
    private int moveY = 0;
    static final int LEFT = 1; // 向左
    static final int RIGHT = 2; // 向右
    static final int TOP = 3; // 向上
    static final int BOTTOM = 4; // 向下

    private TranslateAnimation trans; // 处理超出边界的动画

    public TouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPadding(0, 0, 0, 0);
        screenW = getResources().getDisplayMetrics().widthPixels;
        screenH = getResources().getDisplayMetrics().heightPixels;
    }

    public TouchView(Context context, int w, int h) {
        super(context);
        this.setPadding(0, 0, 0, 0);
        screenW = w;
        screenH = h;
    }

    /**
     * 就算两点间的距离
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }
    
    boolean isOnclick = false;
    long isOnclickStartTime = 0;
    /**
     * 处理触碰..
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN:
            if(getWidth() > screenW || getHeight() > screenH){
                mode = DRAG;
            }else{
                mode = NONE;
            }
            stop_x = (int) event.getRawX();
            stop_y = (int) event.getRawY();
            start_rawx = stop_x;
            start_rawy = stop_y;
            start_x = (int) event.getX();
            start_y = stop_y - this.getTop();
            if (event.getPointerCount() == 2){//双指
                beforeLenght = spacing(event);
            }else if(event.getPointerCount() == 1){//单指
                moveX = NONE;
                moveY = NONE;
                isOnclick = true;
                isOnclickStartTime = System.currentTimeMillis();
            }
            break;
        case MotionEvent.ACTION_POINTER_DOWN:
            if (spacing(event) > 10f) {
                mode = ZOOM;
                beforeLenght = spacing(event);
            }
            break;
        case MotionEvent.ACTION_UP:
            /* 判断是否超出范围 并处理 */
            int disX = 0;
            int disY = 0;
            if(moveX != NONE || moveY != NONE){
                if (getWidth() > screenW) {
                    if(moveX == LEFT){
                        if (this.getRight() < screenW) {
                            disX = -(screenW - getRight());
                            this.layout(-(getWidth() - screenW), this.getTop(), screenW, this.getBottom());
                        }
                    }else if(moveX == RIGHT){
                        if (this.getLeft() > 0) {
                            disX = getLeft();
                            this.layout(0, this.getTop(), getWidth(), this.getBottom());
                        }
                    }
                }else{
                    if(moveX == LEFT){
                        if (this.getRight() > screenW) {
                            disX = -(screenW - getRight());
                            this.layout(-(getWidth() - screenW), this.getTop(), screenW, this.getBottom());
                        }
                    }else if(moveX == RIGHT){
                        if (this.getLeft() > 0) {
                            disX = getLeft();
                            this.layout(0, this.getTop(), getWidth(), this.getBottom());
                        }
                    }
                }
                if (getHeight() > screenH) {
                    if(moveY == TOP){
                        if (this.getBottom() < screenH) {
                            disY = -(screenH - getHeight() - getTop());
                            this.layout(this.getLeft(), screenH - getHeight(), this.getRight(), screenH);
                        }
                    }else if(moveY == BOTTOM){
                        if (this.getTop() > 0) {
                            disY = getTop();
                            this.layout(this.getLeft(), 0, this.getRight(), 0 + this.getHeight());
                        }
                    }
                }else{
                    if(moveY == TOP){
                        if (this.getTop() < 0) {
                            disY = getTop();
                            this.layout(this.getLeft(), 0, this.getRight(), 0 + this.getHeight());
                        }
                    }else if(moveY == BOTTOM){
                        if (this.getBottom() > screenH) {
                            disY = -(screenH - getHeight() - getTop());
                            this.layout(this.getLeft(), screenH - getHeight(), this.getRight(), screenH);
                        }
                    }
                }
            }else{
                if(getHeight() > screenH && getWidth() > screenW){
                    if(getHeight() > screenH){
                        if (this.getBottom() < screenH) {
                            disY = -(screenH - getHeight() - getTop());
                            this.layout((screenW - getWidth())/2, screenH - getHeight(), getWidth()+(screenW - getWidth())/2, screenH);
                        }
                        if (this.getTop() > 0) {
                            disY = getTop();
                            this.layout((screenW - getWidth())/2, 0, getWidth()+(screenW - getWidth())/2, 0 + this.getHeight());
                        }
                        disX = getLeft() - (screenW - getWidth())/2;
                    }
                    if(getWidth() > screenW){
                        if (this.getRight() < screenW) {
                            disX = -(screenW - getRight());
                            this.layout(-(getWidth() - screenW), (screenH - getHeight())/2, screenW, getHeight()+(screenH - getHeight())/2);
                        }
                        if (this.getLeft() > 0) {
                            disX = getLeft();
                            this.layout(0, (screenH - getHeight())/2, getWidth(), getHeight()+(screenH - getHeight())/2);
                        }
                        disY = getTop() - (screenH - getHeight())/2;
                    }
                }else{
                    disX = getLeft() - (screenW - getWidth())/2;
                    disY = getTop() - (screenH - getHeight())/2;
                    this.layout((screenW - getWidth())/2, (screenH - getHeight())/2, getWidth()+(screenW - getWidth())/2, getHeight() + (screenH - getHeight())/2);
                }
            }
            
            if (disX != 0 || disY != 0) {
                trans = new TranslateAnimation(disX, 0, disY, 0);
                trans.setDuration(500);
                this.startAnimation(trans);
            }
            mode = NONE;
            if(isOnclick && Math.abs(event.getRawX() - stop_x) < 10 && Math.abs(event.getRawY() - stop_y) < 10 && System.currentTimeMillis() - isOnclickStartTime < 500){
                performClick();
            }
            break;
        case MotionEvent.ACTION_POINTER_UP:
            mode = NONE;
            break;
        case MotionEvent.ACTION_MOVE:
            /* 处理拖动 */
            if (mode == DRAG) {
                LogInfo.LogOut("start_rawx="+start_rawx+"  rawx="+ event.getRawX() +"  start_rawy="+start_rawy+"  y="+event.getRawY());
                if (Math.abs(stop_x - start_x - getLeft()) < 88 && Math.abs(stop_y - start_y - getTop()) < 85) {
                    if(Math.abs(event.getRawX() - start_rawx) > 10 || Math.abs(event.getRawY() - start_rawy) > 10){
                        isOnclick = false;
                    }
                    if(event.getRawX() > start_rawx){
                        moveX = RIGHT;
                    }else if(event.getRawX() < start_rawx){
                        moveX = LEFT;
                    }else {
                        moveX = NONE;
                    }
                    if(event.getRawY() > start_rawy){
                        moveY = BOTTOM;
                    }else if(event.getRawY() < start_rawy){
                        moveY = TOP;
                    }else {
                        moveY = NONE;
                    }
                    
                    this.setPosition(stop_x - start_x, stop_y - start_y, stop_x + this.getWidth() - start_x, stop_y - start_y + this.getHeight());
                    stop_x = (int) event.getRawX();
                    stop_y = (int) event.getRawY();
                }
            }
            /* 处理缩放 */
            else if (mode == ZOOM) {
                if (spacing(event) > 10f) {
                    isOnclick = false;
                    afterLenght = spacing(event);
                    float gapLenght = afterLenght - beforeLenght;
                    if (gapLenght == 0) {
                        break;
                    } else if (Math.abs(gapLenght) > 5f) {
                        if (gapLenght > 0) {
                            if(getWidth() < maxW && getHeight() < maxH){
                                this.setScale(scale, BIGGER);
                            }
                        } else {
                            if(getWidth() > minW || getHeight() > minH){
                                this.setScale(scale, SMALLER);
                            }
                        }
                        beforeLenght = afterLenght;
                    }
                }
            }
            break;
        }
        return true;
    }

    /**
     * 实现处理缩放
     */
    private void setScale(float temp, int flag) {

        if (flag == BIGGER) {
            this.setFrame(this.getLeft() - (int) (temp * this.getWidth()), this.getTop() - (int) (temp * this.getHeight()), this.getRight() + (int) (temp * this.getWidth()),
                    this.getBottom() + (int) (temp * this.getHeight()));
        } else if (flag == SMALLER) {
            this.setFrame(this.getLeft() + (int) (temp * this.getWidth()), this.getTop() + (int) (temp * this.getHeight()), this.getRight() - (int) (temp * this.getWidth()),
                    this.getBottom() - (int) (temp * this.getHeight()));
        }
    }

    /**
     * 实现处理拖动
     */
    private void setPosition(int left, int top, int right, int bottom) {
        this.layout(left, top, right, bottom);
    }

}
