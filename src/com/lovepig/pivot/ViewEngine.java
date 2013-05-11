package com.lovepig.pivot;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ViewAnimator;

public class ViewEngine implements AnimationListener {
    public enum AnimationStyle {
        fromBottom, fromLeft, fromTop, fromRight, fromCenter, fromAlpha, none
    }

    private ViewAnimator viewSwiter;
    private TranslateAnimation animNone;
    private TranslateAnimation animSlideInLeft;
    private TranslateAnimation animSlideOutLeft;
    private TranslateAnimation animSlideInRight;
    private TranslateAnimation animSlideOutRight;
    private ScaleAnimation scaleAnimation;
    private AlphaAnimation alphaAnimation;
    public Context context;
    public boolean isClickEnabled = true;
    int ScreenHeight, ScreenWidth;
    BaseManager fromManager = null;
    BaseManager toManager = null;
    BaseView currentDC = null;

    public ViewEngine(Context c) {
        context = c;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ScreenHeight = wm.getDefaultDisplay().getHeight();
        ScreenWidth = wm.getDefaultDisplay().getWidth();
        viewSwiter = new ViewAnimator(context);
        viewSwiter.setDrawingCacheEnabled(false);
        animNone = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animNone.setDuration(1);
        animSlideInLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        animSlideInLeft.setDuration(500);
        animSlideOutLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animSlideOutLeft.setDuration(500);
        animSlideInRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animSlideInRight.setDuration(500);
        animSlideOutRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animSlideOutRight.setDuration(500);
        alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(500);
        makeScaleAnimation();
        scaleAnimation.setDuration(1000);
        scaleAnimation.setAnimationListener(this);
        alphaAnimation.setAnimationListener(this);
        animSlideInLeft.setAnimationListener(this);
        animSlideOutLeft.setAnimationListener(this);
        animSlideInRight.setAnimationListener(this);
        animSlideOutRight.setAnimationListener(this);
    }

    public void setStyle(AnimationStyle aStyle) {
        switch (aStyle) {
        case fromBottom:
            break;
        case fromTop:
            break;
        case fromLeft:
            viewSwiter.setInAnimation(animSlideInLeft);
            viewSwiter.setOutAnimation(animSlideOutLeft);
            break;
        case fromRight:
            viewSwiter.setInAnimation(animSlideInRight);
            viewSwiter.setOutAnimation(animSlideOutRight);
            break;
        case fromCenter:
            viewSwiter.setAnimation(scaleAnimation);
            break;
        case fromAlpha:
            viewSwiter.setAnimation(alphaAnimation);
            break;
        case none:
            viewSwiter.setInAnimation(null);
            viewSwiter.setOutAnimation(null);
            break;
        default:
            break;
        }
    }

    /**
     * 不在动画中,用于判断是否相应click
     * 
     * @author  
     */
    public boolean notAnimition() {
        return isClickEnabled;
    }

