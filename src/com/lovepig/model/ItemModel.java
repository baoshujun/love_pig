package com.lovepig.model;

public class ItemModel {

    public ItemModel() {
        super();
    }

    public ItemModel(String name, String id) {
        super();
        this.name = name;
        this.id = id;
    }

    /**
     * 产品名
     */
    public String name;

    /**
     * 周期名
     */
    public String periodName;

    /**
     * 后缀
     */
    public String suffix;
    /**
     * 下载id
     */
    public String deliverId;

    /**
     * GUID 名称
     */
    public String guid;
    /**
     * 总数
     */
    public String counts;
    /**
     * 当前页
     */
    public String currentPage;
    /**
     * 作者
     */
    public String author;
    /**
     * 出版社
     */
    public String press;
    /**
     * Item id
     */
    public String id;

    /**
     * 产品 id
     */
    public String parentId;
    /**
     * 是否被选中
     */
    public boolean checked;

    /**
     * 下载百分比 0-100
     */
    public int pos;
    /**
     * 下载状态,0=停止,1=等待,2=下载中,3=错误,4=完成,5=删除
     */
    public int state;
    /**
     * Item co下载地址
     */
    public String url;
    /**
     * ro 下载地址
     */
    public String urlRo;

    /**
     * Item 图标地址
     */
    public String imgUrl;
    /**
     * 类型,0=新闻,1=报纸,2=期刊,3=图书,4=图书馆,5=专供, 新闻报纸期刊是有二级的,二级时type=6
     */
    public int type;
    public String pubDate;
    /**
     * 1:商城 2:图书馆 6:独览天下期刊 7:方正报纸
     */
    public String source;
    public int isCategory;

    public int getIsCategory() {
        return isCategory;
    }

    public void setIsCategory(int isCategory) {
        this.isCategory = isCategory;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlRo() {
        return urlRo;
    }

    public void setUrlRo(String urlRo) {
        this.urlRo = urlRo;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((deliverId == null) ? 0 : deliverId.hashCode());
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
        ItemModel other = (ItemModel) obj;
        if (deliverId == null) {
            if (other.deliverId != null)
                return false;
        } else if (!deliverId.equals(other.deliverId))
            return false;
        return true;
    }
}
