package com.lovepig.model;

import java.io.Serializable;

import com.lovepig.utils.Json;

/**
 * 新闻评论
 * 
 * @author Administrator
 * 
 */
public class NewsCommentModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public NewsCommentModel() {
        super();
    }

    public NewsCommentModel(int newsId, float mark, String comment) {
        super();
        this.newsId = newsId;
        this.mark = mark;
        this.comment = comment;
    }

    private int memberId;
    /**
     * 新闻id
     */
    private int newsId;

    /**
     * 评论的星数
     */
    private float mark;
    private int commentId;

    private String userName;
    private String productName;

    private String title;
    private String time;
    private String comment;
    private String headUrl;
    private int memnerId;

    public int getMemnerId() {
        return memnerId;
    }

    public void setMemnerId(int memnerId) {
        this.memnerId = memnerId;
    }

    private int count = 0;

    public NewsCommentModel(int count) {
        super();
        this.count = count;
    }

    private int firstSize;
    private int maxSize;

    public int getFirstSize() {
        return firstSize;
    }

    public void setFirstSize(int firstSize) {
        this.firstSize = firstSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

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

    // =========================================================
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

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /**
     * 发表评论参数
     */
    public Json updateCommentParams(int apiVersion) {
        Json json = new Json(apiVersion);
        json.put("grade", mark);
        json.put("newsId", newsId);
        json.put("comment", comment);

        return json;
    }

    /**
     * commentId":2, "comment":"***今日开始访美", "memnerId":1001,
     * "nickName":"zhangSan", "grade":3, "time":""
     * 
     * @param json
     * @return
     */
    public NewsCommentModel parserJsonForComments(Json json) {
        if (json.has("commentId")) {
            commentId = json.getInt("commentId");
        }
        if (json.has("comment")) {
            comment = json.getString("comment");
        }
        if (json.has("commentId")) {
            memnerId = json.getInt("memnerId");
        }
        if (json.has("nickName")) {
            userName = json.getString("nickName");
        }
        if (json.has("grade")) {
            mark = json.getInt("grade");
        }
        if (json.has("time")) {
            time = json.getString("time");
        }
        return this;
    }
}
