package com.lovepig.utils;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.lovepig.main.Application;

/**
 * 整个应用程序的配置文件，修改配置项即可适应不同的版本 不同的版本需要修改的： factory code. versionName.
 * 
 * ***/
public class ConfigInfo {
    public static String LOGTAG = "ConfigInfo";
    private static SharedPreferences cfg;
    private static Editor cfgEdit;

    private static SharedPreferences getcfg() {
        return Application.application.getSharedPreferences("ctfInfo", 0);
    }

    private static Editor getEditor() {
        return getcfg().edit();
    }

  

    // set userInfo into config file.
    public static boolean setUserInfo(String userId, String userPassword) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("MemberAccount", userId);
        cfgEdit.putString("MemberPassword", userPassword);
        y = cfgEdit.commit();
        return y;
    }

    public static String getMemberAccount() {
        cfg = getcfg();
        String memberAccount = cfg.getString("MemberAccount", "");
        LogInfo.LogOut("the memacc is " + memberAccount);
        return memberAccount;
    }

    public static boolean setMemberAccount(String sMemberAccount) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("MemberAccount", sMemberAccount);
        y = cfgEdit.commit();
        LogInfo.LogOut("setmemacc is " + sMemberAccount);
        return y;
    }

 
    // get userInfo from config file.
    public static String[] getUserInfo() {
        String[] userInfo = new String[2];
        cfg = getcfg();
        userInfo[0] = cfg.getString("MemberAccount", null);
        userInfo[1] = cfg.getString("MemberPassword", null);
        return userInfo;
    }
}