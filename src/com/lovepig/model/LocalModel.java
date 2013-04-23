
package com.lovepig.model;

import java.util.ArrayList;
/**
 * 本地音乐实体
 * @author bao.shujun@audiocn.com
 *
 */
public class LocalModel extends Model{
    public LocalModel(){
    }
	public LocalModel(String id) {
        super(id);
    }
	public LocalModel(String id,String name) {
        super(id);
        this.name=name;
    }
	
    public String path;
	public String lrcPath;
	/**
	 * 0:一级目录，１.为二级目录（歌星，专辑，日期），２：为三级目录（歌曲列表）
	 */
	public int type;
	public String artist;
	public String album;
	public String style;
	public String year;
	public int childType;
    /**
     * 子类
     */
    public ArrayList<LocalModel> child;
    

}
