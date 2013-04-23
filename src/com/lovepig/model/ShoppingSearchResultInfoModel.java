package com.lovepig.model;

import java.io.Serializable;

public class ShoppingSearchResultInfoModel extends ShopingInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    

    public ShoppingSearchResultInfoModel() {
        super();
    }
    private int parentId;


    public int getParentId() {
        return parentId;
    }
    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
    
    
}
