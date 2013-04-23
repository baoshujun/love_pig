package com.lovepig.widget;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lovepig.main.R;

/**
 * 
 * TODO 单行文本跑马灯控件
 */
public class AutoScrollTextView extends TextView {
    public final static String TAG = AutoScrollTextView.class.getSimpleName();

    private float textLength = 0f;// 文本长度
    private float viewWidth = 0f;
    private float viewHeight = 0f;
    private float step = 0f;// 文字的横坐标
    private float y = 0f;// 文字的纵坐标
    private float temp_view_plus_text_length = 0.0f;// 用于计算的临时变量
    private float temp_view_plus_two_text_length = 0.0f;// 用于计算的临时变量
    public boolean isStarting = false;// 是否开始滚动
    private Paint paint = null;// 绘图样式
    private ArrayList<String[]> text;// 文本内容
    private int textNum = 0;

    public AutoScrollTextView(Context context) {
        super(context);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 文本初始化，每次更改文本内容或者文本效果等之后都需要重新初始化一下
     */
    public void init(ArrayList<String[]> list) {
        paint = getPaint();
        text = list;
        paint.setColor(getResources().getColor(R.color.online_item_title));
        viewWidth = getWidth();
        viewHeight = getHeight();
        if (viewWidth == 0 && viewHeight == 0) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    init(text);
                }
            }, 500);
        }
        y = viewHeight - getTextSize() / 4;
        init();
    }

    /**
     * 文本初始化，每次更改文本内容或者文本效果等之后都需要重新初始化一下
     */
    public void init(String txt) {
        text = new ArrayList<String[]>();
        String[] txtArray = new String[] { txt, "" };
        text.add(txtArray);
        init(text);
    }
    public void onConfigurationChanged(){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                init(text);
            }
        }, 100);
    }
    public void init() {
        textLength = paint.measureText(text.get(textNum)[0]);
        if (textLength > viewWidth) {
            step = textLength + viewWidth / 2;
            temp_view_plus_text_length = viewWidth + textLength;
            temp_view_plus_two_text_length = viewWidth + textLength * 2;
            startScroll();
        } else {
            step = 0;
            temp_view_plus_text_length = (viewWidth - textLength) / 2;
            stopScroll();
        }

    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        isStarting = false;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (text != null && text.size() > textNum && !text.get(textNum)[0].equals("")) {
            canvas.drawText(text.get(textNum)[0], temp_view_plus_text_length - step, y, paint);
            if (!isStarting) {
                return;
            }
            step += 0.5;
            if (step > temp_view_plus_two_text_length) {
                textNum++;
                if (textNum == text.size()) {
                    textNum = 0;
                }
                step = textLength;
                temp_view_plus_text_length = viewWidth + textLength;
            }
            invalidate();
        }
    }
}