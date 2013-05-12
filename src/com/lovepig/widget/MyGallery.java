package com.lovepig.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lovepig.main.R;
import com.lovepig.utils.LogInfo;

/**
 * 由于Gallery会自动居中等不可控原因,自定义此Gallery,用于分类展示
 */
public class MyGallery extends HorizontalScrollView implements OnClickListener {
    Context context;
    int itemWidth = 0;
    int itemDimenWidth = 0;
    private long l = 0;

    public interface TlcyGalleryListener {
        /**
         * 点击回调,如果处理了回调,则返回true,否则返回false
         */
        public boolean onItemClick(int position);

        /**
         * 状态回调 state 0为不可滚动,1为只可以向左滚动,2为只可以向右滚动,3为还可以向左向右滚动
         */
        public void onState(int state);
    }

    LayoutInflater inflater;
    LinearLayout layout = null;
    TlcyGalleryListener listener = null;

    /**
     * 被选中的项
     */
    int selected = -1;
    /**
     * 子项数目
     */
    int childNum = -1;
    /**
     * 选中的背景
     */
    int bg;
    /**
     * 每个项的宽度dimen
     */
    int widthDimenId;
    /**
     * 每个项的高度dimen
     */
    int heightDimenId;

    public MyGallery(Context context) {
        super(context);
        this.context = context;
    }

    public MyGallery(Context context, AttributeSet set) {
        super(context, set);
        this.context = context;
    }

    public MyGallery(Context context, AttributeSet set, int style) {
        super(context, set, style);
        this.context = context;
    }

    /**
     * 设置回调函数
     */
    public void setOnItemClickListener(TlcyGalleryListener listener) {
        this.listener = listener;
    }

