package com.lovepig.pivot;

import java.util.Stack;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 每一个Activity为一个功能模块，其内部存放处理不同事务的manager
 */
public abstract class BaseActivity extends Activity {
    /**
     * 对manager的顺序管理
     */
    public Stack<BaseManager> managerStack = new Stack<BaseManager>();
    /**
     * 此控件为主显示区域
     */
    public RelativeLayout dcEngineContener;
   

  
    /**
     * 多进程时，调用别的进程，参数以json形式传递，返回json形式字符串
     */
    public String remoteDoAction(String json) {
       
        return null;
    }

    /**
     * 在子类实现。多进程时，被别的进程(一般为主进程)回调，参数以json形式传递，返回json形式字符串 此项目中不需要进程间通信,子类无需处理
     */
    public String callFromRemote(String action) {
        return null;
    }

    /**
     * 返回子类类型，判断是否向远程service注册了回调函数，防止重复注册 一般返回getClass().getName()即可
     */
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

   

    private Toast mToast;// 统一toast

    /**
     * 显示小提示
     */
    public void showToast(String text) {
       
        mToast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        mToast.setText(text);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public ProgressDialog loadingDialog;

    /**
     * 显示正在获取数据的弹出框提示
     */
    public void showLoading(String text, OnCancelListener listener, boolean cancelable) {
        /**
         * 等待画面初始化 有的手机不new不能显示动画
         */
        if (loadingDialog == null || !loadingDialog.isShowing()) {
            if (loadingDialog != null)
                loadingDialog.dismiss();

            loadingDialog = new ProgressDialog(this);
            loadingDialog.setMessage(text);
            loadingDialog.setIndeterminate(true);
            loadingDialog.setCancelable(cancelable);
            loadingDialog.setOnCancelListener(listener);
            loadingDialog.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_SEARCH){
                        return true;
                    }
                    return false;
                }
            });
        }
        loadingDialog.show();
    }

    private Dialog tempDialog;// 处理连续快速点击出现多个对话框的情况
    private Dialog toDialog = null;// 金立，魅族等手机在不显示时无法弹出alertdialog
    private boolean isShowing = true;// 当前activity是否显示中

    /**
     * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示后再弹出dialog
     * 
     * @param dialog
     */
    public Dialog showDialog(Dialog dialog) {
        if (tempDialog != null && tempDialog.isShowing()) {
            tempDialog.dismiss();
        }
        if (isShowing) {
            tempDialog = dialog;
            tempDialog.show();
        } else {
            toDialog = dialog;
        }
        return dialog;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShowing = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
        if (toDialog != null) {
            toDialog.show();
            toDialog = null;
        }
    }

   

    /**
     * 屏蔽过快的按键点击
     */
    long lastKeyDown = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
      
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (currentManager != null) {
                if (Math.abs(System.currentTimeMillis() - lastKeyDown) > 500) {
                    lastKeyDown = System.currentTimeMillis();
                    currentManager.back();
                    return true;
                }
            }
        }
        return super.onKeyUp(keyCode, event);
    }
 
    /**
     * 返回程序主界面
     */
    public void leaveToMain() {

    }

    /**
     * 当前显示的manager
     */
    public BaseManager currentManager;

    public boolean isNowManager(BaseManager manager) {
        if (managerStack != null && managerStack.size() > 0 && managerStack.peek() == manager) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置此模块的主manager
     */
    public void setMainManager(BaseManager manager) {
        try {
            if(managerStack.size() > 0){
                if(managerStack.peek() == manager){
                    return ;
                }
            }
            managerStack.clear();
            managerStack.push(manager);
            dcEngineContener.removeAllViews();
            dcEngineContener.addView(manager.getMainDC(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            currentManager = manager;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置此模块的辅manager
     */
    public void setSubManager(BaseManager manager) {
        try {
            if (managerStack.size() > 0 && managerStack.peek() != manager) {
                managerStack.push(manager);
                dcEngineContener.removeAllViews();
                dcEngineContener.addView(manager.getMainDC(), new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
            }
            currentManager = manager;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
