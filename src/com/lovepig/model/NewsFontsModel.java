package com.lovepig.model;

public class NewsFontsModel {
    public NewsFontsModel(String name, int fontSize, boolean checked) {
        super();
        this.name = name;
        this.fontSize = fontSize;
        this.checked = checked;
    }

    public NewsFontsModel() {
        super();
    }

    public String name;
    public int fontSize;
    public boolean checked;
}
