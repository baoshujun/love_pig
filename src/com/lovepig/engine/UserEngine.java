package com.lovepig.engine;

import android.os.AsyncTask;
import android.util.Log;

import com.lovepig.main.Application;
import com.lovepig.main.Configs;
import com.lovepig.manager.UserManager;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.utils.ConfigInfo;
import com.lovepig.utils.Json;
import com.lovepig.view.UserLoginView;

public class UserEngine extends BaseEngine {
    UserManager manager;
    UserInfoTask mUserInfoTask;// 获取用户信息
    RegisterUserTask mRegisterUserTask;// 用户注册
    UpdateUserInfoTask mUpdateUserInfoTask;// 修改用户信息
    ModifyPWDTask mModifyPWDTask;// 修改密码
    CheckUserIdTask checkUserIdTask;
    LoginTask mLoginTask;
    
    private Json userInfo = null;

    public UserEngine(UserManager manager) {
        super(manager);
        this.manager = manager;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
        StopUserInfo();
        mUserInfoTask = new UserInfoTask();
        mUserInfoTask.execute();
    }

    public void StopUserInfo() {
        if (mUserInfoTask != null) {
            mUserInfoTask.Stop();
            mUserInfoTask = null;
        }
    }
    
    /**
     * 用户登陆
     * 
     * @param j
     */
    public void login(Json j) {
    	stopLogin();
    	userInfo = j;
    	StringBuilder mStrBuilder = new StringBuilder("?");
    	mStrBuilder.append("userName=").append(j.getString("userName"))
    	.append("&pwd=").append(j.getString("pwd"));
    	mLoginTask = new LoginTask();
    	mLoginTask.execute(mStrBuilder.toString());
    }

    /**
     * 用户注册
     * 
     * @param j
     */
    public void RegisterUser(Json j) {
        StopRegister();
        userInfo = j;
        StringBuilder mStrBuilder = new StringBuilder("?");
		mStrBuilder.append("userName=").append(j.getString("userName"))
				.append("&pwd=").append(j.getString("pwd")).append("&userEmail=")
				.append(j.getString("userEmail")).append("&userPhoneNum=")
				.append(j.getString("userPhoneNum"));
        mRegisterUserTask = new RegisterUserTask();
        
        mRegisterUserTask.execute(mStrBuilder.toString());
    }

    /**
     * 停止登陆
     */
    private void stopLogin() {
    	if (mRegisterUserTask != null) {
    		mRegisterUserTask.Stop();
    		mRegisterUserTask = null;
    	}
    }
    /**
     * 停止注册
     */
    private void StopRegister() {
        if (mRegisterUserTask != null) {
            mRegisterUserTask.Stop();
            mRegisterUserTask = null;
        }
    }

    /**
     * 修改用户信息
     * 
     * @param j
     */
    public void upDateUserInfo(Json j) {
    	userInfo = j;
    	StringBuilder mStrBuilder = new StringBuilder("?");
		mStrBuilder.append("userName=").append(j.getString("userName"))
				.append("&pwd=").append(j.getString("pwd")).append("&userEmail=")
				.append(j.getString("userEmail")).append("&userPhoneNum=")
				.append(j.getString("userPhoneNum"));
        StopUpdateUserInfo();
        mUpdateUserInfoTask = new UpdateUserInfoTask();
        mUpdateUserInfoTask.execute(mStrBuilder.toString());
    }

    public void StopUpdateUserInfo() {
        if (mUpdateUserInfoTask != null) {
            mUpdateUserInfoTask.Stop();
            mUpdateUserInfoTask = null;
        }
    }

    /**
     * 修改密码
     * 
     * @param j
     */
    public void ModifyUserPWD(Json j) {
        if (mModifyPWDTask != null) {
            mModifyPWDTask.Stop();
            mModifyPWDTask = null;
        }
        mModifyPWDTask = new ModifyPWDTask();
        mModifyPWDTask.execute(j.toString());

    }

    /**
     * 解除绑定
     * 
     * @param j
     */
//    public void UnBinding(Json j) {
//        stopUnbindTask();
//        mUnbindTask = new UnbindTask();
//        mUnbindTask.execute(j.toString());
//    }

//    public void stopUnbindTask() {
//        if (mUnbindTask != null) {
//            mUnbindTask.Stop();
//            mUnbindTask = null;
//        }
//    }

    /**
     * 用户绑定
     * 
     * @param j
     */
//    public void ToBinding(Json j) {
//        stopToBiinding();
//        mToBindTask = new ToBindTask();
//        mToBindTask.execute(j.toString());
//    }

//    public void stopToBiinding() {
//        if (mToBindTask != null) {
//            mToBindTask.Stop();
//            mToBindTask = null;
//        }
//    }

