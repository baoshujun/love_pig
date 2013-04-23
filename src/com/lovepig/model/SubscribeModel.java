package com.lovepig.model;

public class SubscribeModel {
    private String produtId;
    private String productName;
    private String endDate;
    private String price;
    private String payer;
    private String payerId;
    private String subscribeId;
    private boolean isSubscribe;
    private int  count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isSubscribe() {
        return isSubscribe;
    }

    public void setSubscribe(boolean isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public String getSubscribeId() {
        return subscribeId;
    }

    public void setSubscribeId(String subscribeId) {
        this.subscribeId = subscribeId;
    }

    public String getProdutId() {
        return produtId;
    }

    public void setProdutId(String produtId) {
        this.produtId = produtId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    /*
     * 产品Id,产品名称,到期时间,价格,付款人Id,付款人名称
     * 
     * 
     * "subscribeList":[{ "produtId":1011, "productName":"新华时评",
     * "endDate":"2012-04-30", "price":"￥3/月", "payer":111600,
     * "payerId":"hcwsqq"}]
     */
}
