package com.lovepig.model;

import java.io.Serializable;

public class PlayModel implements Serializable {
    /**
     * 用于序列化
     */
    private static final long serialVersionUID = -7077077627716761280L;
    public String id;

    public String name;
    public String artist;
    public String album;
    /**
     * 当前播放的音频类型,服务器端根据不同的type值，返回不同的url:1=mac,2=mp3,3=mv,4=other
     */
    public int type;
    public boolean isOnline;
    public String url;// 判断类型
    public String path1;// 原唱
    public String path2;// 伴唱
    public String lrcUrl;
    public boolean hasMV;// 播放本地时,是否有mv可以切换到卡拉OK模式
    public int mvsize;// 播放本地时，如果mv尚未下载，侧提示下载

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
        PlayModel other = (PlayModel) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
