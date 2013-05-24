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
//    private MoreMsgMng messageProcessManager;
//    private MoreUserFriendMng userFriendProcessManager;
//    private UserReChargeManager userReChargeManager;

    public UserInfoManager(BaseActivity c) {
        super(c);
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
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
//            userReChargeManager = new UserReChargeManager(context);
//            userReChargeManager.sendEmptyMessage(60);
            // context.setSubManager(userReChargeManager);
            break;
        case 2:// 订阅
//            if (!Utils.isNetworkValidate(context)) {
//                showAlert("网络不可用,请检查您的网络！");
//                return;
//            }
//            Application.subscriptionManager.setSelectNum(0);
//            Application.subscriptionManager.sendEmptyMessage(1);
            // Application.application.setSubManager(Application.subscriptionManager);
            break;
        case 3:// 好友
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
//            userFriendProcessManager = new MoreUserFriendMng(context);
//            userFriendProcessManager.sendEmptyMessage(MoreUserFriendMng.MSG_WHAT_FETCH_FRIENDS_LIST);
            // context.setSubManager(userFriendProcessManager);
            break;
        case 4:// 关于帮助
        	Application.application.setSubManager(Application.aboutManager);
            break;
        case 5:// 收藏
            if (!Utils.isNetworkValidate(context)) {
                showAlert("网络不可用,请检查您的网络！");
                return;
            }
//            Application.favoritesManager.getFavorites(0);
            // Application.application.setSubManager(Application.favoritesManager);
            break;
        case 6:// 重新下载
//            Application.application.setSubManager(Application.downloadProductManager);
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