    /**
     * 初始化之后,将初始化完毕的MainDC呈现,作为最底层的DC,重复back()方法N次后显示此DC,quit()方法直接返回到此DC,
     * 要在acitvity中的setContentView()方法前设置
     * 
     * @author  
     */
    public void setMainDC(BaseView mainDC) {
        viewSwiter.removeAllViews();
        viewSwiter.addView(mainDC, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        viewSwiter.setAnimation(alphaAnimation);
        viewSwiter.setDisplayedChild(0);
        mainDC.onShow();
    }

    public void setInitDC(BaseView initDC) {
        viewSwiter.removeAllViews();
        viewSwiter.addView(initDC, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        viewSwiter.setAnimation(scaleAnimation);
        viewSwiter.setDisplayedChild(0);
        initDC.onShow();
    }

    /**
     * 用于设置到activity中
     * 
     * @author  
     */
    public ViewAnimator getContentView() {
        /***** 新华频媒只用到了当前显示的DC判断,如果需要,请将viewSwiter中所有子项都执行checkOrientation()方法 *****************/
        if (viewSwiter.getCurrentView() instanceof BaseView) {
            ((BaseView) viewSwiter.getCurrentView()).checkOrientation();
        }
        return viewSwiter;
    }

    /**
     * 判断正在显示的dc是否是某dc
     * 
     * @author  
     */
    public boolean isShowDC(BaseView dc) {
        if (viewSwiter.getCurrentView() == dc) {
            return true;
        } else {
            return false;
        }
    }

    public BaseView getNowDC() {
        if (viewSwiter.getCurrentView() instanceof BaseView) {
            return (BaseView) viewSwiter.getCurrentView();
        } else {
            return null;
        }
    }

    public void makeScaleAnimation() {
        if (ScreenWidth == 320 && ScreenHeight == 600) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.3f, 0.1f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 320 && ScreenHeight == 800) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 800 && ScreenHeight == 480) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.4f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 480 && ScreenHeight == 800) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 600 && ScreenHeight == 1024) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 1024 && ScreenHeight == 600) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.4f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 768 && ScreenHeight == 1024) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.01f, 0.1f, 1.01f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else if (ScreenWidth == 1024 && ScreenHeight == 768) {
            scaleAnimation = new ScaleAnimation(0.1f, 1.01f, 0.1f, 1.01f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {// 未知屏幕则按默认
            scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        scaleAnimation.setFillAfter(true);
    }

    private void setClickEnabled(boolean isCleckEnabled) {
        if (isCleckEnabled) {
            viewSwiter.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isClickEnabled = true;
                }
            }, 200);
            // isClickEnabled=true;
            // viewSwiter.setClickable(true);
        } else {
            isClickEnabled = false;
            // viewSwiter.setClickable(false);
        }
    }

    public boolean back() {
        if (viewSwiter.getChildCount() > 1) {
            final BaseView toShow = (BaseView) viewSwiter.getChildAt(viewSwiter.getChildCount() - 2);
            toShow.onShow();
            viewSwiter.setDisplayedChild(viewSwiter.getChildCount() - 2);
            viewSwiter.removeViewAt(viewSwiter.getChildCount() - 1);
            return true;
        } else {
            return false;
        }
    }

    public void showDC(BaseView dc, int type, BaseManager from, BaseManager to) {
        if (type == 0) {
            setStyle(AnimationStyle.none);
        } else if (type == 1) {// from left to right
            setStyle(AnimationStyle.fromLeft);
        } else if (type == 2) {
            setStyle(AnimationStyle.fromRight);
        }
        if (viewSwiter.getCurrentView() == dc) {
            if (type == 0) {// 专为播放界面进入别的界面设置
                to.sendMessageDelayed(toManager.obtainMessage(BaseManager.MSG_ENTER_IN_END), 500);
            }
            return;
        }
        if (dc.getParent() != null) {
            if (dc.getParent() instanceof ViewGroup) {
                ((ViewGroup) (dc.getParent())).removeView(dc);
            } else {
                return;
            }
        }
        dc.invalidate();
        viewSwiter.addView(dc, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        viewSwiter.setDisplayedChild(viewSwiter.getChildCount() - 1);
        // viewSwiter.setDisplayedChild(1);
        // viewSwiter.removeViewAt(0);
        dc.onShow();
        fromManager = from;
        toManager = to;
        if (type == 0) {// 专为播放界面进入别的界面设置
            toManager.sendMessageDelayed(toManager.obtainMessage(BaseManager.MSG_ENTER_IN_END), 500);
        }
    }

    public void showDC(BaseView dc) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        setClickEnabled(true);
        if (fromManager == toManager && fromManager != null) {
            if (animation == animSlideOutLeft) {
                fromManager.sendEmptyMessage(BaseManager.MSG_ENTER_SELF_END);
            } else if (animation == animSlideOutRight) {
                fromManager.sendEmptyMessage(BaseManager.MSG_BACK_SELF_END);
            }
        } else if (animation == animSlideInLeft && fromManager != null) {
            toManager.sendEmptyMessage(BaseManager.MSG_ENTER_IN_END);
        } else if (animation == animSlideOutLeft && toManager != null) {
            fromManager.sendEmptyMessage(BaseManager.MSG_ENTER_OUT_END);
        } else if (animation == animSlideInRight && fromManager != null) {
            toManager.sendEmptyMessage(BaseManager.MSG_BACK_IN_END);
        } else if (animation == animSlideOutRight && toManager != null) {
            fromManager.sendEmptyMessage(BaseManager.MSG_BACK_OUT_END);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onAnimationStart(Animation animation) {
        setClickEnabled(false);
    }

    public void changSkin(BaseView dc) {
        dc.invalidate();
        viewSwiter.removeAllViews();
        viewSwiter.addView(dc);
        // viewSwiter.setAnimation(alphaAnimation);
        viewSwiter.setDisplayedChild(0);
        dc.onShow();
    }
}
