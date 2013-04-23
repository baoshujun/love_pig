package com.lovepig.model;

import java.io.Serializable;

public class ShopingInfoModel extends ShoppingTushuTitleModel implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String name;
    public String author;
    public String price;
    public String image_url;
    public String grade;
    public String publish_house;
    public String typeString;
    public String checkFlag;
    public int isCategory = 0;
    public String time;
    public String introString;
    public int count = 0;
    public int purchaseDetailId = 0;
    public String COString;
    public String ROString;
    public String periodName;
    public int hasSingleList;
    
    // -------------------------------------
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }


}
