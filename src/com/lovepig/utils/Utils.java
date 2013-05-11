package com.lovepig.utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.text.format.Formatter;

import com.lovepig.main.R;

/**
 * 这里是工具类
 * 
 * @author  
 */
public class Utils {
    private static String imei = null;
    private static String imsi = null;

     

    /**
     * 获取SD卡路径,一般为/sdcard
     * 
     * @return
     */
    public static String getSdcardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 1为简体,2为繁体,3为英文,4为其他 2011-3-1
     * 
     * @author  
     */
    public static int getLocalLanguage() {
        String country = Locale.getDefault().getCountry();
        String language = Locale.getDefault().getLanguage();
        if ("zh".equalsIgnoreCase(language)) {
            if ("cn".equalsIgnoreCase(country)) {
                return 1;// 简体
            } else {
                return 2;// 繁体
            }
        } else if ("en".equalsIgnoreCase(language)) {
            return 3;// 英文
        } else {
            return 4;// 默认简体
        }
    }

    /**
     * 获取手机IMEI 2010-5-6
     * 
     *  
     */
    public static String getIMEI(Context context) {
        if (imei == null || imei.equals("")) {
            try {
                if (context == null) {
                    return imei;
                }
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                try {
                    // 酷派手机专用获取IMEI号
                    Class<?> su = Class.forName("com.yulong.android.server.systeminterface.util.SystemUtil");
                    Class<?> ptypes[] = new Class[1];
                    ptypes[0] = Class.forName("android.content.Context");
                    Method method = su.getMethod("getIMEI", ptypes);
                    imei = (String) method.invoke(null, context);
                    LogInfo.LogOut("imei<-酷派手机专用获取IMEI号:" + imei);
                } catch (Exception e) {
                    imei = tm.getDeviceId();
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!imei.equals(getIMEI())) {
            // Application.bookshelfManager.showAlert("xhpm-->imei:"+getIMEI()+"  : "+imei);
        }
        return "IMEI" + imei;
    }

    // get IMEI
    public static String getIMEI() {
        TelephonyManager tm = (TelephonyManager) com.lovepig.main.Application.application.getSystemService(Context.TELEPHONY_SERVICE);
        LogInfo.LogOut("IMEI:" + tm.getDeviceId());
        String sIMEI = tm.getDeviceId();
        // String sIMEI=new Random().nextInt()+"";
        if (sIMEI != null && (sIMEI.length() >= 6)) {
            if (sIMEI.substring(0, 6).equals("000000")) {
                sIMEI = null;
            }
        } else {
            sIMEI = null;
        }
        // return "gaoyang12345678";
        return sIMEI;// ----------此处（获取证书的）的IMEI和鉴权时的不一致，出现鉴权不成功，现在暂时统一为鉴权时用的IMEI-BAOSHUJUN---------
    }

    /**
     * 获取sim卡串号 2010-5-10
     * 
     * @author  
     */
    public static String getIMSI(Context context) {
        if (imsi == null || imsi.equals("")) {
            try {
                if (context == null) {
                    return imsi;
                }
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
                imsi = tm.getSimSerialNumber();
                // imsi="000000000000000&customerid=1010101212";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imsi;
    }

    /**
     * 获取手机号 2010-5-6
     * 
     * @author  
     */
    public static String getPhoneNumber(Context c) {
        try {
            TelephonyManager tm = (TelephonyManager) c.getSystemService(Service.TELEPHONY_SERVICE);
            return tm.getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取屏幕宽度
     * 
     * @author  
     */
    public static int getScreenWidth(Activity context) {
        if (context == null) {
            return 321;
        } else {
            return context.getWindowManager().getDefaultDisplay().getWidth();
        }
    }

    /**
     * 获取屏幕高度
     * 
     * @author  
     */
    public static int getScreenHeight(Activity context) {
        if (context == null) {
            return 481;
        } else {
            return context.getWindowManager().getDefaultDisplay().getHeight();
        }
    }

    /**
     * 获取媒体音量 2010-5-27
     * 
     * @author  
     */
    public static int getMusicVolume(Context c) {
        try {
            AudioManager aManager = (AudioManager) c.getSystemService(Context.AUDIO_SERVICE);
            return aManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取系统固件版本,如1.5 2010-5-6
     * 
     * @author  
     */
    public static String getOSVersion() {
        // 2.1_update1太长,服务端存储太小,暂时改用SDK号
        return Build.VERSION.SDK;
        // return Build.VERSION.RELEASE;
    }

    /**
     * 获取api版本,如1.5对应的3 2010-5-6
     * 
     * @author  
     */
    public static String getSDKVersion() {
        return Build.VERSION.SDK;
    }

    /**
     * 获取手机型号 2010-5-6
     * 
     * @author  
     */
    public static String getMobileModel() {
        return Build.MODEL.replaceAll(" ", "");
    }

    /**
     * 构造app版本信息 2010-5-10
     * 
     * @author  
     */
    public static String getAppVersion() {
        return "Android" + getSDKVersion() + "V5";
    }

    /**
     * 判断是否装载的SDcard 2010-4-14
     * 
     * @author  
     */
    public static boolean isSDCard() {
        return Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * sd卡是否还有剩余空间
     * 
     * @author  
     */
    public static boolean isSDCardFree() {
        if (isSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            // 取得sdcard文件路径
            StatFs statfs = new StatFs(path.getPath());
            // 获取block的SIZE
            long blocSize = statfs.getBlockSize();
            // 获取BLOCK数量
            // long totalBlocks=statfs.getBlockCount();
            // 可使用的Block的数量
            long availaBlock = statfs.getAvailableBlocks();
            if (availaBlock * blocSize > 1024 * 1024 * 10) {
                return true;
            }
        }
        return false;
    }

    /**
     * sd卡是否还有剩余空间
     * 
     * @author  
     */
    public static long getSDCardFree() {
        if (isSDCard()) {
            File path = Environment.getExternalStorageDirectory();
            // 取得sdcard文件路径
            StatFs statfs = new StatFs(path.getPath());
            // 获取block的SIZE
            long blocSize = statfs.getBlockSize();
            // 获取BLOCK数量
            // long totalBlocks=statfs.getBlockCount();
            // 可使用的Block的数量
            long availaBlock = statfs.getAvailableBlocks();
            return availaBlock * blocSize / 1024 / 1024;
        }
        return 0;
    }

    /**
     * @return 返回已经格式化的sdcard剩余容量
     */
    public static String getSDCardFreeCapacity() {
        if (isSDCard()) {
            StatFs stat = new StatFs(getSdcardPath());
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return formatSize(availableBlocks * blockSize);
        } else {
            return "0KB";
        }
    }

    public static String timeformat(int hour, int minute) {
        StringBuilder sb = new StringBuilder();
        String h = String.valueOf(hour);
        String m = String.valueOf(minute);
        if (h.length() == 1) {
            sb.append("0");
        }
        sb.append(h).append(":");
        if (m.length() == 1) {
            sb.append("0");
        }
        sb.append(m);

        return sb.toString();
    }

    public static boolean strParamValidate(String str) {
        return str != null && str.length() > 0;
    }

    /**
     * 返回20100416010101格式的时间 2010-4-16
     * 
     * @author  
     */
    public static String returnNowTime() {
        Date date = new Date();
        String timeString = "";
        try {
            timeString = DateFormat.format("yyyyMMddkkmmss", date).toString();

        } catch (Exception e) {
            LogInfo.LogOut("returnNowTime excepiton:" + e.getMessage());
        }
        return timeString;
    }

    /**
     * 将1970格式的时间转换成可读时间yyyyMMddkkmmss 2010-5-25
     * 
     * @author  
     */
    public static long return1970_to_Time(long time) {
        Date date = new Date(time);
        long timeT = time;
        try {
            timeT = Long.parseLong(DateFormat.format("yyyyMMddkkmmss", date).toString());
        } catch (Exception e) {
            LogInfo.LogOut("return1970_to_Time excepiton:" + e.getMessage());
        }
        return timeT;
    }

    /**
     * 对time1和time2进行比较,如果time1在time2之前,则为true 2010-4-20
     * 
     * @author  
     */
    public static boolean returnCompare(String time1, String time2) {
        boolean rs = false;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date1 = sdf.parse(time1);
            Date date2 = sdf.parse(time2);
            return date1.getTime() < date2.getTime();
        } catch (Exception e) {
            LogInfo.LogOut("returnCompare excepiton:" + e.getMessage());
        }
        return rs;
    }

    /**
     * 对yyyy-MM-dd格式的日期,计算两个日期之间的天数 2010-11-2
     * 
     * @author  
     */
    public static int returnCalculateDays(String fromDay, String toDay) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(fromDay);
            Date date2 = sdf.parse(toDay);
            return (int) ((date2.getTime() - date1.getTime()) / 1000 / 60 / 60 / 24);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 对时间进行更改,将time 调整secds秒钟,secds为正时向后延迟,secds为负时向前提前 2010-4-20
     * 
     * @author  
     */
    public static String returnChangeTime(String time, int secds) {
        String timeString = time;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = sdf.parse(time);
            date.setTime(date.getTime() + secds * 1000);
            timeString = sdf.format(date);
            // timeString=DateFormat.format("yyyyMMddkkmmss", date).toString();
        } catch (Exception e) {
            LogInfo.LogOut("returnChangeTime excepiton:" + e.getMessage());
        }
        return timeString;
    }

    /**
     * 删除文件 2010-4-19
     * 
     * @author  
     */
    public static void deleteFile(String fileAllPathAndName) {
        try {
            if (fileAllPathAndName == null) {
                return;
            }
            File file = new File(fileAllPathAndName);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogInfo.LogOut(e.getMessage());
        }
    }

    /**
     * 判断音频文件是否存在 2010-4-29
     * 
     * @author  
     */
    public static boolean isFileExist(String path) {
        if (path == null) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    /**
     * 音频类型,1=mac,2=mp3,3=mv(3gp,mp4),4=other,0=rec
     */
    public static int fileType2Int(String type) {
        if (type == null) {
            return 4;
        } else if (type.toLowerCase().equals("mac")) {
            return 1;
        } else if (type.toLowerCase().equals("mp3")) {
            return 2;
        } else if (type.toLowerCase().equals("3g2")) {
            return 3;
        } else if (type.toLowerCase().equals("rec")) {
            return 0;
        } else {
            return 4;
        }
    }

    /**
     * 文件类型,1=mac,2=mp3,3=3gp,4=other,0=rec
     */
    public static String fileInt2Type(int type) {
        if (type == 1) {
            return "mac";
        } else if (type == 2) {
            return "mp3";
        } else if (type == 3) {
            return "3g2";
        } else if (type == 0) {
            return "rec";
        } else {
            return "other";
        }
    }

    /**
     * 获取mac和mp3格式的id3v1的发行年
     * 
     * @author  
     */
    public static String getId3V1Year(File f) {
        String unkown = "-1";
        try {
            int TAG_SIZE = 128;
            if (!f.exists() || f.length() < TAG_SIZE) {
                return unkown;
            } else {
                RandomAccessFile raf = new RandomAccessFile(f, "r");
                raf.seek(raf.length() - TAG_SIZE);
                byte[] buf = new byte[TAG_SIZE];
                raf.read(buf, 0, TAG_SIZE);
                raf.close();
                String tag = new String(buf, 0, TAG_SIZE, "ASCII");
                if (tag.indexOf("TAG") != 0 || tag.length() != TAG_SIZE) {
                    return unkown;
                } else {
                    if (tag.substring(93, 97).trim().equals("")) {
                        return unkown;
                    }
                    return tag.substring(93, 97);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unkown;
    }

    /**
     * 获取mac和mp3格式的id3v1的风格
     * 
     * @author  
     */
    public static String getId3V1Genre(File f, Context context) {
        String unkown = "-1";
        try {
            int TAG_SIZE = 128;
            if (!f.exists() || f.length() < TAG_SIZE) {
                return unkown;
            } else {
                RandomAccessFile raf = new RandomAccessFile(f, "r");
                raf.seek(raf.length() - TAG_SIZE);
                byte[] buf = new byte[TAG_SIZE];
                raf.read(buf, 0, TAG_SIZE);
                raf.close();
                String tag = new String(buf, 0, TAG_SIZE, "ASCII");
                if (tag.indexOf("TAG") != 0 || tag.length() != TAG_SIZE) {
                    return unkown;
                } else {
                    // int genreid=Integer.parseInt(tag.substring(127));
                    // return
                    // context.getResources().getStringArray(R.array.genres)[genreid];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unkown;
    }

    /**
     * 判断是否是mac格式文件
     * 
     * @author  
     */
    public static boolean isREC(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("rec");
        }
    }

    /**
     * 判断是否是mac格式文件
     * 
     * @author  
     */
    public static boolean isMAC(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("mac");
        }
    }

    /**
     * 判断是否是amr格式文件
     * 
     * @author  
     */
    public static boolean isAMR(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("amr");
        }
    }

    /**
     * 判断是否是mp3格式文件
     * 
     * @author  
     */
    public static boolean isMP3(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("mp3");
        }
    }

    /**
     * 判断是否是mp3格式文件
     * 
     * @author  
     */
    public static boolean isMP4(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("mp4");
        }
    }

    /**
     * 判断是否是amr格式文件
     * 
     * @author  
     */
    public static boolean is3GP(String path) {
        if (path == null) {
            return false;
        } else {
            return path.toLowerCase().endsWith("3gp");
        }
    }

    /**
     * 生成文件下载临时地址
     * 
     * @author  
     */
    public static String buildFinalFileTempPathDown(String realPath) {
        return realPath.substring(0, realPath.lastIndexOf('.')) + ".tmp";
    }

    /**
     * 生成文件播放临时地址
     * 
     * @author  
     */
    public static String buildFinalFileTempPathListen(String realPath) {
        return realPath.substring(0, realPath.lastIndexOf('.')) + ".tmp2";
    }

    /**
     * 生成文件类型 2010-5-10
     * 
     * @author  
     */
    public static String buildFileType(String fileUrl) {
        String type = null;
        try {
            if (fileUrl != null && fileUrl.split("\\.").length > 0) {
                type = fileUrl.split("\\.")[fileUrl.split("\\.").length - 1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * 文件名
     * 
     * @param fileUrl
     * @return
     */
    public static String buildFileName(String fileUrl) {
        String name = null;
        try {
            if (fileUrl != null && fileUrl.split("\\/").length > 0) {
                name = fileUrl.split("\\/")[fileUrl.split("\\/").length - 1];
                if (name != null && name.split("\\.").length > 0) {
                    name = name.split("\\.")[0];
                }
            } else if (fileUrl != null && fileUrl.split("\\.").length > 0) {
                name = fileUrl.split("\\.")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 文件名以及文件类型
     * 
     * @param fileUrl
     * @return
     */
    public static String buildFileNameandType(String fileUrl) {
        String name = null;
        try {
            if (fileUrl != null && fileUrl.split("\\/").length > 0) {
                name = fileUrl.split("\\/")[fileUrl.split("\\/").length - 1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取已经下载的文件长度,用与断点续传 2010-4-28
     * 
     * @author  
     */
    public static long getTempFileLength(String path) {
        if (path == null) {
            return 0;
        } else {
            File file = new File(path);
            if (file.exists()) {
                return file.length();
            } else {
                return 0;
            }
        }
    }

    /**
     * 删除下载临时文件 2010-4-19
     * 
     * @author  
     */
    public static void deleteTempFile(String path) {
        if (path == null) {
            return;
        } else {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    /**
     * 格式化秒数 如36000秒-->10:00:00 2010-5-10
     * 
     * @author  
     */
    public static String formatSeconds(long secs) {
        String string = "00:00";
        try {
            String tString;
            if (secs >= 60 * 60) {
                tString = String.valueOf(secs / 60 / 60);
                tString = tString.length() > 1 ? tString : "0" + tString;
                string = tString + " ： ";// 小时

                tString = String.valueOf(secs % 3600 / 60);
                tString = tString.length() > 1 ? tString : "0" + tString;
                string = string + (tString) + " : ";// 分钟

                tString = String.valueOf(secs % 60);
                tString = tString.length() > 1 ? tString : "0" + tString;
                string = string + tString;
            } else {
                tString = String.valueOf(secs / 60);
                tString = tString.length() > 1 ? tString : "0" + tString;
                string = tString + " : ";

                tString = String.valueOf(secs % 60);
                tString = tString.length() > 1 ? tString : "0" + tString;
                string = string + tString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 格式化秒数 如10:00:00.000-->36000 毫秒数去掉 2010-5-10
     * 
     * @author  
     */
    public static long formatSeconds(String secs) {
        long time = 0;
        try {
            if (secs == null) {
                return 0;
            }
            secs = secs.split("\\.")[0];
            String s[] = secs.split(":");
            if (s.length > 3 || s.length < 1) {
                return 0;
            } else {
                time = s.length == 3 ? Long.parseLong(s[s.length - 3]) * 60 * 60 : time;// 小时
                time = s.length > 1 ? time + Long.parseLong(s[s.length - 2]) * 60 : time;// 分钟
                time = time + Long.parseLong(s[s.length - 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 获取本机语言类型 2010-4-22
     * 
     * @author  
     */
    public static String getLocal() {
        Locale locale = Locale.getDefault();
        // ISO-639 2字母编码格式
        // 英文 en
        // 日文 ja
        // 简体中文 zh
        String langCode = locale.getLanguage();

        return langCode;
    }

    public static void getMEM(Activity activity) {
        LogInfo.LogOut("totalMemory=" + Runtime.getRuntime().totalMemory());
        LogInfo.LogOut("freeMemory=" + Runtime.getRuntime().freeMemory());
        ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        LogInfo.LogOut("availMem=" + Formatter.formatFileSize(activity.getApplicationContext(), mi.availMem));
        LogInfo.LogOut("lowMemory=" + mi.lowMemory);
        LogInfo.LogOut("getAvailableInternalMemorySize=" + formatSize(getAvailableInternalMemorySize()));
        LogInfo.LogOut("getTotalInternalMemorySize=" + formatSize(getTotalInternalMemorySize()));
    }

    static public boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    static public long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    static public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    static public String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null)
            resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    /**
     * 1为中国移动,2为中国联通,3为中国电信,4为其他
     */
    public static int getSimType(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = telManager.getSimOperator();
        if (operator != null) {
            if (operator.equals("46000") || operator.equals("46002")) {
                // 中国移动
                return 1;
            } else if (operator.equals("46001")) {
                // 中国联通
                return 2;
            } else if (operator.equals("46003")) {
                // 中国电信
                return 3;
            }
        }
        return 4;
    }

    /**
     * 网络是否连接
     * 
     * @param context
     * @return
     */
    public static boolean isNetworkValidate(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null) {
            return cm.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    public static String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());
        return formatter.format(curDate);
    }

    public static int getCurrentYEAR() {
        Date curDate = new Date(System.currentTimeMillis());
        return curDate.getYear();
    }

    public static int getCurrentMONTH() {
        Date curDate = new Date(System.currentTimeMillis());

        return curDate.getMonth();
    }

    public static int getCurrentDAY() {
        Date curDate = new Date(System.currentTimeMillis());

        return curDate.getDate();
    }

    /**
     * 是否是WIFI连接
     * 
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // mobile
        State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        if (wifi == State.CONNECTED) {
            LogInfo.LogOut("isWifi=true");
            return true;
        } else {
            LogInfo.LogOut("isWifi=false");
            return false;
        }
    }

    public static void updateNotify(Context context, Class<?> c) {
        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (c != null) {
            Notification notification = new Notification(R.drawable.ic_launcher, null, System.currentTimeMillis());
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClass(context, c);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 10, intent, 2);
            notification.setLatestEventInfo(context, context.getText(R.string.app_name), context.getText(R.string.app_name), contentIntent);
            mNM.notify(R.layout.main, notification);
        } else {
            mNM.cancel(R.layout.main);
        }
    }
     
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmptyArrayList(ArrayList arrayList){
        return arrayList!=null&&arrayList.size()>0;
        
    }
    /**
     * 生成数字和字母的随机数
     */
    public static String generateRandomVeriCode() {
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            sbf.append(" ").append(RANDOM_VEERI_CODE[(int) Math.floor(Math.random() * 33)]).append(" ");
        }
        return sbf.toString();
    }

    private final static String[] RANDOM_VEERI_CODE = { "0", "1", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
}
