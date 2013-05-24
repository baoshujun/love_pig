package com.lovepig.manager;

import android.os.Message;
import android.text.TextUtils;
import android.widget.ViewAnimator;

import com.lovepig.engine.UserEngine;
import com.lovepig.main.Application;
import com.lovepig.main.Configs;
import com.lovepig.main.R;
import com.lovepig.pivot.BaseActivity;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.Json;
import com.lovepig.view.UserAccountView;
import com.lovepig.view.UserModiyPWView;
import com.lovepig.view.UserRegisterView;
import com.lovepig.view.UserUpdateUserInfoView;

public class UserManager extends BaseManager {
	public static final int STATE_MODIFYINFO = 0;// 修改用户信息
	public static final int STATE_MODIFYPWD = 1;// 修改用户密码
	public static final int STATE_UNBIND = 2;// 解除绑定
	public static final int STATE_GETUSERINFO = 3;// 获取用户信息
	public static final int STATE_GETUSERINFOSUCESS = 4;// 成功获取到用户信息
	public static final int STATE_GETUSERINFOERROR = 5;// 获取失败
	public static final int STATE_REGISTERSUCESS = 9;// 注册成功
	public static final int STATE_REGISTERFAIL = 10;// 注册失败
	public static final int STATE_BACK = 13;// 返回
	public static final int STATE_UPDATESUCESS = 14;// 修改用户信息成功
	public static final int STATE_UPDATEFAIL = 15;// 修改用户信息失败
	public static final int STATE_MODIFYPWDSUCESS = 16;// 修改密码成功
	public static final int STATE_MODIFYPWDFAIL = 17;// 修改密码失败

	public static final int STATE_NAME_CAN_USE = 18;// 用户名称可用
	public static final int STATE_NAME_USEED = 19;// 用户名称不可用
	private UserAccountView mainDC;
	// private UserBindDC bindDC;
	// private UserUnBindDC mUserUnBindDC;
	private UserRegisterView registerDC;
	private UserEngine engine;
	private UserUpdateUserInfoView mUpdateUserInfoDC;
	private UserModiyPWView mUserModiyPWDC;
	private boolean isGetUserInfo;

