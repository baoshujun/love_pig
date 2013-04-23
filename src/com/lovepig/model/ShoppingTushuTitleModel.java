package com.lovepig.model;



public class ShoppingTushuTitleModel {
    /* "id":7,"name":"新品速递","checked":1 */
    private int id;
    private int checked;
    private int type;
    private int indexNum;

    // -------------------------------------
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    // --------------------------------------
    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getChecked() {
        return this.checked;
    }
    //----------------------------------------
    public void setType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
  //----------------------------------------
    public void setIndexNum(int indexNum) {
        this.indexNum = indexNum;
    }

    public int getIndexNum() {
        return this.indexNum;
    }
}
