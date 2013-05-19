package com.lovepig.manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Message;

import com.lovepig.engine.CheckUserEngine;
import com.lovepig.main.Configs;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.widget.TlcyDialog.TlcyDialogListener;

public class CheckUserManager extends BaseManager {
    CheckUserEngine engine;

    public CheckUserManager(BaseActivity c) {
        super(c);
        engine = new CheckUserEngine(this);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case 1:// 升级
            if (Configs.UPDATE_FLAG == 1) {// 可以升级也可以不升级
                showAlert(Configs.u_ShowPopStr, new TlcyDialogListener() {
                    @Override
                    public void onClick() {
                        try {
                            Uri uri = Uri.parse(Configs.u_Url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, null);
            } else if (Configs.UPDATE_FLAG == 2) {// 必须升级,否则不可使用
                showAlert(Configs.u_ShowPopStr, new TlcyDialogListener() {
                    @Override
                    public void onClick() {
                        try {
                            Uri uri = Uri.parse(Configs.u_Url);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            context.startActivity(intent);
                            sendEmptyMessageDelayed(2, 1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new TlcyDialogListener() {
                    @Override
                    public void onClick() {
                        sendEmptyMessage(2);
                    }
                });
            }
            break;
        case 2:// 强制升级时退出程序
            System.exit(0);
            break;
        case 3:// 新用户赠送提示
            try {
                int iUsrBalance = msg.arg1;
                String alertStr1 = "感谢您的注册，已向您的账户赠送充值";
                String alertStr2 = "点！您可在“在线商城”查询、订阅和购买您感兴趣的内容资源。欢迎您体验新华频媒电子阅读带来的正版内容服务！";
                showAlert(alertStr1+iUsrBalance+alertStr2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        default:
            break;
        }
    }

    /**
     * 鉴权,可在多个线程同时调用,只鉴权一次
     */
    public synchronized void checkUser() {
//        if (Configs.isCheckin) {
            // 已经在其他线程鉴权完毕
//        } else {
            engine.checkUser();
//        }
    }

    /**
     * 轮询
     */
    public synchronized void timingPolling() {
        engine.timingPolling();
    }

}
