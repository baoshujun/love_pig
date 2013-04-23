package com.lovepig.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ShareModel implements Parcelable {
    /**
     * 分享者ID
     */
    public String userId;
    /**
     * 分享者名字
     */
    public String userName;

    /**
     * 分享类型,1为人人,2为新浪
     */
    public String shareType;

    /**
     * 新闻标题
     */
    public String newsTitles;
    /**
     * 新闻链接
     */
    public String newsUrl;

    /**
     * 新闻图片
     */
    public String imageUrl;

    public ShareModel() {
        super();
    }

    public ShareModel(String newsTitles, String newsUrl, String imageUrl) {
        super();
        this.newsTitles = newsTitles;
        this.newsUrl = newsUrl;
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
        // -----------------------------------
        dest.writeString(shareType);
        dest.writeString(newsTitles);
        dest.writeString(newsUrl);
        dest.writeString(imageUrl);
    }

    public static final Parcelable.Creator<ShareModel> CREATOR = new Parcelable.Creator<ShareModel>() {

        @Override
        public ShareModel createFromParcel(Parcel source) {
            ShareModel model = new ShareModel();

            model.userId = source.readString();
            model.userName = source.readString();
            model.shareType = source.readString();
            model.newsTitles = source.readString();
            model.newsUrl = source.readString();
            model.imageUrl = source.readString();
            return model;
        }

        @Override
        public ShareModel[] newArray(int size) {
            return null;
        }
    };

}
