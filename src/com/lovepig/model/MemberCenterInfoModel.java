package com.lovepig.model;

import java.io.Serializable;

public class MemberCenterInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String imageUrl;
    private String borrowTime;
    private String dueTime;
    private String bookIntro;
   

    private String deliveryId;
    
    
    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }
    // =============================================
    public String getBookIntro() {
        return this.bookIntro;
    }

    public void setBookIntro(String bookIntro) {
        this.bookIntro = bookIntro;
    }

    // ---------------------------------------------
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // ---------------------------------------------
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ---------------------------------------------
    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ---------------------------------------------
    public String getBorrowTime() {
        return this.borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    // ---------------------------------------------
    public String getDueTime() {
        return this.dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }
}
