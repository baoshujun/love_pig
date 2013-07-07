package com.lovepig.view;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lovepig.engine.ImageEngine;
import com.lovepig.main.R;
import com.lovepig.model.BoarPigFactoryDetailModel;
import com.lovepig.pivot.BaseManager;
import com.lovepig.pivot.BaseView;
import com.lovepig.utils.Json;

/**
 * @author greenboy1
 * @E-mail:360315247@qq.com
 * @qq:360315247
 * @version 1.0 创建时间：May 5, 2013 6:38:40 PM
 * 
 */
public class BoarPigFactoryDetailView extends BaseView implements OnClickListener {
	RelativeLayout toListView;
	ImageView toListViewiv;
	TextView categoryName;
	BoarPigFactoryDetailModel pfm;
	TextView pigFactoryName,pigFactoryDetailDesc,productContent,people,phoneNum,detailAddress,mail,networkAddress;
	ImageView pigImg,icon01,icon02,icon03;
	public BoarPigFactoryDetailView(Context context, int layoutId,BaseManager manager,BoarPigFactoryDetailModel pfm) {
		super(context, layoutId, manager);
		this.pfm = pfm;
		toListView = (RelativeLayout) findViewById(R.id.pigProduct);
		toListViewiv = (ImageView) findViewById(R.id.toListView);
		categoryName = (TextView)findViewById(R.id.title);
		categoryName.setText("猪场详情");
		toListView.setOnClickListener(this);
		toListView.setOnClickListener(this);
		pigFactoryName = (TextView)findViewById(R.id.pigFactoryName);
		pigFactoryDetailDesc = (TextView)findViewById(R.id.pigFactoryDetailDesc);
		productContent = (TextView)findViewById(R.id.productContent);
		people = (TextView)findViewById(R.id.people);
		phoneNum = (TextView)findViewById(R.id.phoneNum);
		detailAddress = (TextView)findViewById(R.id.detailAddress);
		mail = (TextView)findViewById(R.id.mail);
		networkAddress = (TextView)findViewById(R.id.networkAddress);
		pigImg = (ImageView)findViewById(R.id.bigImg);
		icon01 = (ImageView)findViewById(R.id.img01);
		icon02 = (ImageView)findViewById(R.id.img02);
		icon03 = (ImageView)findViewById(R.id.img03);
		setView(pfm);
	}
	
	private void setView(BoarPigFactoryDetailModel pfm){
		pigFactoryName.setText(pfm.pigFactoryName);
		pigFactoryDetailDesc.setText(pfm.pigFactoryDesc);
		productContent.setText(pfm.product);
		people.setText("联系人："+ pfm.contact);
		detailAddress.setText("详细地址："+ pfm.address);
		mail.setText("邮箱："+pfm.email);
		networkAddress.setText("网址：" + pfm.site);
		ImageEngine.setImageBitmap(pfm.img, pigImg,R.drawable.ic_launcher, -1);
		Json imgJson = new Json(pfm.pigImgs);
		ImageEngine.setImageBitmap(imgJson.getString("icon1"), icon01, R.drawable.ic_launcher, -1);
		ImageEngine.setImageBitmap(imgJson.getString("icon2"), icon02, R.drawable.ic_launcher, -1);
		ImageEngine.setImageBitmap(imgJson.getString("icon3"), icon03, R.drawable.ic_launcher, -1);
	}

}
