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
//    public static String lovePigImagePath = lovePigPath + "Image/";
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
     * 分享到新浪微博使用
     */
    public static String WEIBO_API_KEY = "362193558";
    //public static String WEIBO_API_KEY = "2282403708";
    /**
     * 分享到新浪微博使用
     */
    public static String WEIBO_SECRET_KEY = "4f1d4d6d95545eac6ed60f24eef67b20";
    //public static String WEIBO_SECRET_KEY = "30b3ce0941a6a5634dc26810956a71c2";
    /**
     * 是否开启程序日志
     */
    public static boolean isDebug = true;
    /**
     * 程序文件存储路径
     */
    public static String tlcyMusicPath = Utils.getSdcardPath() + "/XHPM/";
    /**
     * 图片存储
     */
    public static String tlcyImagePath = tlcyMusicPath + "Image/";

    public static String tlcyImageCache = tlcyMusicPath + "Cache/";
    public static String tlcyReaderApkPath = tlcyMusicPath + "readerrd.apk";

    public static String tlcyVideoPath = tlcyMusicPath + "Video/";
    public static String tlcyAudioPath = tlcyMusicPath + "Audio/";
    public static String tlcyRoPath = tlcyMusicPath + "ro/";// ro文件路径
    public static String tlcyCoPath = tlcyMusicPath + "co/";// co文件根路径
    public static String tlcyOriginPath = tlcyMusicPath + "origin/";// 解密后的临时文件根路径

    public static String xhpmLrcPath = tlcyMusicPath + "lrc/";
    public static String xhpmMusicPath = tlcyMusicPath + "music/";

    public static final String PREFS_NAME = "TlPrefsFile";
    public static final String PREFS_UserInfo = "UserInfo";

    public static String u_ShowPopStr = "";// 服务器连接信息
    public static String u_Url = "";// 升级地址
    public static String u_Discribe;
    public static String userid = "123";// 用户名字
    public static String readerUrl = null;// 阅读器下载地址
    public static String readerpackageName = "com.founder.readerforrd";// 阅读器包名称
    public static int readerVersionCode = -1;// 阅读器版本号
    public static String mUser_Name;// 用户昵称
    public static String mUser_Email;// 用户邮箱
    public static String mUser_PhoneNum;// 用户手机号
    public static int mMemberId;// 用户手机号
    public static String mCheckCode = "1234";// 验证码
    /**
     * 1=报纸 2=期刊 3=图书 4=新闻 5=图书馆
     */
    public static int parentId = 4;

    public static String C_NEWSPAPER_ID = "1";
    public static String C_PERIODICAL_ID = "2";
    public static String C_BOOK_ID = "3";
    public static String C_NEWS_ID = "4";
    public static String C_LIBRARY_ID = "5";
    public static String C_ZHUANGONG_ID = "6";
    public static String C_XINGTONGTUITONG_ID = "7";

    public static String C_NEWSPAPER_NAME = "报纸";
    public static String C_PERIODICAL_NAME = "期刊";
    public static String C_BOOK_NAME = "图书";
    public static String C_NEWS_NAME = "新闻";
    public static String C_LIBRARY_NAME = "图书馆";
    public static String C_ZHUANGONG_NAME = "专供";
    public static String C_XINGTONGTUITONG_NAME = "系统推送";

    public final static int EACH_PAGE_COUNTNUM = 20;
    public final static int EACH_PAGE_MAX_COUNTNUM = 500;

    public static final int MORE_LIST_DATA = 50;
    public static final int PRE_LIST_DATA = 51;
    public static final int JUMP_LIST_PAGE = 52;
    public static final int CLICK_LIST_ADVER_IMAGE = 53;
    
    public static String getNewsShareContent = "/xhs/getNewsforPage.action";// 获取分享新闻的url
    public static String getMyProduct = "/xhs/getMyProduct.action";// 获取下载url
    public static String getNewsAction = "/xhs/getNews.action";// 新闻
    public static String getNewsTypesAction = "/xhs/getNewsTypes.action";// 新闻分类
    public static String getAdvertsAction = "/xhs/getAdverts.action";// 商城首页广告
    public static String getDetailsAction = "/xhs/getDetails.action";// 产品详情
    public static String getContentsAction = "/xhs/getContents.action";// 内容
    public static String getCategorysAction = "/xhs/getCategorys.action";// 栏目内容
    public static String searchCategorysAction = "/xhs/searchCategorys.action";
    public static String myCenterAction = "/xhs/myCenter.action";
    
    public static String  sentNewsCommentsAction = "/xhs/newsComment.action";//新闻评论
    public static String  getNewsComment= "/xhs/getNewsComment.action";//获取新闻评论

    
    
    public static String giveBackBookAction = "/xhs/giveBackBook.action"; // 还书
    public static String borrowBookAction = "/xhs/borrowBook.action"; // 借书
    public static String getBorrowListAction = "/xhs/getBorrowList.action"; // 借阅列表
    public static String buyBookAction = "/xhs/buyBook.action"; // 购买书

    public static String GetfavoriteAction = "/xhs/getFavorites.action"; // 获取收藏
    public static String favoritesAction = "/xhs/favorites.action"; // 添加收藏
    public static String unfavoriteAction = "/xhs/unfavorite.action"; // 取消收藏

    public static String subscribeAction = "/xhs/subscribe.action"; // 订阅
    public static String unsubscribeAction = "/xhs/unsubscribe.action"; // 取消订阅
    public static String getSubscribeList = "/xhs/getSubscribeList.action"; // 获取订阅列表
    public static String tosubscribeAction = "/xhs/toSubscribe.action"; // 续订
    public static String getSingleJournal = "/xhs/getSingleJournal.action";

    public static String recommendAction = "/xhs/recommend.action"; // 推荐
    public static String presentAction = "/xhs/present.action"; // 赠送
    public static String timingPollingAction = "/xhs/timingPolling.action"; // 定时轮询
    public static String authenticationAction = "/xhs/authentication.action"; // 鉴权
    public static String giveSingleJournal = "/xhs/giveSingleJournal.action"; // 单本赠送
    public static String buySingleJournal = "/xhs/buySingleJournal.action"; // 单本购买
    /**
     * 获取好友列表
     */
    public static String GET_FRIEND_LIST_ACTION = "/xhs/getFriends.action"; // 好友列表
    public static String searchFriendsAction = "/xhs/searchFriends.action"; // 搜索好友
    public static String delFriendAction = "/xhs/delFriend.action"; // 好友删除
    public static String addFriendAction = "/xhs/addFriend.action"; // 好友增加
    public static String Get = "/xhs/getFriends.action"; // 好友增加

    /**
     * 用户相关
     */
    public static final String getRechargeListAction = "/xhs/getRechargeList.action";// 获取充值记录
    public static final String commentAction = "/xhs/comment.action";// 发送用户评价
    public static final String getCommentAction = "/xhs/getComment.action";// 获取用户评价
    public static final String accountAction = "/xhs/account.action";// 用户信息
    public static final String UserInfoaccountAction = "/xhs/getAccount.action";// 用户信息
    public static final String RegisterUserAction = "/xhs/register.action";// 注册用户
    public static final String CheckUsernameAction = "/xhs/checkUsername.action";// 验证用户文号
    public static final String UpdateUserInfoAction = "/xhs/changeAccount.action";// 修改用户信息
    public static final String ModifyUserPwdAction = "/xhs/changePassword.action";// 修改密码
    public static final String TobindingAction = "/xhs/binding.action";// 用户绑定
    public static final String UnbindingAction = "/xhs/unpinless.action";// 解除绑定

    public static final String GET_LIBRARY_CARD = "/xhs/getLibraryCard.action";// 会员中心
    public static final String Join_Library = "/xhs/joinLibrary.action";// 会员中心

    /**
     * 下载相关的
     * 
     */
    public static final String FetchRedownloadAction = "/xhs/getDownList.action";// 获取已订阅的产品
    // 获取产品已发行的具体周期产品
    public static final String GetProductPeroidList = "/xhs/getProductPeroidList.action";
    // 根据账单好重新获取ｕｒｌ
    public static final String GetUrl = "/xhs/getUrl.action";
    /**
     * 和消息相关的
     * 
     */
    public static final String GET_MAILS_IN_MORE_MSG = "/xhs/getMails.action";// 收件箱列表
    public static final String GET_OUTBOX_IN_MORE_MSG = "/xhs/getOutbox.action";// 发件箱列表
    public static final String GET_DELMESSAGE_IN_MORE_MSG = "/xhs/delMessage.action";// 发件箱列表
    public static final String SENDMAIL_IN_MORE_MSG = "/xhs/sendMail.action";// 发邮件
    public static final String ADD_FRIEND_CONFIRM_IN_MORE_MSG = "/xhs/addFriendConfirm.action";// 确认添加好友

    /**
     * 缴费相关的
     */
    public static final String GET_CONSUME_LIST = "/xhs/getConsumeList.action";// 获取消费记录
    public static final String YeBaoRecharge = "/xhs/memberRechargeRequest.action";// 获取消费记录
    public static final String GetAccountOverview = "/xhs/getAccountOverview.action";// 获取账号信息

    public static final String MEM_BERYEEPAY_REQUEST = "/xhs/memberYeepayRequest.action";//易宝获取订单
   

    /**
     * 方正报纸打开时使用的用户名和密码
     */
    public static final String Founder_User_Name = "ruide";
    public static final String Founder_User_Password = "xruide";

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

    
//     public static final String HostName2[] = {
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://server8.audiocn.com:1433",
//     "http://server8.audiocn.com:1433", "http://192.168.1.44:8080" };// 音乐
//     
//     public static final String HostName1[] = {
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://lijunhua2004.3322.org:8080",
//     "http://lijunhua2004.3322.org:8080", "http://Server8.newbook.mobi:1433",
//     "http://Server8.newbook.mobi:1433", "http://192.168.1.44:8080" };// 音乐
     
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
            nowOrientation = context.getResources().getConfiguration().orientation;
            lastOrientation = nowOrientation;
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

    /**
     * 保存是否有专供
     */
    public static void setSpecial(Context c, boolean isSpecial) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isSpecial", isSpecial).commit();
    }

    /**
     * 读取是否有专供
     */
    public static boolean isSpecial(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isSpecial", false);
    }

    public static int getPlayMode(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getInt("playMode", 0);
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
     * 书架当前分类标签
     * 
     * @param c
     * @return
     */
    public static int getBookShelfCurrentType(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getInt("shelf_current_type", 0);
    }

    public static void setBookShelfCurrentType(Context c, int type) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putInt("shelf_current_type", type).commit();
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

    /**
     * 
     * 是否显示新闻页帮助界面
     * 
     * @param c
     * @return
     */
    public static boolean isShowNewHelp(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isShowNewHelp", true);
    }

    public static void setShowNewHelp(Context c, boolean isShowNewHelp) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isShowNewHelp", isShowNewHelp).commit();
    }

    /**
     * 
     * 是否显示更多页帮助界面
     * 
     * @param c
     * @return
     */
    public static boolean isShowMoreHelp(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isShowMoreHelp", true);
    }

    public static void setShowMoreHelp(Context c, boolean isShowNewHelp) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isShowMoreHelp", isShowNewHelp).commit();
    }

    /**
     * 
     * 是否显示商城界面
     * 
     * @param c
     * @return
     */
    public static boolean isShowShoppingHelp(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isShowShoppingHelp", true);
    }

    public static void setShowShoppingHelp(Context c, boolean isShowNewHelp) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isShowShoppingHelp", isShowNewHelp).commit();
    }

    /**
     * 
     * 是否显示書架页帮助界面
     * 
     * @param c
     * @return
     */
    public static boolean isShowBookHelp(Context c) {
        return c.getSharedPreferences(PREFS_NAME, 0).getBoolean("isShowBookHelp", true);
    }

    public static void setShowBookHelp(Context c, boolean isShowNewHelp) {
        c.getSharedPreferences(PREFS_NAME, 0).edit().putBoolean("isShowBookHelp", isShowNewHelp).commit();
    }



}
