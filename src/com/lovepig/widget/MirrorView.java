package com.lovepig.widget;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

import com.lovepig.utils.LogInfo;

public class MirrorView extends Gallery {

    private Camera mCamera = new Camera();     // 是用来做类3D效果处理,比如z轴方向上的平移,绕y轴的旋转等

    // -120 for hvga
    // 
    private int mMaxZoom = -250;             // 是图片在z轴平移的距离,视觉上看起来就是放大缩小的效果.
    private int mCoveflowCenter;
    private boolean mAlphaMode = true;
    private boolean mCircleMode = false;

    private int screenWidth=768;
    
    public MirrorView(Context context) {
        super(context);
        this.setStaticTransformationsEnabled(true);
        //this.setSpacing(800);
        init();
    }

    public MirrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setStaticTransformationsEnabled(true);
        init();
    }

    public MirrorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setStaticTransformationsEnabled(true);
        init();
    }

    public void init(){
        screenWidth=getResources().getDisplayMetrics().widthPixels;
        if(screenWidth == 768){
            mMaxZoom = -250;
        }else if(screenWidth == 600){
            mMaxZoom= -120;
        }else if(screenWidth == 1024){
            mMaxZoom = -350;
        }
    }
    public boolean getCircleMode() {
        return mCircleMode;
    }

    public void setCircleMode(boolean isCircle) {
        mCircleMode = isCircle;
    }

    public boolean getAlphaMode() {
        return mAlphaMode;
    }

    public void setAlphaMode(boolean isAlpha) {
        mAlphaMode = isAlpha;
    }

    public int getMaxZoom() {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom) {
        mMaxZoom = maxZoom;
    }

    private int getCenterOfCoverflow() {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    //得到子对象的中线
    private static int getCenterOfView(View view) {
        int width_v = view.getLeft() + view.getWidth() / 2;
        LogInfo.LogOut("---------------------------------width_v = "+width_v);
        return width_v;
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
    	//减慢滚动速度
    	return super.onFling(e1, e2, velocityX/3, velocityY/3);
    }
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        final int childCenter = getCenterOfView(child);
        float rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);
        int shift = mCoveflowCenter - childCenter;
        if (shift < this.getWidth() * 0.1 && shift >= 0) {
        	rotationAngle = (float)(90 - 18 + (shift - this.getWidth()*0.1) * (90-18)/(this.getWidth()*0.1));
        }
        else if (shift > -this.getWidth() * 0.1 && shift < 0) {
        	rotationAngle = (float)(-shift * (90+10) / this.getWidth() * 10.0);
        }
        else if (shift < 0) {
        	rotationAngle = (float)(90 - shift * 180.0 / this.getWidth()+180);
        }
        else {
        	rotationAngle = (float)(90 - shift * 180.0 / this.getWidth());
        } 
        transformImageBitmap2((View) child, t, rotationAngle, shift);
        return true;
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     * 
     * @param w
     *            Current width of this view.
     * @param h
     *            Current height of this view.
     * @param oldw
     *            Old width of this view.
     * @param oldh
     *            Old height of this view.
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }
    
    /**
     * Transform the Image Bitmap by the Angle passed
     * 
     * @param imageView
     *            ImageView the ImageView whose bitmap we want to rotate
     * @param t
     *            transformation
     * @param rotationAngle
     *            the Angle by which to rotate the Bitmap
     */
    private void transformImageBitmap2(View child, Transformation t, float rotationAngle, int shift) {
        mCamera.save();
        final Matrix imageMatrix = t.getMatrix();
        final int imageHeight = child.getLayoutParams().height;
        final int imageWidth = child.getLayoutParams().width;
        final float rotation = Math.abs(rotationAngle);
        //for 320x480: final int zoom = Math.abs(shift);
        int zoom = Math.abs(shift)/2;
        if(screenWidth == 768){
            zoom = Math.abs(shift)/2;
        }else if(screenWidth == 600){
            zoom = Math.abs(shift);
        }else if(screenWidth == 1024){
            zoom = Math.abs(shift)/4;
        }
        LogInfo.LogOut("zoom="+zoom);
//        mCamera.translate(0.0f, 0.0f, 100.0f);
        // As the angle of the view gets less, zoom in
        // mMaxZoom 放大基础
        // zoom 根据位移缩放比率
//        float zoomAmount = (float) (mMaxZoom + zoom);
//        mCamera.translate(0.0f, 0.0f, zoomAmount);
//        if (mCircleMode) {
//            if (zoom < 40)
//                mCamera.translate(0.0f, 155, 0.0f);
//            else
//                mCamera.translate(0.0f, (255 - zoom * 2.5f), 0.0f);
//        }
//        
//        mCamera.rotateY(rotationAngle);
//        mCamera.getMatrix(imageMatrix);
//        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
//        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        
        float zoomAmount = (float) (-140 + (rotation * 2));
        if (rotationAngle < 0) {
            mCamera.translate((float) (-rotation * 0.5), (float) (-rotation * 0.3) + 5, zoomAmount);
        } else {
            mCamera.translate((float) rotation, (float) (-rotation * 0.3) + 5, zoomAmount);
        }
        if (mCircleMode) {
            if (rotation < 40) {
                mCamera.translate(0.0f, 155, 0.0f);
            } else {
                mCamera.translate(0.0f, (255 - rotation * 2.5f), 0.0f);
            }
        }
        mCamera.rotateX((float) (rotationAngle + 0.5 * rotationAngle));
        mCamera.rotateY((float) (rotationAngle + 0.5 * rotationAngle));

        mCamera.getMatrix(imageMatrix);
        imageMatrix.preTranslate(-(imageWidth / 2), -(imageHeight / 2));
        imageMatrix.postTranslate((imageWidth / 2), (imageHeight / 2));
        mCamera.restore();
    }
    
    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
    	int selectedIndex = this.computeHorizontalScrollOffset() - this.getFirstVisiblePosition();
    	
    	if (i < selectedIndex) {
    		return i;
    	}
    	else {
    		return childCount - 1 + selectedIndex - i;
    	}
    }
}