	public UserManager(BaseActivity c) {
		super(c);
		if (engine == null) {
			engine = new UserEngine(this);
		}
		initDC();
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case STATE_MODIFYINFO:
			break;
		case STATE_MODIFYPWD:
			break;
		case STATE_UNBIND:
			break;
		case STATE_GETUSERINFO:
//			 getUserInfo();
			 goToRegister();
			break;
		case STATE_GETUSERINFOSUCESS:
			UserInfoSucess();
			Application.application.setSubManager(this);
			break;
		case STATE_GETUSERINFOERROR:
			if (msg.obj != null) {
				if (((String) msg.obj).equals("regist")) {
					Application.application.setSubManager(this);
				} else {
					showToast((String) msg.obj);
				}
			} else {
				showAlert("网络不可用,请检查您的网络！");
				// back();
			}
			dismissLoading();
			break;
		case STATE_REGISTERSUCESS:
			showToast("注册成功");
			back();
			dismissLoading();
			break;
		case STATE_REGISTERFAIL:
			if (msg.obj != null) {
				showAlert((String) msg.obj);
			} else {
				showAlert(context.getString(R.string.netconnecterror));
			}
			dismissLoading();
			break;
		case STATE_BACK:
			Back();
			break;
		case STATE_UPDATESUCESS:
			dcEngine.setMainDC(mainDC);
			mainDC.setUserInfo();
			showToast("修改成功");
			dismissLoading();
			break;
		case STATE_UPDATEFAIL:
			if (msg.obj != null) {
				showToast((String) msg.obj);
			} else {
				showToast(context.getString(R.string.netconnecterror));
			}
			dismissLoading();
			break;
		case STATE_MODIFYPWDSUCESS:
			dcEngine.setMainDC(mainDC);
			showToast("修改密码成功");
			dismissLoading();
			break;
		case STATE_MODIFYPWDFAIL:
			if (msg.obj != null) {
				showToast((String) msg.obj);
			} else {
				showAlert(context.getString(R.string.netconnecterror));
			}
			dismissLoading();
			break;
		case STATE_NAME_CAN_USE:
			dismissLoading();
			showToast("用户名字可用");
			break;
			
		case STATE_NAME_USEED:
			dismissLoading();
			showToast("该用户名字已被使用！");
			break;
		}

	}

	public void Back() {
		if (getNowShownDC() == mUpdateUserInfoDC
				|| getNowShownDC() == mUserModiyPWDC) {
			dcEngine.setMainDC(mainDC);
		} else {
			back();
		}
	}

	@Override
	public void onClicked(int id) {
		switch (id) {
		case R.id.user_modifypwd_cancel:
		case R.id.updateuserinfo_cancel:
		case R.id.userunbind_cancel_btn:
		case R.id.binduser_cancel_btn:// 返回
		case R.id.leftBtn:
			sendEmptyMessage(STATE_BACK);
			break;
		case R.id.rightBtn:// 用户绑定
			// registerDC.Reset();
			// enterSubDC(bindDC);
			break;
		case R.id.register_register_btn:// 用户注册
			if (registerDC.checkDataintegrity()) {
				engine.RegisterUser(registerDC.getRegisterInfo());
				showLoading();
			}
			break;
		case R.id.register_register_reset_btn:// 重置
			registerDC.Reset();
			break;
		case R.id.register_userid_checked_btn:// 检测名称
			if (registerDC != null) {
				if (!TextUtils.isEmpty(registerDC.getUserAccount())) {
					showLoading();
					engine.checkUserAccount(registerDC.getUserAccount());
				} else {
					showToast("用户名称不能为空！");
				}
			}
			break;
		case R.id.account_modifyinfo:// 修改个人信息
			if (Configs.userid == null) {
				dcEngine.setMainDC(registerDC);
			} else {
				mUpdateUserInfoDC.setUserInfo();
				enterSubDC(mUpdateUserInfoDC);

			}
			break;
		case R.id.account_modifypwd:// 修改用户密码
			if ( Configs.userid == null) {
				dcEngine.setMainDC(registerDC);
			} else {
				mUserModiyPWDC.setUserID();
				enterSubDC(mUserModiyPWDC);
				mUserModiyPWDC.setOldPasswdVisibility();
			}

			break;
		case R.id.updateuserinfo_ok:
			UpdateUserInfo();
			break;
		case R.id.user_modifypwd_ok:
			ModifyPWD();
			break;
		default:
			break;
		}

	}

	public void getUserInfo() {
		showLoading();
		engine.getUserInfo();
	}

	/**
	 * 成功获取用户信息
	 */
	public void UserInfoSucess() {
		isGetUserInfo = true;
		mainDC.setUserInfo();
		dismissLoading();
	}

	/**
	 * 去注册
	 */
	public void goToRegister() {
		isGetUserInfo = false;
		dcEngine.setMainDC(registerDC);
	}

	/**
	 * 修改用户信息
	 */
	public void UpdateUserInfo() {
		Json j = mUpdateUserInfoDC.getUserInfo();
		if (j != null) {
			showLoading();
			engine.UpdateUserInfo(j);
		}
	}

	/**
	 * 修改密码
	 */
	public void ModifyPWD() {
		Json j = mUserModiyPWDC.getPwd();
		if (j != null) {
			showLoading();
			engine.ModifyUserPWD(j);
		}
	}

	/**
	 * 用户绑定
	 */
	public void ToBinding() {
		// Json j = bindDC.getBindingInfo();
		// if (j != null) {
		// showLoading();
		// engine.ToBinding(j);
		// }
	}

	/**
	 * 取消绑定
	 */
	public void UnBind() {
		// Json j = mUserUnBindDC.getUserPWD();
		// if (j != null) {
		// showLoading();
		// engine.UnBinding(j);
		// }
	}

	@Override
	public boolean backOnKeyDown() {
		sendEmptyMessage(STATE_BACK);
		return true;
	}

	private void initDC() {
		if (mainDC == null) {
			mainDC = new UserAccountView(context, R.layout.user_account, this);
		}
		if (registerDC == null) {
			registerDC = new UserRegisterView(context, R.layout.user_register,
					this);
		}
		
		if (mUserModiyPWDC == null) {
			mUserModiyPWDC = new UserModiyPWView(context,
					R.layout.user_modifypwd, this);
		}
		if (mUpdateUserInfoDC == null) {
			mUpdateUserInfoDC = new UserUpdateUserInfoView(context,
					R.layout.user_updateuserinfo, this);
		}
	}

	@Override
	public void onLoadingCacel() {
//		if (!Configs.isCheckin) {
//			engine.onPostHttp();
//			back();
//		}
		engine.stopCheckUserId();
		engine.StopUpdateUserInfo();
		engine.StopUserInfo();
	}

	/**
	 * 该设备没有绑定用户时调用
	 */
	public void ToBindOrRegister(int backtxtid) {
		Application.application.setSubManager(this);
		registerDC.setBackText(backtxtid);
	}

	/**
	 * 进入用户管理
	 * 
	 * @param backtxtid
	 */
	public void EnterUserManager(int backtxtid) {
		if ( Configs.userid == null || !isGetUserInfo) {
//			sendEmptyMessage(STATE_GETUSERINFO);
			Application.application.setSubManager(this);
		} else {
			Application.application.setSubManager(this);
		}
		registerDC.setBackText(backtxtid);
	}

	@Override
	public ViewAnimator getMainDC() {
		if (Configs.userid != null) {
			dcEngine.setMainDC(mainDC);
		} else {
			goToRegister();
		}
		// mainDC.onShow();
		return super.getMainDC();
	}
}