    /**
     * 设置分类名称列表 res为Item视图,必须是一个Button,bgRes为选中时的背景
     */
    public void setAdapter(int res, int bgRes, int itemWidthDimenId, int itemHeightDimenId, String[] texts) {

        if (layout == null) {
            inflater = LayoutInflater.from(getContext());
            layout = new LinearLayout(getContext());
            layout.setGravity(Gravity.CENTER_VERTICAL);
            addView(layout);
        } else {
            layout.removeAllViews();
        }
        bg = bgRes;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        widthDimenId = itemWidthDimenId;
        itemWidth = wm.getDefaultDisplay().getWidth() / texts.length;
        itemDimenWidth = (int) getResources().getDimension(widthDimenId);
        LogInfo.LogOut("info", "itemWidth =" + itemWidth + "    itemDimenWidth =" + itemDimenWidth);
        heightDimenId = itemHeightDimenId;
        if (texts != null) {
            for (int i = 0; i < texts.length; i++) {
                Button button = (Button) inflater.inflate(res, null);
                if (button != null) {
                    button.setText(texts[i]);
                    button.setTag(i);
                    button.setOnClickListener(this);
                    button.setBackgroundDrawable(mkSelector(-1, bg, -1, -1));
                    button.setPadding(0, 0, 0, 0);//不加这句无法使用9png
                    layout.addView(button, new LinearLayout.LayoutParams(getItemWidth(), (int) getResources().getDimension(heightDimenId)));
                    if(i != texts.length-1){
                        ImageView im = new ImageView(context);
                        im.setBackgroundResource(R.drawable.fenlei_sp);
                        layout.addView(im,new LinearLayout.LayoutParams(getResources().getDimensionPixelOffset(R.dimen.one_dp), LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                    // ((LinearLayout.LayoutParams)button.getLayoutParams()).weight=1;
                }
            }
            childNum = texts.length;
            setSelected(0);
        }
        // else {//走不到这里,不然itemWidth已经异常
        // childNum = -1;
        // setSelected(-1);
        // }

    }

    
    /** 设置Selector。 */
    private StateListDrawable mkSelector(int idNormal, int idPressed, int idFocused,
            int idUnable) {
    StateListDrawable bg = new StateListDrawable();
    Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
    Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
    Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
    Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
    // View.PRESSED_ENABLED_STATE_SET
    bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
    // View.ENABLED_FOCUSED_STATE_SET
    bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
    // View.ENABLED_STATE_SET
    bg.addState(new int[] { android.R.attr.state_enabled }, normal);
    // View.FOCUSED_STATE_SET
    bg.addState(new int[] { android.R.attr.state_focused }, focused);
    // View.WINDOW_FOCUSED_STATE_SET
    bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
    // View.EMPTY_STATE_SET
    bg.addState(new int[] {}, normal);
    return bg;
}
    
    private int getItemWidth() {
        if (itemWidth > itemDimenWidth) {
            return itemWidth;
        } else {
            return itemDimenWidth;// itemDimenWidth;
        }
    }

    /**
     * 返回是否按钮太多超出现实范围,需要滚动
     */
    public boolean isScroll() {
        int width = 0;// getWidth();
        int btnWidth = getItemWidth();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        if (width < childNum * btnWidth) {
            return true;
        }
        return false;
    }

    /**
     * 设置某一下为选中状态
     */
    public void setSelected(int pos) {
        selected = pos;
        Button button;
        ImageView imageView;
        for (int i = 0; i < childNum; i++) {
            button=(Button) layout.getChildAt(2*i);
            if (i == selected) {
                button.setTextColor(Color.YELLOW);
                
                button.getPaint().setFakeBoldText(true);
            } else {
                button.getPaint().setFakeBoldText(false);
                button.setTextColor(Color.WHITE);
            }
            if(i != childNum - 1){
                imageView = (ImageView) layout.getChildAt(2*i+1);
                imageView.getLayoutParams().width=getResources().getDimensionPixelOffset(R.dimen.one_dp);
                //imageView.setVisibility(GONE);
                imageView.invalidate();
            }
            button.invalidate();
        }
        layout.invalidate();
    }

    /**
     * 外部调用setSelected后,需调用此方法滚动到目标选中项
     */
    public void scrollToSelected() {
        if (selected >= 0 && selected < childNum) {
            int scroolx = getScrollX();
            int width = getWidth();
            int btnWidth = getItemWidth();
            int perShowing = width / btnWidth;// 每页能显示的项数
            int maxShowing = (scroolx + width) / btnWidth;// 显示的最大的项数
            if (selected >= maxShowing) {// 选择的在右边,根据具体效果还需修改
                scrollTo((selected - perShowing + 1) * btnWidth, 0);
            } else {
                if ((maxShowing - selected) >= perShowing) {
                    scrollTo((selected) * btnWidth, 0);
                }
            }

        }
    }

    /**
     * 向右滚动一个项
     */
    public void scrollRight() {
        scrollTo(getScrollX() - getItemWidth(), 0);
    }

    /**
     * 向左滚动一个项
     */
    public void scrollLeft() {
        scrollTo(getScrollX() + getItemWidth(), 0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int state = 0;
        if (isScroll()) {
            if (getScrollX() == 0) {
                state = 1;
            } else {
                int x = getScrollX(), w = getWidth(), a = childNum * getItemWidth();
                LogInfo.LogOut("getScrollX=" + x + "  getWidth=" + w + " childNum * getItemWidth()=" + a);
                if ((x + w) < a) {
                    state = 3;
                } else {
                    state = 2;
                }
            }
        }
        if (listener != null) {
            listener.onState(state);
        }
    }

    public LinearLayout getLayout() {
        return layout;
    }

    /**
     * 返回选中的序号,-1为没有选中
     */
    public int getSelected() {
        return selected;
    }

    @Override
    public void onClick(View v) {
        if(Math.abs(System.currentTimeMillis() - l) > 500){
            l=System.currentTimeMillis();
            try {
                int pos = Integer.parseInt(v.getTag().toString());
                if (listener != null) {
                    if(listener.onItemClick(pos)){
                        setSelected(pos);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        View view;
        for (int i = 0; i < childNum; i++) {
            view = layout.getChildAt(i);
            if (view != null&&view instanceof Button) {
                view.getLayoutParams().width = getItemWidth();
                view.getLayoutParams().height = (int) getResources().getDimension(heightDimenId);
                view.requestLayout();
            }
        }
        super.onConfigurationChanged(newConfig);
    }
}
