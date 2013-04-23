package com.lovepig.model;

import java.util.ArrayList;


public class MusicModel extends Model{
    
    public MusicModel() {
        super();
    }
    public MusicModel(int id) {
        this.id=String.valueOf(id);
    }
    /**
     * 歌曲url路径
     */
    public String url;
	
    public ArrayList<MusicModel> child;

    public int childType;

    public int type;

    public boolean isCover;

    public String coverUrl;
    
}
