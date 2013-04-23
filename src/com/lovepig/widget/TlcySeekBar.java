package com.lovepig.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.lovepig.utils.LogInfo;

public class TlcySeekBar extends View {

    public interface OnSeekListener {
        public void onSeek(int per);
    }

    int seekHeight;
    int seekWidth;
    float Scale;//
    int downPer = 0;
    int playPer = 0;
    Bitmap bg;
    Bitmap down;
    Bitmap fing;
    boolean isCanSeek = false;
    boolean isSeek = false;
    Context context;
    OnSeekListener onSeekListener;
    int ScreenW = 0;

    public TlcySeekBar(Context context) {
        super(context);
    }

    public TlcySeekBar(Context context, AttributeSet attr) {
        super(context, attr);
        this.context = context;
//        TypedArray a = context.obtainStyledAttributes(attr, R.styleable.TlcySeekBar);
//        bg = BitmapFactory.decodeResource(getResources(), a.getInteger(R.styleable.TlcySeekBar_bg, R.drawable.playerprocessdownload));
//        down = BitmapFactory.decodeResource(getResources(), a.getInteger(R.styleable.TlcySeekBar_downbg, R.drawable.playerprocessdownload));
//        fing = BitmapFactory.decodeResource(getResources(), a.getInteger(R.styleable.TlcySeekBar_fing, R.drawable.playerprocessfing));
        seekHeight = 18;
    }

    public void init(int bgId, int downId, int fingId) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        ScreenW = wm.getDefaultDisplay().getWidth();
        seekWidth = (int) (260 * getResources().getDisplayMetrics().density + 0.5);
        seekHeight = (int) (12 * getResources().getDisplayMetrics().density + 0.5);
        if (ScreenW <= 320) {
            seekWidth = (int) (260 * getResources().getDisplayMetrics().density + 0.5);
        }

        bg = BitmapFactory.decodeResource(getResources(), bgId);
        down = BitmapFactory.decodeResource(getResources(), downId);
        fing = BitmapFactory.decodeResource(getResources(), fingId);
        Scale = seekWidth / 260f;

    }

    public void changeWidthAndHeight(int w, int h) {
        seekWidth = w;
        seekHeight = h;
        LogInfo.LogOut("TlcySeekBar.changeWidthAndHeight  w=" + w + "  h=" + h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bg, null, new Rect(0, 0, seekWidth, seekHeight), null);
        canvas.drawBitmap(down, null, new Rect(3, 1, seekWidth * downPer / 100, seekHeight), null);

        canvas.drawBitmap(fing, null, new Rect(seekWidth * playPer / 100 + ((int) (fing.getWidth()) / 2), 0, seekWidth * playPer / 100 + ((int) (fing.getWidth()) * 3 / 2), 20),
                null);

        canvas.restore();
    }

    public void onPlaying(int per) {
        if (!isSeek) {
            playPer = per;
            downPer = playPer;
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogInfo.LogOut("TlcySeekBar.onTouch");
        if (!isCanSeek) {
            isSeek = false;
            return true;
        }
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            setPressed(true);
            onStartTrackingTouch();
            trackTouchEvent(event);
            break;

        case MotionEvent.ACTION_MOVE:
            trackTouchEvent(event);
            break;

        case MotionEvent.ACTION_UP:
            trackTouchEvent(event);
            onStopTrackingTouch();
            setPressed(false);
            invalidate();
            break;

        case MotionEvent.ACTION_CANCEL:
            onStopTrackingTouch();
            setPressed(false);
            invalidate(); // see above explanation
            break;
        }
        return true;
    }

    void onStartTrackingTouch() {
        isSeek = true;
    }

    void trackTouchEvent(MotionEvent event) {
        isSeek = true;
        playPer = (int) (event.getX() * 100 / seekWidth - (fing.getWidth() <= 100 ? fing.getWidth() * Scale / 10 : 0));
        playPer = playPer < 0 ? 0 : playPer;
        playPer = playPer > 100 ? 100 : playPer;
        downPer = playPer;
        invalidate();
        LogInfo.LogOut("TlcySeekBar.trackTouchEvent. enent.getX()=" + event.getX() + "  seekWidth=" + seekWidth + "  playPer=" + playPer);
    }

    void onStopTrackingTouch() {
        isSeek = false;
        if (isCanSeek && onSeekListener != null) {
            onSeekListener.onSeek(playPer);
        }
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        throw new UnsupportedOperationException("Use setOnSeekListener(OnSeekListener l) please!");
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        throw new UnsupportedOperationException("Use setOnSeekListener(OnSeekListener l) please!");
    }

    /**
     * ���ûص�����,���û���ק��λ��Ƶλ�ú�,��������λ�ðٷֱȿ̶� 2010-10-9
     * 
     * @author Li Hongjun
     */
    public void setOnSeekListener(OnSeekListener l) {
        onSeekListener = l;
    }

    /**
     * getHeight()������ȡ�ĸ߶Ȳ�׼ȷ,�˷���׼ȷ 2010-10-9
     * 
     * @author Li Hongjun
     */
    public int getSeekHeight() {
        return seekHeight;
    }

    public void setIsCanSeek(boolean isCanS) {
        isCanSeek = isCanS;
        if (!isCanSeek) {
            isSeek = false;
            onPlaying(0);
        }
    }
}
