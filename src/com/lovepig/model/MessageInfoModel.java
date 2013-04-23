package com.lovepig.model;

import java.io.Serializable;

public class MessageInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    public String msgTitle;
    public String msgContent;
    public String msgDate;
    public String sendName;
    public String sendId;
    public String addressee;
    public String id;
    
    public int msgType; // 0:邮件、1:申请好友、2:加为好友成功消息、3:好友商品推荐、4:好友商品赠送、 -1:无
    public boolean isNewFlag;
    public int count;// 信息条数
    public int parengId;
    public int productId;
    
   
    
    public boolean checked;
}
