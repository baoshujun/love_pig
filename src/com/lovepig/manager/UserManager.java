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
import com.lovepig.view.UserLoginView;
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
	public static final int STATE_LOGIN_SUCCESS = 20;// 用户名称不可用
	
	public static final int STATE_VERIFCATION_CODE = 21;//用户获取验证码
	public static final int STATE_VERIFCATION_CODE_FAIL = 22;//获取验证码失败
	public static boolean isFromPigFactory = false;
	public static String pigFactoryId = null;
	
	
	
	private UserAccountView userAccountView;
	private UserRegisterView registerView;
	private UserEngine engine;
	private UserUpdateUserInfoView updateUserInfoView;
	private UserModiyPWView userModiyPWview;
	private UserLoginView userLoginView;
	private boolean isGetUserInfo;

	public UserManager(BaseActivity c) {
		super(c);
		if (engine == null) {
			engine = new UserEngine(this);
		}
		initView();
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
			if(isFromPigFactory){
				back();
				Application.boarMallManager.sendMessage(Application.boarMallManager.obtainMessage(BoarMallManager.GET_PIG_FACTORY_DETAIL_DATA, pigFactoryId));
			} else {
				back();
				back();
				dismissLoading();
			}
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
			dcEngine.setMainDC(userAccountView);
			userAccountView.setUserInfo();
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
			dcEngine.setMainDC(userAccountView);
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
		case STATE_LOGIN_SUCCESS:
			dismissLoading();
			showToast("登陆成功！");
			back();
			break;
		case STATE_VERIFCATION_CODE://获取验证码
			//联网获取返回码
			dismissLoading();
			showToast("获取验证码成功，请耐心等待短信通知！");
			break;
		case STATE_VERIFCATION_CODE_FAIL://获取验证码失败
			//联网获取返回码
			dismissLoading();
			showToast("因网络原因，获取验证码失败，请重新获取");
			break;
		}

	}

	public void Back() {
		if (getNowShownDC() == updateUserInfoView
				|| getNowShownDC() == userModiyPWview) {
			dcEngine.setMainDC(userAccountView);
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
			if (registerView.checkDataintegrity()) {
				engine.registerUser(registerView.getRegisterInfo());
				showLoading();
			}
			break;
		case R.id.register_register_reset_btn:// 重置
			registerView.Reset();
			break;
		case R.id.register_userid_checked_btn:// 检测名称
			if (registerView != null) {
				if (!TextUtils.isEmpty(registerView.getUserAccount())) {
					showLoading();
					engine.checkUserAccount(registerView.getUserAccount());
				} else {
					showToast("用户名称不能为空！");
				}
			}
			break;
		case R.id.account_modifyinfo:// 修改个人信息
			if (Configs.userid == null) {
				dcEngine.setMainDC(registerView);
			} else {
				updateUserInfoView.setUserInfo();
				enterSubDC(updateUserInfoView);

			}
			break;
		case R.id.account_modifypwd:// 修改用户密码
			if ( Configs.userid == null) {
				dcEngine.setMainDC(registerView);
			} else {
				userModiyPWview.setUserID();
				enterSubDC(userModiyPWview);
				userModiyPWview.setOldPasswdVisibility();
			}
			break;
		case R.id.updateuserinfo_ok://联网修改用户信息
			updateUserInfo();
			break;
		case R.id.user_modifypwd_ok:
			ModifyPWD();
			break;
		case R.id.btn_register://点击登陆页面的注册
//			goToRegister();
			if(userLoginView.checkDataintegrity()) {
				engine.registerUser(userLoginView.getRegisterInfo());
			}
			break;
		case R.id.btn_login://点击登陆页面的登陆
			if (userLoginView.checkLoginData()) {
				engine.login(userLoginView.getLoginInfo());
				showLoading();
			}
			break;
		case R.id.verificationCode://获取用户验证码
			String result = userLoginView.checkPhoneNum();
			if(result != null){
				showLoading();
				engine.getVerificationCode(result);
			}
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
		userAccountView.setUserInfo();
		dismissLoading();
	}

	/**
	 * 去注册
	 */
	public void goToRegister() {
		isGetUserInfo = false;
		enterSubDC(registerView);
	}
	
	/**
	 * 去登陆
	 */
	public void goToLogin() {
		isGetUserInfo = false;
		dcEngine.setMainDC(userLoginView);
		userLoginView.setverificationTextVisible(isFromPigFactory);
	}

	/**
	 * 修改用户信息
	 */
	public void updateUserInfo() {
		Json j = updateUserInfoView.getUserInfo();
		if (j != null) {
			showLoading();
			engine.upDateUserInfo(j);
		}
	}

	/**
	 * 修改密码
	 */
	public void ModifyPWD() {
		Json j = userModiyPWview.getPwd();
		if (j != null) {
			showLoading();
			engine.ModifyUserPWD(j);
		}
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

	private void initView() {
		
		if(userLoginView == null){
			userLoginView = new UserLoginView(context, R.layout.login, this);
		}
		
		if (userAccountView == null) {
			userAccountView = new UserAccountView(context, R.layout.user_account, this);
		}
		
		if (registerView == null) {
			registerView = new UserRegisterView(context, R.layout.user_register,this);
		}
		
		if (userModiyPWview == null) {
			userModiyPWview = new UserModiyPWView(context,R.layout.user_modifypwd, this);
		}
		
		if (updateUserInfoView == null) {
			updateUserInfoView = new UserUpdateUserInfoView(context,
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
	 * 进入用户管理
	 * 
	 * @param backtxtid
	 * @param isFrom 判断是否来自猪场
	 * @param pigFactoryID 猪场的ID
	 */
	public void enterUserManager(int backtxtid,boolean isFrom,String pigFtyID) {
		isFromPigFactory = isFrom;
		pigFactoryId = pigFtyID;
		Application.application.setSubManager(this);
		registerView.setBackText(backtxtid);
	}

	@Override
	public ViewAnimator getMainDC() {
		if (Configs.userid != null) {
			dcEngine.setMainDC(userAccountView);
		} else {
			goToLogin();
		}
		// mainDC.onShow();
		return super.getMainDC();
	}
}
