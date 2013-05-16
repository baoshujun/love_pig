package com.lovepig.main;

import com.lovepig.utils.Utils;

public class Configs {
    public static final String lovePigPath = Utils.getSdcardPath() + "/lovepig/";
    /**
     * 是否开启程序日志
     */
    public static boolean isDebug = true;

    /**
     * 图片存储
     */
//    public static String lovePigImagePath = lovePigPath + "Image/";
    public static String lovePigImageCache = lovePigPath + "Cache/";
    public static final String DATABASE_NAME = "content.db";// 数据库名

    /**
     * 邮箱和电话号码的正则表达式
     */
    public static final String EmailPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9]*[\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    public static final String PhonePattern = "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";

    /**
     * 当前屏幕横竖屏状态 参考Configuration.ORIENTATION_PORTRAIT
     * Configuration.ORIENTATION_LANDSCAPE
     */
    public static int nowOrientation;
    /**
     * 上一个屏幕状态 参考Configuration.ORIENTATION_PORTRAIT
     * Configuration.ORIENTATION_LANDSCAPE
     */
    public static int lastOrientation;

    /**
     * 0代表不升级，1代表一般升级，2代表强制升级
     */
    public static int UPDATE_FLAG = 0;

    public static final String HostName2[] = { "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/" };

    public static final String HostName1[] = { "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/",
            "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/", "http://test.dreawin.com/pig-api/" };

}