    /**
     * 获取用戶信息
     * 
     * @author DCH
     * 
     */
    class UserInfoTask extends AsyncTask<Void, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(Void... params) {
            Application.checkUserManager.checkUser();
            if (iStop ) {
                return null;
            } else if (Configs.userid == null) {
                return "regist";
            }
            return httpRequestThisThread(1, Configs.UserInfoaccountAction + new Json(0).toString(),false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            if (result != null && result.equals("regist")) {
                manager.sendMessage(manager.obtainMessage(UserManager.STATE_GETUSERINFOERROR, result));
            } else if (result != null) {
                Json j = new Json(result);
                if (j.getInt("result") == 1) {
                    if (j.getInt("binding") == 1) {
                        Configs.mUser_Name = j.getString("userName");
                        try {
                            Configs.mUser_Email = j.getString("email");
                        } catch (Exception e) {
                        }
                        try {
                            Configs.isChangededPWD = j.getInt("isChangededPWD");
                        } catch (Exception e1) {

                            e1.printStackTrace();
                        }
                        try {
                            Configs.mUser_PhoneNum = j.getString("phone");
                        } catch (Exception e) {
                        }
                        manager.sendEmptyMessage(UserManager.STATE_GETUSERINFOSUCESS);
                    }
                } else if (j.getInt("result") == 0) {
                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_GETUSERINFOERROR, j.getString("msg")));
                }
            } else {
                manager.sendEmptyMessage(UserManager.STATE_GETUSERINFOERROR);
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }

    /**
     * 用户注册
     * 
     * @author DCH
     * 
     */
    class RegisterUserTask extends AsyncTask<String, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(String... params) {
        	Log.d("LKP", params[0]);
            return httpRequestThisThread(1, Configs.RegisterUser + params[0],true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            if (result != null) {
                Json j = new Json(result);
                if (j.getInt("status") == 1) {
                    Configs.userid = userInfo.getString("userName");
                    Configs.mUser_Name = userInfo.getString("userName");
//                  Configs.updateUidToTypeAndVsersion(Configs.userid, Configs.mUser_Name, j.getInt("memberId"));
                	ConfigInfo.setUserInfo(userInfo.getString("userName"), userInfo.getString("pwd"));
                    manager.sendEmptyMessage(UserManager.STATE_REGISTERSUCESS);
                } else {
                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_REGISTERFAIL, j.getString("msg")));
                }
            } else {
                manager.sendEmptyMessage(UserManager.STATE_REGISTERFAIL);
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }
    
    /**
     * 用户注册
     * 
     * @author LKP
     * 
     */
    class LoginTask extends AsyncTask<String, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(String... params) {
        	Log.d("LKP", params[0]);
            return httpRequestThisThread(1, Configs.loginUser + params[0],true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            if (result != null) {
                Json j = new Json(result);
                if (j.getInt("status") == 1) {
                    Configs.userid = j.getString("userName");
                    Configs.mUser_Name = j.getString("userName");
//                  Configs.updateUidToTypeAndVsersion(Configs.userid, Configs.mUser_Name, j.getInt("memberId"));
                	ConfigInfo.setUserInfo(userInfo.getString("userName"), userInfo.getString("pwd"));
                    manager.sendEmptyMessage(UserManager.STATE_REGISTERSUCESS);
                } else {
                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_LOGIN_SUCCESS, j.getString("msg")));
                }
            } else {
                manager.sendEmptyMessage(UserManager.STATE_REGISTERFAIL);
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }

    /**
     * 修改用户信息
     * 
     * @author DCH
     * 
     */
    class UpdateUserInfoTask extends AsyncTask<String, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(String... params) {
            return httpRequestThisThread(1, Configs.updateUserInfo + params[0],true);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            Json j = null;
            if (result != null) {
                j = new Json(result);
                if (j.getInt("result") == 1) {
                    Configs.mUser_Email = userInfo.getString("mail");
                    Configs.mUser_Name = userInfo.getString("userName");
                    Configs.mUser_PhoneNum = userInfo.getString("phone");
                    Configs.updateUidToTypeAndVsersion(Configs.userid, Configs.mUser_Name, Configs.mMemberId);
                    manager.sendEmptyMessage(UserManager.STATE_UPDATESUCESS);
                } else {
                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_UPDATEFAIL, j.getString("msg")));
                }
            } else {
                manager.sendEmptyMessage(UserManager.STATE_UPDATEFAIL);
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }

    /**
     * 修改密码
     * 
     * @author DCH
     * 
     */
    class ModifyPWDTask extends AsyncTask<String, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(String... params) {
            return httpRequestThisThread(1, Configs.ModifyUserPwdAction + params[0],false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            if (result != null) {
                Json j = new Json(result);
                if (j.getInt("result") == 1) {
                    Configs.isChangededPWD = 1;
                    manager.sendEmptyMessage(UserManager.STATE_MODIFYPWDSUCESS);
                } else {
                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_MODIFYPWDFAIL, j.getString("msg")));
                }
            } else {
                manager.sendEmptyMessage(UserManager.STATE_MODIFYPWDFAIL);
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }

    /**
     * 解除绑定
     * 
     * @author DCH
     * 
     */
