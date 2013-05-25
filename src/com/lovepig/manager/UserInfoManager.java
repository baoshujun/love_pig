package com.lovepig.manager;

import android.os.Message;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewAnimator;

import com.lovepig.main.Application;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Utils;
import com.lovepig.view.UserInfoView;

public class UserInfoManager extends BaseManager {
    UserInfoView userInfoView;

    public UserInfoManager(BaseActivity c) {
        super(c);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
        case 0:
            break;
        case 1:// 获取到消息更新界面
            if (userInfoView != null && getNowShownDC() == userInfoView) {
                userInfoView.notifyDataSetChanged();
            }
            break;
        case 3:
            userInfoManageFunctionProcess(msg);
            break;
        default:
            break;
        }
    }

    private void userInfoManageFunctionProcess(Message msg) {
        Button button = (Button) msg.obj;
        switch (msg.arg1) {
        case 0:
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
            Application.userManager.EnterUserManager(R.string.user_info);
            break;
        case 1:// 充值中心
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }

            break;

        case 3:// 好友
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
            break;
        case 4:// 关于帮助
                
        	Application.application.setSubManager(Application.aboutManager);
            break;
        case 5:// 收藏
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
            break;
        default:
            if (button == null) {
                showAlert("程序错误,不应该不存在");
            } else {
                showAlert("标题右边按钮右边距:" + ((RelativeLayout.LayoutParams) button.getLayoutParams()).rightMargin + "\n本应该是:"
                        + context.getResources().getDimension(R.dimen.title_right_btn_margin));
            }
            break;
        }
    }

    @Override
    public ViewAnimator getMainDC() {
        if (userInfoView == null) {
            userInfoView = new UserInfoView(context, R.layout.user_info, this);
            dcEngine.setMainDC(userInfoView);
        }
        userInfoView.notifyDataSetChanged();
        return super.getMainDC();
    }
}
