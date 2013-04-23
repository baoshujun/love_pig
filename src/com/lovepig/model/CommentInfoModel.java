package com.lovepig.model;

import java.io.Serializable;

public class CommentInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    private int memberId;
    private int productId;
    private float mark;
    private int commentId;
    
    private String userName;
    private String productName;

    private String title;
    private String time;
    private String comment;
    private String headUrl;
    private int count = 0;
    /*
     * "commentId": 1, "memberId":35135, "userName":"user1111", "productId":168,
     * "productName":"中国青年", "title":"", "mark":8, "time":"2012-10-21 14:56"
     * "comment":""
     */
    // -----------------------------------
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    // -----------------------------------
    public int getCommentCommentId() {
        return commentId;
    }

    public void setCommentCommentId(int commentId) {
        this.commentId = commentId;
    }
    // -------------------------------------
    public int getCommentProductId() {
        return productId;
    }

    public void setCommentProductId(int productId) {
        this.productId = productId;
    }
    // -------------------------------------
    public int getCommentMemberId() {
        return memberId;
    }

    public void setCommentMemberId(int memberId) {
        this.memberId = memberId;
    }
    // -------------------------------------
    public float getCommentMark() {
        return mark;
    }

    public void setCommentMark(float mark) {
        this.mark = mark;
    }
    //=========================================================
    public String getCommentTime() {
        return time;
    }

    public void setCommentTime(String time) {
        this.time = time;
    }
    // -------------------------------------
    public String getCommentTitle() {
        return title;
    }

    public void setCommentTitle(String title) {
        this.title = title;
    }
    // -------------------------------------
    public String getCommentProductName() {
        return productName;
    }

    public void setCommentProductName(String productName) {
        this.productName = productName;
    }
    // -----------------------------------
    public String getCommentHeadUrl() {
        return headUrl;
    }

    public void setCommentHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    // -----------------------------------
    public String getCommentName() {
        return userName;
    }

    public void setCommentName(String userName) {
        this.userName = userName;
    }
    // -----------------------------------
    public String getCommentContent() {
        return comment;
    }

    public void setCommentContent(String comment) {
        this.comment = comment;
    }
    // -----------------------------------
}