//    class UnbindTask extends AsyncTask<String, Void, String> {
//        boolean iStop;
//
//        @Override
//        protected String doInBackground(String... params) {
//            String result = httpRequestThisThread(1, Configs.UnbindingAction + params[0]);
//            if (result != null) {
//                Json j = new Json(result);
//                if (j.getInt("result") == 1) {
//                    Configs.userid = null;
//                    Configs.updateUidToTypeAndVsersion(null, null, -1);
//                    Configs.setSpecial(manager.context, false);
//                    Application.application.mainManager.sendEmptyMessage(1);
//                    manager.sendEmptyMessage(UserManager.STATE_UNBINDSUCESS);
//                    Application.application.mainManager.sendMessage(Application.application.mainManager.obtainMessage(MainManager.MSG_WHAT_HAVE_NEW_INFO, 0, 0));
//                    // 清楚所有与用户有关的数据
////                    Application.bookshelfManager.clearDataBases();
//                } else {
//                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_UNBINDFAIL, j.getString("msg")));
//                }
//            } else {
//                manager.sendEmptyMessage(UserManager.STATE_UNBINDFAIL);
//            }
//
//            return result;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//
//        }
//
//        public void Stop() {
//            iStop = true;
//            cancel(iStop);
//        }
//    }

    /**
     * 用户绑定
     * 
     * @author DCH
     * 
     */
//    class ToBindTask extends AsyncTask<String, Void, String> {
//        boolean iStop;
//
//        @Override
//        protected String doInBackground(String... params) {
////            if (!Configs.isCheckin) {
////                Application.checkUserManager.checkUser();
////            }
////            if (!Configs.isCheckin) {
////                return null;
////            }
//            return httpRequestThisThread(1, Configs.TobindingAction + params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            if (iStop) {
//                return;
//            }
//            boolean isSpecial = false;
//            if (result != null) {
//                Json j = new Json(result);
//                if (j.getInt("result") == 1) {
//                    Configs.userid = j.getString("userId");
//                    Configs.readerUrl = j.getString("reader");
//                    Configs.mUser_Name = j.getString("userName");
//                    Configs.mUser_Email = j.getString("mail");
//                    Configs.mUser_PhoneNum = j.getString("phone");
//                    Configs.isChangededPWD = j.getInt("checkedPWD");
//                    if (j.getInt("special") == 1) {
//                        isSpecial = true;
//                    }
//                    Configs.setSpecial(manager.context, isSpecial);
//                    Configs.updateUidToTypeAndVsersion(Configs.userid, Configs.mUser_Name, j.getInt("memberId"));
//                    manager.sendEmptyMessage(UserManager.STATE_TOBINDSUCESS);
//                } else if (j.getInt("result") == 0) {
//                    manager.sendMessage(manager.obtainMessage(UserManager.STATE_TOBINDFAIL, j.getString("msg")));
//                }
//            } else {
//                manager.sendEmptyMessage(UserManager.STATE_TOBINDFAIL);
//            }
//        }
//
//        public void Stop() {
//            iStop = true;
//            cancel(iStop);
//        }
//    }

    public void checkUserAccount(String userAccount) {
        stopCheckUserId();
        checkUserIdTask = new CheckUserIdTask();
        checkUserIdTask.execute(userAccount);

    }

    public void stopCheckUserId() {
        if (checkUserIdTask != null) {
            checkUserIdTask.Stop();
            checkUserIdTask = null;
        }
    }

    /**
     * 修改密码
     * 
     * @author DCH
     * 
     */
    class CheckUserIdTask extends AsyncTask<String, Void, String> {
        boolean iStop;

        @Override
        protected String doInBackground(String... params) {
            Json json = new Json(0);
            json.put("userId", params[0]);
            return httpRequestThisThread(1, Configs.CheckUsernameAction + json.toString(),false);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (iStop) {
                return;
            }
            if (result != null) {
                Json j = new Json(result);
                if (j.getInt("result") == 1) {
                    manager.sendEmptyMessage(UserManager.STATE_NAME_CAN_USE);
                } else {
                    manager.sendEmptyMessage(UserManager.STATE_NAME_USEED);
                }
            } else {
                manager.showToast("网络失败");
            }
        }

        public void Stop() {
            iStop = true;
            cancel(iStop);
        }
    }
}
