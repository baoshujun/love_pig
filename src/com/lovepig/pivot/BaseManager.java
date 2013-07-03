package com.lovepig.pivot;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Handler;
import android.os.Message;
import android.widget.ViewAnimator;
import com.lovepig.main.R;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.TlcyDialog;
import com.lovepig.widget.TlcyDialog.TlcyDialogListener;

public abstract class BaseManager extends Handler {

    public BaseActivity context;
    public ViewEngine dcEngine;
    public static final int MSG_ENTER_IN_END = 1000;
    public static final int MSG_ENTER_OUT_END = 1001;
    public static final int MSG_BACK_IN_END = 1002;
    public static final int MSG_BACK_OUT_END = 1003;
    public static final int MSG_BACK_SELF_END = 1004;
    public static final int MSG_ENTER_SELF_END = 1005;

    public BaseManager(BaseActivity c) {
        this.context = c;
        dcEngine = new ViewEngine(c);
    }

    /**
     * 返回当前正在显示的DC,非baseDC则返回null
     * 
     * @author  
     */
    public BaseView getNowShownDC() {
        return dcEngine.getNowDC();
    }

    /**
     * 必须在子类中实现,在主线程中调用,接受消息
     * 
     * @author  
     */
    public abstract void handleMessage(Message msg);

    /**
     * 在子线程中调用,做耗时的事情
     * 
     * @author  
     */
    public void initData() {
    };

    /**
     * 如果DC中的点击事件,需要更改数据等,则直接调用此方法,应在子类中重写
     * 
     * @author  
     */
    public void onClicked(int id) {

    }

    /**
     * 用于按钮被点击时做相应
     */
    public boolean back() {
        LogInfo.LogOut(" baseManager back....................");
        if (dcEngine.back()) {
            return true;
        } else {
            try {
                if (context.managerStack.size() > 0) {
                    context.managerStack.pop();
                    if (context.managerStack.size() > 1) {// 二级或者三级四级manger
                        context.setSubManager(context.managerStack.pop());
                    } else {
                        context.setMainManager(context.managerStack.pop());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    };

    /**
     * 用于back按键被点击时做相应
     */
    public boolean backOnKeyDown() {
        LogInfo.LogOut("backOnKeyDown....................");
        return dcEngine.back();
    };

    /**
     * 此模块的主界面
     */
    public ViewAnimator getMainDC() {
        return dcEngine.getContentView();
    };

    /**
     * 模块内界面前进跳转
     */
    public void enterSubDC(BaseView dc) {
//        if (dcEngine.notAnimition()) {
//            dcEngine.showDC(dc, 0, this, this);
//        }
        dcEngine.showDC(dc, 0, this, this);
    }

    /**
     * 模块内界面返回跳转
     */
    public void backSubDC(BaseView dc) {
        if (dcEngine.notAnimition()) {
            dcEngine.showDC(dc, 2, this, this);
        }
    }

    /**
     * 返回到程序主界面
     */
    public void quitToMainDC() {
        context.leaveToMain();
    }

    /**
     * 显示正在获取数据的弹出框提示
     */
    public void showLoading() {
        context.showLoading(context.getString(R.string.loading), new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onLoadingCacel();
            }
        }, true);
    }

    /**
     * 显示正在获取数据的弹出框提示
     */
    public void showLoading(String text) {
        context.showLoading(text, new OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onLoadingCacel();
            }
        }, true);
    }

    /**
     * 显示正在获取数据的弹出框提示
     */
    public void showLoading(String text, OnCancelListener listener) {
        context.showLoading(text, listener, true);
    }

    /**
     * 显示正在获取数据的弹出框提示
     */
    public void showLoading(String text, OnCancelListener listener, boolean cancelable) {
        context.showLoading(text, listener, cancelable);
    }

    /**
     * 隐藏正在获取数据的弹出框提示
     */
    public void dismissLoading() {
        if (context.loadingDialog != null) {
            context.loadingDialog.dismiss();
            context.loadingDialog = null;
        }
    }

    /**
     * 手动取消正在获取数据的弹出框提示时的回调函数
     */
    public void onLoadingCacel() {
        dismissLoading();
    }

    /**
     * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示后再弹出dialog
     */
    public Dialog showAlert(String text) {
        return context.showDialog(new TlcyDialog(context).setTitle(context.getString(R.string.tip)).setMessage(text).setOnlyOkPositiveMethod(context.getString(R.string.OK)));
    }

    /**
     * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示再在弹出dialog
     */
    public Dialog showAlert(String title, String text, String ok, String cancel, TlcyDialogListener okListener, TlcyDialogListener cancelListener) {
        return context.showDialog(new TlcyDialog(context).setTitle(title).setMessage(text).setButton(ok, cancel, okListener, cancelListener));
    }

    /**
     * 金立，魅族等手机在不显示时无法弹出dialog 此方法自动判断当前activity是否正在显示，并作出直接显示还是等显示再在弹出dialog
     */
    public Dialog showAlert(String text, TlcyDialogListener okListener, TlcyDialogListener cancelListener) {
        return context.showDialog(new TlcyDialog(context).setTitle("提示").setMessage(text).setButton("确定", "取消", okListener, cancelListener));
    }

    /**
     * 调用远程方法
     * 
     * @param json
     *            为Json的字符串，如果起PivotCenter.FROM的值是PivotCenter.
     *            from_Main则调用所有其他存在的activity的callFromRemote方法
     *            ，否则调用主进程service的doAction方法
     */
    public String remoteDoAction(String json) {
        return context.remoteDoAction(json);
    }

    /**
     * 显示小提示
     */
    public void showToast(String text) {
        context.showToast(text);
    }

}
