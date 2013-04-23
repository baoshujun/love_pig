package com.lovepig.model;

/**
 * 商城广告实体
 * 
 * @author Administrator
 * 
 */
public class ShoppingAdvertsModel {
    /*
     * 返回: {"result":1, "adverts":[{ "indexNum": 1,
     * "lateralPic":"http://www.kenni.com/huyou.jpg",
     * "target":"http://www.baidu.com"}] }
     */
    private int indexNum; // 显示索引

    // private String verticalPic; // 竖屏图片URL
    private String lateralPic; // 横屏图片URL

    private String target; // 图片链接

    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
    }

    public String getLateralPic() {
        return lateralPic;
    }

    public void setLateralPic(String lateralPic) {
        this.lateralPic = lateralPic;
    }

    public int getIndexNum() {
        return this.indexNum;
    }

    // // --------------------------------------------
    //
    // public String getVerticalPic() {
    // return verticalPic;
    // }
    //
    // public void setVerticalPic(String verticalPic) {
    // this.verticalPic = verticalPic;
    // }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTarget() {
        return this.target;
    }
}
