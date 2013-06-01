package com.lovepig.main;

import org.json.JSONStringer;

import android.app.Activity;
import android.content.Context;

import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;
import com.lovepig.utils.Utils;

public class Configs {
    public static final String lovePigPath = Utils.getSdcardPath() + "/lovepig/";

    /**
     * 图片存储
     */
    public static String lovePigImageCache = lovePigPath + "Cache/";
    public static final String DATABASE_NAME = "content.db";// 数据库名

    /**
     * 邮箱和电话号码的正则表达式
     */
    public static final String EmailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9]*[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    public static final String PhonePattern = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";


    public static final String HostName2[] = { "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/" };

    public static final String HostName1[] = { "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/" };


    
    /**
     * 是否开启程序日志
     */
    public static boolean isDebug = true;
   
    public static final String PREFS_NAME = "TlPrefsFile";
    public static final String PREFS_UserInfo = "UserInfo";

    public static String u_ShowPopStr = "";// 服务器连接信息
    public static String u_Url = "";// 升级地址
    public static String u_Discribe;
    public static String userid = "123";// 用户id
    public static String readerUrl = null;// 阅读器下载地址
    public static String readerpackageName = "com.founder.readerforrd";// 阅读器包名称
    public static int readerVersionCode = -1;// 阅读器版本号
    public static String mUser_Name;// 用户账号
    public static String mUser_Email;// 用户邮箱
    public static String mUser_PhoneNum;// 用户手机号
    public static int mMemberId;// 用户手机号
    public static String mCheckCode = "1234";// 验证码
  
    

    public static final int MORE_LIST_DATA = 50;
    public static final int PRE_LIST_DATA = 51;
    public static final int JUMP_LIST_PAGE = 52;
    public static final int CLICK_LIST_ADVER_IMAGE = 53;
    
   

    /**
     * 用户相关
     */
    public static final String getRechargeListAction = "/xhs/getRechargeList.action";// 获取充值记录
    public static final String commentAction = "/xhs/comment.action";// 发送用户评价
    public static final String getCommentAction = "/xhs/getComment.action";// 获取用户评价
    public static final String accountAction = "/xhs/account.action";// 用户信息
    public static final String UserInfoaccountAction = "/xhs/getAccount.action";// 用户信息
    public static final String RegisterUser = "user/register";// 注册用户
    public static final String updateUserInfo = "user/updateUserInfo";// 修改用户信息
    public static final String quicklyRegister = "user/quicklyRegister";// 快速注册
    public static final String loginUser = "user/login";// 用户
    public static final String CheckUsernameAction = "/xhs/checkUsername.action";// 验证用户文号
    public static final String ModifyUserPwdAction = "/xhs/changePassword.action";// 修改密码
    public static final String TobindingAction = "/xhs/binding.action";// 用户绑定
    public static final String UnbindingAction = "/xhs/unpinless.action";// 解除绑定

    public static final String GET_LIBRARY_CARD = "/xhs/getLibraryCard.action";// 会员中心
    public static final String Join_Library = "/xhs/joinLibrary.action";// 会员中心

	public static  int UPDATE_FLAG = 0;

    
    public static String typeAndVsersion = null;
    public static String oldtypeAndVsersion = null;

    /**
     * 栏目根目录
     */
    public static String categoryRootId = "0";
    /**
     * 是否修改第一次系统分配的密码(含系统重置密码) 0为未修改默认密码 1为修改过默认密码
     */
    public static int isChangededPWD = 0;
    // public static String certificateId;

    public static String IMEI = null;
    public static int userState;
    public static int userLevel;
    public static String certificate;
    public static int bingding;

    public static void initTypeAndVsersion(Activity context) {
        try {
          
            JSONStringer stringer = new JSONStringer();
            stringer.object();
            stringer.key("product").value("XHPM");
            stringer.key("clienttype").value("Android");
            stringer.key("clientversion").value("2.0.004");
            stringer.key("model").value(Utils.getMobileModel());
            stringer.key("resolution").value(Utils.getScreenWidth(context) + "X" + Utils.getScreenHeight(context));
            stringer.key("systemversion").value(Utils.getSDKVersion());
            stringer.key("channel").value("DEV");
            stringer.key("updatechannel").value("2");
            IMEI = Utils.getIMEI(context);
            LogInfo.LogOut("imei:" + IMEI);
            stringer.key("imei").value(IMEI);
            stringer.key("imsi").value(Utils.getIMSI(context) == null ? "" : Utils.getIMSI(context));
            stringer.key("login").value(0);
            stringer.key("memberId").value(2);
            stringer.key("language").value(Utils.getLocalLanguage());
            typeAndVsersion = stringer.endObject().toString();
            oldtypeAndVsersion = typeAndVsersion;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUidToTypeAndVsersion(String uid, String username, int memberId) {
        Json json = new Json(oldtypeAndVsersion);
        if (uid != null) {
            json.put("userid", uid);
            json.put("userId", uid);
            json.put("userName", username);
            json.put("memberId", memberId);
            mUser_Name = username;
            mMemberId = memberId;
        }
        typeAndVsersion = json.toNormalString();
    }

    public static void setTxtGravity(Context c, int gravity) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("gravity", gravity).commit();
    }

    public static int getTxtGravity(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getInt("gravity", 0);
    }

    public static void setTxtSize(Context c, int size) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("txtsize", size).commit();
    }

    /**
     * 设置消息个数
     * 
     * @param c
     * @param size
     */
    public static void setTipSize(Context c, int size) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("tipSize", size).commit();
    }

    /**
     * 获取消息个数
     * 
     * @param c
     * @return
     */
    public static int getTipSize(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getInt("tipSize", 0);
    }

    public static int getTxtSize(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getInt("txtsize", 25);
    }

   

    /**
     * 把IMEI保存到本地
     * 
     * @param c
     * @return
     */
    public static String getIMEI(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getString("IMEI", "");
    }

    public static void setIMEI(Context c, String imei) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putString("IMEI", imei).commit();
    }




}
