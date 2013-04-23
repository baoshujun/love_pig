package com.lovepig.model;

import java.io.Serializable;

public class FriendInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;

    private String name;
    private String imageUrl;
    private String email;
    private String phonenum;
    private String account;
    private boolean isFriend;
    private boolean selectflag;
    private int count = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    // =============================================
    public String getFriendAccount() {
        return this.account;
    }

    public void setFriendAccount(String account) {
        this.account = account;
    }

    // ---------------------------------------------
    public String getFriendPhonenum() {
        return this.phonenum;
    }

    public void setFriendPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    // ---------------------------------------------
    public String getFriendId() {
        return this.id;
    }

    public void setFriendId(String id) {
        this.id = id;
    }

    // ---------------------------------------------
    public String getFriendName() {
        return this.name;
    }

    public void setFriendName(String name) {
        this.name = name;
    }

    // ---------------------------------------------
    public String getFriendImageUrl() {
        return this.imageUrl;
    }

    public void setFriendImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ---------------------------------------------
    public String getFriendEmail() {
        return this.email;
    }

    public void setFriendEmail(String email) {
        this.email = email;
    }

    // ---------------------------------------------
    public boolean getFriendSelectflag() {
        return this.selectflag;
    }

    public void setFriendSelectflag(boolean selectflag) {
        this.selectflag = selectflag;
    }

    // ---------------------------------------------
    public boolean getIsFriend() {
        return this.isFriend;
    }

    public void setIsFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FriendInfoModel other = (FriendInfoModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
