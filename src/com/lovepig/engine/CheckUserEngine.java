package com.lovepig.engine;



import com.lovepig.main.Configs;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.BaseManager;
import com.lovepig.utils.ConfigInfo;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

/**
 * 鉴权和轮询
 * 
 * @author Administrator
 * 
 */
public class CheckUserEngine extends BaseEngine {

    public CheckUserEngine(BaseManager manager) {
        super(manager);
    }

    public static boolean isNetwork = false;

    /**
     * 鉴权,在当前线程
     */
    public void checkUser() {
        isNetwork = false;
        Json json = new Json(0);
        String certificateId = ConfigInfo.getCertificateId();
        // Configs.certificateId = certificateId;
        json.put("certificate", certificateId);
        LogInfo.LogOut("checkUser:" + json.toNormalString());
        String rs = httpRequestThisThread(0, Configs.authenticationAction + json.toString(),false);
        LogInfo.LogOut("result:" + rs);
        if (rs == null) {
            return;
        }
        Json rJson = new Json(rs);
        int result = rJson.getInt("result");
        if (result != -1) {
            isNetwork = true;
        }
        if (result == 1) {
            int bind = rJson.getInt("binding");
            if (bind == 1) {
                Configs.userid = rJson.getString("userId");
                Configs.readerUrl=rJson.getString("reader");
                Configs.mUser_Name = rJson.getString("userName");
                Configs.mMemberId = rJson.getInt("memberId");
                Configs.updateUidToTypeAndVsersion(Configs.userid, Configs.mUser_Name, Configs.mMemberId);
            } else {
                Configs.userid = null;
            }
            
            if(rJson.has("isNewUser") && rJson.has("givePoint")){//新用户赠送提示
                if(rJson.getInt("isNewUser") == 1){
                    manager.sendMessage(manager.obtainMessage(3, rJson.getInt("givePoint"), 0));
                }
            }
            
            // Configs.userid=rJson.getString("userId");
            // Configs.mUser_Name=rJson.getString("userName");
            // Configs.updateUidToTypeAndVsersion(Configs.userid,Configs.mUser_Name,rJson.getInt("memberId"));
            Configs.isChangededPWD = rJson.getInt("changedPWD");
            boolean isSpecial = rJson.getInt("special") == 0 ? false : true;
            if (isSpecial != Configs.isSpecial(manager.context)) {
                Configs.setSpecial(manager.context, isSpecial);
                // 此处还要通知书架,已经改为在书架中自动刷新
                // Application.bookshelfManager.specialNotifyDataSetChanged();

            }
            Json uJson = rJson.getJson("upgrade");
            if (uJson != null && uJson.has("upgradable")) {
                Configs.UPDATE_FLAG = uJson.getInt("upgradable");
                Configs.u_Url = uJson.getString("url");
                Configs.u_ShowPopStr = uJson.getString("info");
                Configs.u_Discribe = uJson.getString("version");
                manager.sendEmptyMessage(1);
            }

            Json cs = rJson.getJson("remindMsg");
            if (cs != null && cs.has("dueSize")) {
                int dueSize = cs.getInt("dueSize");
                if (dueSize > 0) {
                    final String alter = "余额不足,您有" + dueSize + "个订阅需要续费.需要金额" + cs.getString("total") + "元,当前余额" + cs.getString("balance") + "元.请尽快充值.\n续费产品为:"
                            + cs.getString("subNames");
                    manager.post(new Runnable() {
                        @Override
                        public void run() {
                            manager.showAlert(alter);
                        }
                    });
                }

            }

//            Configs.isCheckin = true;
            timingPolling();
        }
    }

    /**
     * •上传: 证书号
     * 
     * Action: timingPolling •JSON: •请求： request={ <commen>,
     * "certificate":"456bd" } •返回：(用户信息,新产品信息,新消息数量 ) { " result ":1,
     * " userInfo ":{ "memberId":1367603, "userId":"xiyuan",
     * "userName":"xiyuan", "email":"fan.jing1@audiocn.com", "status":2, // 用户状态
     * 1:審核中 2:有效 3:无效 "level":5, // 用户级别 1:初級會員 2:中級會員 3:高級會員 4:VIP會員 5:專供會員
     * "certificate":"20648", "binding":1},
     * 
     * 
     * " products ":[{ "productId":1000, //产品Id "productName":"新华晨报（02月16日）", //
     * 产品名 "parentId":4, // 父产品Id "parentName":"新华晨报", // 父产品名
     * "publishDate":"2012-02-16 07:22", "deliveryId":44589876}], " newMsg ":3
     * // 新消息数量 }
     */
    public void timingPolling() {}
}
