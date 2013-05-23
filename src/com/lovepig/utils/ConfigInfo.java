package com.lovepig.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import com.lovepig.main.Application;
import com.lovepig.main.Configs;

/**
 * 整个应用程序的配置文件，修改配置项即可适应不同的版本 不同的版本需要修改的： factory code. versionName.
 * 
 * ***/
public class ConfigInfo {
    public static String LOGTAG = "ConfigInfo";

    public static String sPlatformFile = "PlatformCert.der";
    // 新华瑞德bossurl：
    public static final String XHRD_BOSSURL = "http://www.xinhuapinmei.com:7001/web/";
    // public static final String
    // XHRD_BOSSURL="http://219.141.223.61:7001/web/";
    // public static final String
    // XHRD_BOSSURL="http://192.168.248.11:7001/web/";
    // factory code (houzhuang)（不同的厂商码）
    public static final String FACTORY_CODE = "10078";

    public static final String CONFIG_CERT_PATH_1 = "/data/xhrd/";
    // public static final String
    // CONFIG_CERT_PATH_2="/data/data/com.sansec/files/";

    public static final String JS_QUOTEDMAK = "IPad";
    public static final String ALERT_TITLE = "提示";

    private static SharedPreferences cfg;
    private static Editor cfgEdit;

    // 完整版的包名
    private static final String MAIN_APK_PACKAGE = "com.xhpm.audiocn.main";

    // get TerminalSpecID (changeable)
    public static String getSpecID() {
        if ("MH900".equals(android.os.Build.MODEL)) {
            return "SMP_XHPM_" + android.os.Build.MODEL;// for moto.
        }

        return android.os.Build.MODEL.replaceAll("\\s", "");// default
    }

    // get TerminalID;(changeable)
    public static String getTerminalID() {
        if (CONFIG_CERT_PATH_1.equals(getCertBasePath())) {
            return getCertificateId();
        }
        cfg = getcfg();
        String terminalId = cfg.getString("TerminalID", "");
        return terminalId;
    }

    public static String getHomeDirFromModel() {

        if ("MH900".equals(android.os.Build.MODEL)) {// 留待拓展,可根据机型定义不同根目录
            return Configs.tlcyMusicPath;
        } else {
            return Configs.tlcyMusicPath;
        }
    }

    // cfg exists?
    public static boolean isCfgExist() {
        String cfgPath = "/data/data/"  + "/shared_prefs/cfgInfo.xml";
        LogInfo.LogOut("the cfgpath is " + cfgPath);
        File file = new File(cfgPath);
        return file.exists();
    }

    // Create SharedPreferences Object.
    private static SharedPreferences getcfg() {
        return Application.application.getSharedPreferences("ctfInfo", 0);
    }

    // get the editable SharedPreferences Object.
    private static Editor getEditor() {
        return getcfg().edit();
    }

    // create config file;
    public static boolean CreatCfgfile(String TerminalId) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("TerminalSpecID", getSpecID());
        cfgEdit.putString("TerminalID", TerminalId);
        cfgEdit.putString("HomeDir", getHomeDirFromModel());

        y = cfgEdit.commit();
        LogInfo.LogOut("[ConfigInfo Create:]--TerminalID---" + TerminalId);
        return y;

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

