package com.lovepig.model;

/**
 * 收藏
 * 
 * @author Administrator
 * 
 */
public class FavoritesModel {
    /**
     * { "favoriteId":123, "productId":56245, "productName":"",
     * "favoriteDate":"2011-12-12", "source": 1}
     */
    private String productName = "";
    private int favoriteId;
    private int productId;
    private String favoriteDate;
    private int source;// 1:商城 2:图书馆
    private int totalNum;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public void setFavoriteDate(String favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
