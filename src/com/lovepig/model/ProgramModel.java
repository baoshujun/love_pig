package com.lovepig.model;

/**
 * 在线音乐详情实体
 * @author bao.shujun@audiocn.com
 *
 */
public class ProgramModel extends Model{
	/**
	 * 1=mac,2=mp3,3=mv,4=other
	 */
	public int type;
	public int smallsize; 
	public int largesize; 
	public String coverUrl;
	public String artistid;
	public String artist;
	public String albumid;
	public String album;
	public String url;
	
}