    // set terminalSpecId into config file.
    public static boolean setSpecID(String specId) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("TerminalSpecID", specId);
        y = cfgEdit.commit();
        return y;
    }

    // get terminal specId from config file.
    public static String getTerminalSpecID() {
        cfg = getcfg();
        String specId = cfg.getString("TerminalSpecID", getSpecID());
        return specId;
        // return "IPad2";
    }

    // get userInfo from config file.
    public static String[] getUserInfo() {
        String[] userInfo = new String[2];
        cfg = getcfg();
        userInfo[0] = cfg.getString("MemberAccount", null);
        userInfo[1] = cfg.getString("MemberPassword", null);
        return userInfo;
    }

    // get homeDir from config file.
    public static String getHomeDir() {
        cfg = getcfg();
        String homeDir = cfg.getString("HomeDir", getHomeDirFromModel());
        return homeDir;

    }

    // set homedir into config file.
    public static boolean setHomeDIr(String homeDir) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("HomeDir", homeDir);
        y = cfgEdit.commit();
        return y;
    }

    // set TerminalID;
    public static boolean setTerminalID(String terminalID) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("TerminalID", terminalID);
        y = cfgEdit.commit();
        return y;
    }

    // get last updatedownlist time
    public static String getLastUpdateDownlistTime() {
        cfg = getcfg();
        String time = cfg.getString("LastUPTime", "");
        return time;
    }

    // set last updatedownlist time
    public static boolean setLastUpdateDownlistTime(String time) {
        boolean y = false;
        cfgEdit = getEditor();
        cfgEdit.putString("LastUPTime", time);
        y = cfgEdit.commit();
        return y;
    }

    // get platFormCert path.
    public static String getCertFile() {
        return ConfigInfo.getCertBasePath() + "PlatformCert.der";
    }

    public static byte[] readPlatformCert() {
        byte[] sPlatformCertBuffer;
        try {
            File fPlatformCert = new File("/temp");
            InputStream in = new FileInputStream(fPlatformCert);
            sPlatformCertBuffer = new byte[in.available()];
            in.read(sPlatformCertBuffer);
            in.close();
        } catch (Exception e) {
            LogInfo.LogOut("read platform cert exception");
            e.printStackTrace();
            return null;
        }
        return sPlatformCertBuffer;
    }

    public static void writePrivateFile(InputStream is, String out) {
//        try {
//            OutputStream oo = com.xhpm.audiocn.main.Application.application.openFileOutput(out, Context.MODE_PRIVATE);
//            byte[] buffer = new byte[4 * 1024];
//            int temp;
//            while ((temp = is.read(buffer)) != -1) {
//                oo.write(buffer, 0, temp);
//            }
//            oo.flush();
//            is.close();
//            oo.close();
//        } catch (Exception e) {
            LogInfo.LogOut("writeCertFile exception");
//            e.printStackTrace();
//        }
    }

    public static void writePrivateFile(String sFileName, String out) {
//        try {
//            InputStream is = com.xhpm.audiocn.main.Application.application.getAssets().open(sFileName);
//            writePrivateFile(is, out);
//        } catch (Exception e) {
//            LogInfo.LogOut("writeCertFile exception");
//            e.printStackTrace();
//        }
    }

    public static void writePrivateFile(byte[] buffer, String out) {
        if (buffer == null || out == null) {
            LogInfo.LogOut("writeCertFile parameters null");
            return;
        }
        InputStream is = new ByteArrayInputStream(buffer);
        writePrivateFile(is, out);
    }

    // get IMEI
    public static String getIMEI() {
//        TelephonyManager tm = (TelephonyManager) com.xhpm.audiocn.main.Application.application.getSystemService(Context.TELEPHONY_SERVICE);
//        LogInfo.LogOut("IMEI:" + tm.getDeviceId());
//        String sIMEI = tm.getDeviceId();
//        // String sIMEI=new Random().nextInt()+"";
//        if (sIMEI != null && (sIMEI.length() >= 6)) {
//            if (sIMEI.substring(0, 6).equals("000000")) {
//                sIMEI = null;
//            }
//        } else {
//            sIMEI = null;
//        }
//        // return "gaoyang12345678";
//        return Configs.IMEI.replace("IMEI", "");//----------此处（获取证书的）的IMEI和鉴权时的不一致，出现鉴权不成功，现在暂时统一为鉴权时用的IMEI-BAOSHUJUN---------
    	return null;
    }

    // get MAC address
    public static String getMacAddress() {
//        WifiManager wifi = (WifiManager) com.xhpm.audiocn.main.Application.application.getSystemService(Context.WIFI_SERVICE);
//        WifiInfo info = wifi.getConnectionInfo();
//        LogInfo.LogOut("MAC:" + info.getMacAddress());
//        return info.getMacAddress();
    	return null;
    }

    // 获取证书路径，证书预装或后装。
    public static String getCertBasePath() {
        // cert path

        // 1 /data/xhrd/
        // 2 /data/data/com.sansec/files/
        String[] sPath = new String[2];
        sPath[0] = CONFIG_CERT_PATH_1;
        sPath[1] = getAppFilePath();

        // DeviceKey
        // DeviceCert
        // PlatformCert.der
        // Root.der
        String[] sFile = new String[4];
        sFile[0] = new String("DeviceKey");
        sFile[1] = new String("DeviceCert");
        sFile[2] = new String("PlatformCert.der");
        sFile[3] = new String("Root.der");

        boolean bLackFile = false;
        for (int i = 0; i < sPath.length; ++i) {
            bLackFile = false;
            File path = new File(sPath[i]);
            if (path.exists()) {
                for (int j = 0; j < sFile.length; ++j) {
                    File file = new File(sPath[i] + sFile[j]);
                    if (!file.exists()) {
                        //
                        bLackFile = true;
                    }
                }
                // cert file ok, return path
                if (!bLackFile) {
                    LogInfo.LogOut("ConfigInfo.getCertBasePath() return : " + sPath[i]);
                    return sPath[i];
                }
            }
        }

        return getAppFilePath();

    }

    public static String getFirstTerminalID() {
        if (CONFIG_CERT_PATH_1.equals(getCertBasePath())) {
            return getCertificateId();
        }
        String terminalId = null;
       String sIMEI = ConfigInfo.getIMEI();
      
        String sMAC = ConfigInfo.getMacAddress();
        if (sIMEI != null) {
            sMAC = null;
        }

        terminalId = getTerminalIDfrom2(sIMEI, sMAC);
        return terminalId;
    }

    // 获取证书号。
    public static String getCertificateId() {
//        DRMAgent drmAgent = new DRMAgent(ConfigInfo.getCertBasePath() + "DeviceCert", ConfigInfo.getCertBasePath() + "DeviceKey");
//
//        String sCertificateId = null;
//        try {
//            drmAgent.DRM_VerifyPIN("123456".getBytes());
//            sCertificateId = drmAgent.DRM_GetCertID();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return sCertificateId;
    	return null;
    }

    /**
     * 根据IMEI和mac来确定终端的terminalID。 若IMEI存在，则为"IMEI" + sIMEI；(不管mac存在与否)
     * 若IMEI不存在，mac存在，则为"MAC" + sMAC; 都不存在，则为null；
     */
    public static String getTerminalIDfrom2(String sIMEI, String sMAC) {

        String terminalId = null;

        if (sIMEI == null) {
            if (sMAC != null) {
                return "MAC" + sMAC;
            }
        } else {
            return "IMEI" + sIMEI;
        }
        return terminalId;
    }

    /*
     * //the versioncode. public static String getCurrentVersion(){ //return
     * (new String("2011022101")); return ConfigInfo.CONFIG_VERSION_CODE; }
     * //versionName. public static String getDesVersion(){ return
     * ConfigInfo.CONFIG_VERSION_NAME; }
     */
    // 获取下载列表地址。
    public static String getDownloadListUrl() {

        return XHRD_BOSSURL + "boss/downloadList.do?TerminalSpecID=" + ConfigInfo.getTerminalSpecID() + "&TerminalID=" + ConfigInfo.getTerminalID();

    }

    // 更新下载任务url.
    public static String getResUrl(String downloadItemId, int downStatus) {

        return XHRD_BOSSURL + "boss/downloadTaskSts.do?TerminalSpecID=" + getTerminalSpecID() + "&TerminalID=" + getTerminalID() + "&DownloadItemID=" + downloadItemId
                + "&DownloadStatus=" + downStatus;
    }

    // 更新版本的URL
    public static String getVersionUrl(String sApkType) {
        return XHRD_BOSSURL + "boss/versionInfos.do?SoftFacCode=SanWei&TerminalSpecID=" + getTerminalSpecID() + "&ApkType=" + sApkType;
    }

    // 应用程序files目录
    public static String getAppFilePath() {
//        return "/data/data/" + com.xhpm.audiocn.main.Application.application.getPackageName() + "/files/";
    	return null;
    }

    public static String getMainPackage() {
        return MAIN_APK_PACKAGE;
    }

    public static String getMainTitle() {
        String text = "";
        if (!"".equals(getMemberAccount())) {
            text = "你好，" + getMemberAccount();
        }
        return text;
    }

    public static String getDesVersion() {
//        return getComponentVersion(com.xhpm.audiocn.main.Application.application.getPackageName());
    	return null;
    }

    private static String getComponentVersion(String sPackageName) {
        String sVersion = "";
//        try {
//            sVersion = com.xhpm.audiocn.main.Application.application.getPackageManager().getPackageInfo(sPackageName, 0).versionName;
//        } catch (NameNotFoundException e) {
//            e.printStackTrace();
//
//        }

        return sVersion;
    }
}